package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/9.
 */
public class LineChartRenderer extends AbsColumnBase {

    public LineChartRenderer(Context context, ChartListener chartListener) {
        super(context, chartListener);
    }

    @Override
    public void drawRects(Canvas canvas) {

        drawLines(canvas);

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<RectF> rectFs = getRectFs();

        int count = 0;

        rectPaint.setStrokeWidth(30);

        for(RectF rectF : rectFs) {
            if( minViewport.contains(getRawRectF(rectF).centerX(), rectF.top)) {

                ColorDrawable colorDrawable = new ColorDrawable(getValueColor(count));
                colorDrawable.setAlpha(150);

                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(getRawRectF(rectF).centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8), rectPaint);

                colorDrawable.setAlpha(255);
                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(getRawRectF(rectF).centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8) * 0.7f, rectPaint);

            }
            count++;
        }
    }


    private void drawLines(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<RectF> rectFs = getRectFs();

        RectF oldRectF = null;

        int count = 0;

        for(RectF rectF : rectFs) {

            if(oldRectF != null && getRawRectF(rectF).centerX() > minViewport.left) {

                float x1 = getRawRectF(oldRectF).centerX()  < minViewport.left ? minViewport.left : getRawRectF(oldRectF).centerX();
                float y1 = oldRectF.top > minViewport.bottom ? minViewport.bottom : oldRectF.top;

                float x2 = getRawRectF(rectF).centerX();
                float y2 = rectF.top > minViewport.bottom ? minViewport.bottom : rectF.top;

                float oldA_Distance = Math.abs(getRawRectF(oldRectF).centerX() - getRawRectF(rectF).centerX());
                float oldB_Distance = Math.abs(oldRectF.top - rectF.top);

                float curA_Distance;
                float curB_Distance;


                if(minViewport.contains(getRawRectF(oldRectF).centerX(), oldRectF.top) &&
                        minViewport.contains(getRawRectF(rectF).centerX(), rectF.top)) {

                    x1 = getRawRectF(oldRectF).centerX();
                    y1 = oldRectF.top;

                    x2 = getRawRectF(rectF).centerX();
                    y2 = rectF.top;

                    canvas.drawLine(x1, y1, x2, y2, linePaint);

                } else {


                    if(oldRectF.top > minViewport.bottom || rectF.top > minViewport.bottom) {

                        curB_Distance = Math.abs(minViewport.bottom - rectF.top);
                        curA_Distance = curB_Distance / oldB_Distance * oldA_Distance;

                        if(oldRectF.top > minViewport.bottom) {

                            x1 = getRawRectF(oldRectF).centerX() + (oldA_Distance - curA_Distance);
                            y1 = minViewport.bottom;

                        }

                        if (rectF.top > minViewport.bottom) {

                            x2 = getRawRectF(rectF).centerX() - curA_Distance;
                            y2 = minViewport.bottom;

                        }
                    }

                    if(oldRectF.top < minViewport.top || rectF.top < minViewport.top) {

                        curB_Distance = Math.abs(minViewport.top - rectF.top);
                        curA_Distance = curB_Distance / oldB_Distance * oldA_Distance;

                        if(oldRectF.top < minViewport.top) {
                            x1 = getRawRectF(oldRectF).centerX() + (oldA_Distance - curA_Distance);
                            y1 = minViewport.top;
                        }

                        if(rectF.top < minViewport.top) {
                            x2 = getRawRectF(rectF).centerX() - curA_Distance;
                            y2 = minViewport.top;
                        }

                    }

                    if(getRawRectF(oldRectF).centerX() < minViewport.left || getRawRectF(rectF).centerX() > minViewport.right) {

                        if(getRawRectF(oldRectF).centerX() < minViewport.left) {

                            curA_Distance = Math.abs(minViewport.left - getRawRectF(oldRectF).centerX());
                            curB_Distance = curA_Distance / oldA_Distance * oldB_Distance;

                            x1 = x1 > minViewport.left ? x1 : minViewport.left;

                            if(oldRectF.top > rectF.top) {
                                y1 = y1 < (oldRectF.top - curB_Distance) ? y1 : oldRectF.top - curB_Distance;
                            } else {
                                y1 = y1 > (oldRectF.top + curB_Distance) ? y1 : oldRectF.top + curB_Distance;
                            }

                        }

                        if(getRawRectF(rectF).centerX() > minViewport.right) {

                            curA_Distance = Math.abs(minViewport.right - getRawRectF(oldRectF).centerX());
                            curB_Distance = curA_Distance / oldA_Distance * oldB_Distance;

                            x2 = x2 < minViewport.right ? x2 : minViewport.right;

                            if(oldRectF.top > rectF.top) {
                                y2 = y2 > (oldRectF.top - curB_Distance) ? y2 : oldRectF.top - curB_Distance;
                            } else {
                                y2 = y2 < (oldRectF.top + curB_Distance) ? y2 : oldRectF.top + curB_Distance;
                            }
                        }
                    }

                    if(minViewport.contains(x1, y1) && minViewport.contains(x2, y2)) {
                        canvas.drawLine(x1, y1, x2, y2, linePaint);
                    }
                }
            }

            oldRectF = rectF;

            count++;
        }
    }

    private RectF getRawRectF(RectF rectF) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        rectF.left = rectF.right == minViewport.right ? rectF.left: rectF.right - getColumnWidth();
        rectF.right = rectF.left + getColumnWidth();

        return rectF;
    }

}
