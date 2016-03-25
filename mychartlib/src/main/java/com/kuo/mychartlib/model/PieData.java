package com.kuo.mychartlib.model;

/**
 * Created by User on 2016/2/28.
 */
public class PieData {

    private float persent, point;
    private float angle;

    private String lableText;

    private int color, imageSrc;

    public PieData(float point, int color, String lableText, int imageSrc) {
        this.point = point;
        this.color = color;
        this.imageSrc = imageSrc;
    }

    public void setPersent(float persent) {
        this.persent = persent;
    }

    public float getPersent() {
        return persent;
    }

    public float getPoint() {
        return point;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getColor() {
        return color;
    }

    public float getAngle() {
        return angle;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public String getLableText() {
        return lableText;
    }
}
