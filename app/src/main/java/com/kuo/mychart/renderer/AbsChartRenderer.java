package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;

import com.kuo.mychart.until.ChartRendererUntil;

/**
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartRenderer {

    protected int width, height, textSize, textColor, lineColor;
    protected Context context;

    public AbsChartRenderer(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;

        textSize = ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 18);
        textColor = ChartRendererUntil.CHART_GREEN;
        lineColor = ChartRendererUntil.CHART_GREY;
    }

    public abstract void onDraw(Canvas canvas);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Context getContext() {
        return context;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getLineColor() {
        return lineColor;
    }
}
