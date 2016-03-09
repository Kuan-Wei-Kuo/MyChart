package com.kuo.mychart.model;

/**
 * Created by Kuo on 2016/3/7.
 */
public class BarData extends UntilData{

    private String axisX = "AxisX";

    public BarData(int point, String labelText, int color) {
        super(point, labelText, color);
    }

    public void setAxisX(String axisX) {
        this.axisX = axisX;
    }

    public String getAxisX() {
        return axisX;
    }
}
