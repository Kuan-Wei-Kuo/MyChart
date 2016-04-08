package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.model.Viewport;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartRenderer extends AbsColumnBase {

    public ColumnChartRenderer(Context context, ChartListener chartListener) {
        super(context, chartListener);
    }

    @Override
    public void drawRects(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();
        ArrayList<RectF> rectFs = getRectFs();

        SelectData selectData = chartListener.getSelectData();

        int count = 0;

        for(RectF rectF : rectFs) {

            if(rectF.top < minViewport.top) {
                rectF.top = minViewport.top;
            }

            rectPaint.setColor(getValueColor(count));
            canvas.drawRect(rectF, rectPaint);

            if(rectF.contains(selectData.getX(), selectData.getY())) {
                selectData.setPosition(count);
            }

            count++;
        }

        selectRect(canvas);
    }

    private void selectRect(Canvas canvas) {

        SelectData selectData = chartListener.getSelectData();

        Log.d("po", selectData.getPosition() + "");
        Log.d("lpo", selectData.getLastPosition() + "");

        if(selectData.getPosition() != -1) {

            ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
            colorDrawable.setAlpha(100);

            rectPaint.setColor(colorDrawable.getColor());
            canvas.drawRect(getRectFs().get(selectData.getPosition()), rectPaint);
        }
    }

}
