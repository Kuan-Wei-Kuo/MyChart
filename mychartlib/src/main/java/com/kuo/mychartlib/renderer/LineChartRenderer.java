package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import com.kuo.mychartlib.listener.ChartGenericListener;
import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/9.
 */
public class LineChartRenderer extends AbsColumnBase {

    public LineChartRenderer(Context context, ChartListener chartListener, ChartGenericListener chartGenericListener) {
        super(context, chartListener, chartGenericListener);
    }

    @Override
    public void drawRects(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<RectF> rectFs = getRectFs();

        int count = 0;

        rectPaint.setStrokeWidth(30);

        for(RectF rectF : rectFs) {
            if(getRawRectF(rectF).centerX() > minViewport.left) {

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

        drawLines(canvas);
    }


    private void drawLines(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<RectF> rectFs = getRectFs();

        RectF oldRectF = null;
        
        for(RectF rectF : rectFs) {

            if(oldRectF != null && getRawRectF(rectF).centerX() > minViewport.left) {

                float newX = getRawRectF(oldRectF).centerX()  < minViewport.left ? minViewport.left : getRawRectF(oldRectF).centerX() ;

                float oldA_Distance = getDistance(getRawRectF(oldRectF).centerX(), 0, getRawRectF(rectF).centerX(), 0);
                float oldB_Distance = getDistance(0, oldRectF.top, 0, rectF.top);

                float curA_Distance = getDistance(newX, 0, getRawRectF(rectF).centerX(), 0);
                float curB_Distance = curA_Distance / oldA_Distance * oldB_Distance;

                float newY =  oldRectF.top > rectF.top ? oldRectF.top - (oldB_Distance - curB_Distance) : oldRectF.top + oldB_Distance - curB_Distance ;

                canvas.drawLine(newX, newY, getRawRectF(rectF).centerX(), rectF.top, linePaint);
            }

            oldRectF = rectF;

        }
    }

    private RectF getRawRectF(RectF rectF) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        rectF.left = rectF.right == minViewport.right ? rectF.left: rectF.right - getColumnWidth();
        rectF.right = rectF.left + getColumnWidth();

        return rectF;
    }

    private float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

}
