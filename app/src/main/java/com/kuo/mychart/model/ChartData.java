package com.kuo.mychart.model;

/**
 * Created by Kuo on 2016/3/22.
 */
public class ChartData {

    protected float value;

    protected int valueColor;

    protected String valueName;

    public ChartData(String valueName, float value, int valueColor) {
        this.valueName = valueName;
        this.value = value;
        this.valueColor = valueColor;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setValueColor(int valueColor) {
        this.valueColor = valueColor;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public float getValue() {
        return value;
    }

    public int getValueColor() {
        return valueColor;
    }

    public String getValueName() {
        return valueName;
    }
}
