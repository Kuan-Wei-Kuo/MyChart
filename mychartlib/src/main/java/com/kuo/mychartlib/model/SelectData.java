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

    int position = -1;

    int lastPosition = -2;

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

        if(position == this.position) {
            lastPosition = this.position;
        } else {
            this.position = position;
        }

    }

    public boolean computeNowPosition() {

        boolean canSelect;

        //Log.d("po", position + "");
        //Log.d("lpo", lastPosition + "");

        if(lastPosition == position) {
            canSelect = false;
            resetSelect();
        } else {
            canSelect = true;
        }

        return canSelect;
    }

    public int getPosition() {
        return position;
    }

    public int getLastPosition() {
        return lastPosition;
    }


    public void resetSelect() {
        selectPoint.set(0, 0);
        position = -1;
    }

}
