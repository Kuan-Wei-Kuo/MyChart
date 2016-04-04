package com.kuo.mychartlib.model;

/**
 * Created by User on 2016/2/28.
 */
public class PieData extends ChartData{

    private float persent;
    private float angle;

    public PieData(String valueName, float value, int valueColor) {
        super(valueName, value, valueColor);
    }

    public void setPersent(float persent) {
        this.persent = persent;
    }

    public float getPersent() {
        return persent;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
