package com.kuo.mychartlib.model;

import android.graphics.RectF;

/*
 * Created by Kuo on 2016/3/16.
 */
public class Viewport extends RectF {

    private float minHeight, minWidth, maxHeight, maxWidth;

    public Viewport(float left, float top, float right, float bottom) {
        set(left, top, right, bottom);
    }

    @Override
    public void set(float left, float top, float right, float bottom) {
        super.set(left, top, right, bottom);
    }

    @Override
    public void set(RectF src) {
        super.set(src);
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public float getMinHeight() {
        return minHeight;
    }

    public float getMinWidth() {
        return minWidth;
    }
}
