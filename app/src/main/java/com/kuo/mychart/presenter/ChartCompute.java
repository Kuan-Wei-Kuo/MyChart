package com.kuo.mychart.presenter;

import com.kuo.mychart.model.Viewport;

/**
 * Created by User on 2016/3/21.
 */
public class ChartCompute {

    protected Viewport curViewport;
    protected Viewport minViewport;

    protected int topPadding = 20;
    protected int maxTextWidth;
    protected int maxTextHeight;

    protected float maxValue = 0f;

    public ChartCompute(Viewport minViewport) {
        this.minViewport = minViewport;
        curViewport = new Viewport(minViewport.left, minViewport.top, minViewport.right, minViewport.bottom);
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

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public int getTopPadding() {
        return topPadding;
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
}
