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

    private ArrayList<RectF> rectFs = new ArrayList<>();

    private float columnWidth = 0f;

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
            if(rectF.centerX() > minViewport.left) {

                ColorDrawable colorDrawable = new ColorDrawable(getValueColor(count));
                colorDrawable.setAlpha(150);

                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(rectF.centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8), rectPaint);

                colorDrawable.setAlpha(255);
                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(rectF.centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8) * 0.7f, rectPaint);

            }
            count++;
        }
    }

}
