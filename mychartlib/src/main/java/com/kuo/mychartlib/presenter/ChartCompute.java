package com.kuo.mychartlib.presenter;

import com.kuo.mychartlib.handler.ChartTouchHandler;
import com.kuo.mychartlib.model.Viewport;

/*
 * Created by User on 2016/3/21.
 */
public class ChartCompute {

    protected Viewport curViewport;
    protected Viewport minViewport;

    protected float curMargin = 0f;

    protected int padding = 20;

    protected int maxTextWidth;
    protected int maxTextHeight;

    protected int chartWidth = 0;
    protected int chartHeight = 0;

    protected float maxValue = 0f;

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

    public void containsCurrentViewport(float left, float top, float right, float bottom) {

        float curWidth = curViewport.width();
        float curHeight = curViewport.height();

        if (left > minViewport.left) {
            left = minViewport.left;
            right = left + curWidth;
        } else if (right < minViewport.right) {
            right = minViewport.right;
            left = right - curWidth;
        }

        if (top > minViewport.top) {
            top = minViewport.top;
            bottom = top + curHeight;
        } else if (bottom < minViewport.bottom) {
            bottom = minViewport.bottom;
            top = bottom - curHeight;
        }

        curViewport.set(left, top, right, bottom);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setCurViewport(float left, float top, float right, float bottom) {

        float curW = curViewport.width();
        float curH = curViewport.height();

        if (left > minViewport.left) {
            left = minViewport.left;
            right = left + curW;
        } else if (right < minViewport.right) {
            right = minViewport.right;
            left = right - curW;
        }

        if (top > minViewport.top) {
            top = minViewport.top;
            bottom = top + curH;
        } else if (bottom < minViewport.bottom) {
            bottom = minViewport.bottom;
            top = bottom - curH;
        }

        curViewport.left = left;
        curViewport.right = right;
        curViewport.top = top;
        curViewport.bottom = bottom;

    }

    protected int orientation = ChartTouchHandler.HORIZONTAL_VERTICAL;

    public int getOrientation() {
        return orientation;
    }

}
