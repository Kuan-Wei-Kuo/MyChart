package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartGenericListener;
import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartRenderer extends AbsColumnBase {

    public ColumnChartRenderer(Context context, ChartListener chartListener, ChartGenericListener chartGenericListener) {
        super(context, chartListener, chartGenericListener);
    }

    @Override
    public void drawRects(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();
        ArrayList<RectF> rectFs = getRectFs();

        int count = 0;

        for(RectF rectF : rectFs) {
            rectPaint.setColor(getValueColor(count));

            if(rectF.top < minViewport.top) {
                rectF.top = minViewport.top;
            }

            canvas.drawRect(rectF, rectPaint);
            count++;
        }
    }



}
