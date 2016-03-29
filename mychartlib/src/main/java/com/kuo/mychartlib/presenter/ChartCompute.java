package com.kuo.mychartlib.presenter;

import com.kuo.mychartlib.model.Viewport;

/*
 * Created by User on 2016/3/21.
 */
public class ChartCompute {

    protected Viewport curViewport;
    protected Viewport minViewport;

    protected int touchState;

    protected float curMargin = 0f;

    protected int padding = 20;
    protected int maxTextWidth;
    protected int maxTextHeight;

    protected int chartWidth = 0;
    protected int chartHeight = 0;

    protected float maxValue = 0f;

    public ChartCompute(Viewport minViewport) {
        this.minViewport = minViewport;
        curViewport = new Viewport(minViewport.left, minViewport.top, minViewport.right, minViewport.bottom);
    }

    public void setChartHeight(int chartHeight) {
        this.chartHeight = chartHeight;
    }

    public void setChartWidth(int chartWidth) {
        this.chartWidth = chartWidth;
    }

    public int getChartHeight() {
        return chartHeight;
    }

    public int getChartWidth() {
        return chartWidth;
    }

    public void setMaxTextWidth(int maxTextWidth) {
        this.maxTextWidth = maxTextWidth;
    }

    public int getMaxTextWidth() {
        return maxTextWidth;
    }

    public void setMaxTextHeight(int maxTextHeight) {
        this.maxTextHeight = maxTextHeight;
    }

    public int getMaxTextHeight() {
        return maxTextHeight;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPadding() {
        return padding;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMinViewport(Viewport minViewport) {
        this.minViewport = minViewport;
        curViewport = new Viewport(minViewport.left, minViewport.top, minViewport.right, minViewport.bottom);
    }

    public Viewport getCurViewport() {
        return curViewport;
    }

    public Viewport getMinViewport() {
        return minViewport;
    }

    public void setCurMargin(float curMargin) {
        this.curMargin = curMargin;
    }

    public float getCurMargin() {
        return curMargin;
    }

    public void setTouchState(int touchState) {
        this.touchState = touchState;
    }

    public int getTouchState() {
        return touchState;
    }
}
