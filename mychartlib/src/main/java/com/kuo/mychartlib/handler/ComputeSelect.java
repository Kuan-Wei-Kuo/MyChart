package com.kuo.mychartlib.handler;

import android.view.MotionEvent;

import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.until.ChartRendererUntil;

/**
 * Created by Kuo on 2016/4/8.
 */
public class ComputeSelect {

    private float lastX, lastY;

    public boolean onTouchEvent(MotionEvent e, SelectData selectData) {
        return computeSelect(e, selectData);
    }

    public boolean computeSelect(MotionEvent e, SelectData selectData) {

        boolean canSelect = false;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = e.getX();
                lastY = e.getY();

                //selectData.setSelectPoint(lastX, lastY);
                selectData.setSelect(false);

                break;
            case MotionEvent.ACTION_MOVE:

                selectData.setSelectPoint(0, 0);

                break;
            case MotionEvent.ACTION_UP:

                if(ChartRendererUntil.getDistance(lastX, lastY, e.getX(), e.getY()) < 10) {

                    selectData.setSelectPoint(e.getX(), e.getY());
                    selectData.setSelect(true);

                    //if(selectData.getLastPosition() == selectData.getPosition()) {
                      //  selectData.resetSelectPosition();
                    //}

                    canSelect = true;
                }

                break;
        }

        return canSelect;
    }

}
