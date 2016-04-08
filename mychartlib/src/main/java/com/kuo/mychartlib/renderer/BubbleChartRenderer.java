package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.until.ChartRendererUntil;

/**
 * Created by Kuo on 2016/4/8.
 */
public class BubbleChartRenderer extends AbsColumnBase {

    public BubbleChartRenderer(Context context, ChartListener chartListener) {
        super(context, chartListener);
    }

    @Override
    public void drawRects(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        int count = 0;

        rectPaint.setStrokeWidth(50);

        for(RectF rectF : getRectFs()) {
            if(minViewport.contains(getRawRectF(rectF).centerX(), rectF.top)) {

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

    private RectF getRawRectF(RectF rectF) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        rectF.left = rectF.right == minViewport.right ? rectF.left: rectF.right - getColumnWidth();
        rectF.right = rectF.left + getColumnWidth();

        return rectF;
    }
}
