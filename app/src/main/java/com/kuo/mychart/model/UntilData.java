package com.kuo.mychart.model;

/**
 * Created by Kuo on 2016/3/7.
 */
public class UntilData {

    private int point, color;
    private String labelText;

    public UntilData(int point, String labelText, int color) {
        this.point = point;
        this.labelText = labelText;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getPoint() {
        return point;
    }

    public String getLabelText() {
        return labelText;
    }
}
