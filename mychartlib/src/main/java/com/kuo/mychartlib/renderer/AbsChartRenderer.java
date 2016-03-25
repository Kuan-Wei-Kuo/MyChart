package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.kuo.mychartlib.until.ChartRendererUntil;

/**
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartRenderer {

    protected int textSize, textColor, lineColor;
    protected Context context;
    protected Paint rectPaint, linePaint, textPaint;

    public AbsChartRenderer(Context context) {
        this.context = context;

        init();
    }

    private void init() {
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(ChartRendererUntil.CHART_GREEN);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ChartRendererUntil.CHART_GREY);
        linePaint.setStrokeWidth(ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 1));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ChartRendererUntil.CHART_GREY);
        textPaint.setTextSize(ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 15));
    }

    public Context getContext() {
        return context;
    }

    public abstract void prepareCompute();
    public abstract void computeGraph();
    public abstract void drawGraph(Canvas canvas);
}
