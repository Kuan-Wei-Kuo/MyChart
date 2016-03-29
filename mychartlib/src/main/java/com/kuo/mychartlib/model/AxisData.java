package com.kuo.mychartlib.model;

/**
 * Created by Kuo on 2016/3/29.
 */
public class AxisData {

    protected String axis;
    protected boolean isEnable = false;

    public float x, y;

    public AxisData(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getAxis() {
        return axis;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
