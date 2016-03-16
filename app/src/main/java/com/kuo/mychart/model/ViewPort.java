package com.kuo.mychart.model;

import android.graphics.RectF;

/**
 * Created by Kuo on 2016/3/16.
 */
public class ViewPort extends RectF {

    private boolean isScaleEnable = true;

    public ViewPort(float left, float top, float right, float bottom) {
        set(left, top, right, bottom);
    }

    public void setIsScaleEnable(boolean isScaleEnable) {
        this.isScaleEnable = isScaleEnable;
    }

    @Override
    public void set(float left, float top, float right, float bottom) {
        super.set(left, top, right, bottom);
    }

    public boolean isScaleEnable() {
        return isScaleEnable;
    }
}
