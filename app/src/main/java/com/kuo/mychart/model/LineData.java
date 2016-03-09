package com.kuo.mychart.model;

/**
 * Created by Kuo on 2016/3/7.
 */
public class LineData extends UntilData{

    private String axisX = "AxisX";

    public LineData(int point, String labelText, int color) {
        super(point, labelText, color);
    }

    public void setAxisX(String axisX) {
        this.axisX = axisX;
    }

    public String getAxisX() {
        return axisX;
    }
}
