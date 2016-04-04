package com.kuo.mychartlib.presenter;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.kuo.mychartlib.handler.ComputeZoomHandler;
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

    public void setCurViewport(float left, float top, float right, float bottom) {

        if(left > minViewport.left) {
            left = minViewport.left;
            right = curViewport.right;
        }

        if(right < minViewport.right) {
            left = curViewport.left;
            right = minViewport.right;
        }

        if(top > minViewport.top) {
            top = minViewport.top;
            bottom = curViewport.bottom;
        }

        if(bottom < minViewport.bottom) {
            top = curViewport.top;
            bottom = minViewport.bottom;
        }

        curViewport.left = left;
        curViewport.right = right;
        curViewport.top = top;
        curViewport.bottom = bottom;

    }



    protected int orientation = ComputeZoomHandler.HORIZONTAL_VERTICAL;

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }


}
