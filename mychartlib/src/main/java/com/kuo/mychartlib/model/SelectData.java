package com.kuo.mychartlib.model;

import android.graphics.PointF;

/*
 * Created by Kuo on 2016/4/8.
 */
public class SelectData {

    boolean isSelect = false;

    PointF selectPoint;

    int position = -1;

    int lastPosition = -2;

    public SelectData() {
        selectPoint = new PointF(0, 0);
    }

    public void setSelectPoint(float x, float y) {
        selectPoint.set(x, y);
    }

    public void setPosition(int position) {

        if(position == this.position) {
            lastPosition = this.position;
        } else {
            this.position = position;
        }

    }

    public void resetSelect() {
        selectPoint.set(0, 0);
        position = -1;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public float getX() {
        return selectPoint.x;
    }

    public float getY() {
        return selectPoint.y;
    }

    public int getPosition() {
        return position;
    }

    public int getLastPosition() {
        return lastPosition;
    }

}
