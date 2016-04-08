package com.kuo.mychartlib.model;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/*
 * Created by Kuo on 2016/4/8.
 */
public class SelectData {

    boolean isSelect = false;

    PointF selectPoint;

    RectF rectF = new RectF();

    int position = -1;

    public SelectData() {
        selectPoint = new PointF(0, 0);
    }

    public void setSelectPoint(float x, float y) {
        selectPoint.set(x, y);
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

    public void setPosition(int position) {

        Log.d("this", this.position +"");
        Log.d("po", position +"");

        if(this.position == position) {
            this.position = -1;
            selectPoint.set(0, 0);
        } else
            this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
