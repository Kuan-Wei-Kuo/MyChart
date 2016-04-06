package com.kuo.mychartlib.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.Scroller;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    private ComputeScrollHandler computeScrollHandler;

    private ComputeZoomHandler computeZoomHandler;

    private ChartListener chartListener;

    public static final int TOUCH_NONE = -1;
    public static final int TOUCH_SCROLL = 0;
    public static final int TOUCH_ZOOM = 1;

    public static final int TOUCH_SCALE_SCROLL = 0;

    private int touchType = -1;
    private int touchState = TOUCH_NONE;

    private boolean enable = true;

    private GestureDetector gestureDetector;
    private ViewParent viewParent;

    public ChartTouchHandler(Context context, ChartListener chartListener) {

        this.chartListener = chartListener;

        computeScrollHandler = new ComputeScrollHandler(context);

        computeZoomHandler = new ComputeZoomHandler(context, chartListener.getOrientation());

        gestureDetector = new GestureDetector(context, new ChartGestureDetector());
    }

    private ChartCompute chartCompute;

    public boolean onTouchEvent(MotionEvent event, final ChartCompute chartCompute) {
        this.chartCompute = chartCompute;
        if(enable) {

            boolean isInvalidate = computeZoomHandler.startZoom(event, chartCompute);

            gestureDetector.onTouchEvent(event);
            //if(!computeZoomHandler.isScale())


            if(isInvalidate) {
                ViewCompat.postInvalidateOnAnimation(chartListener.getView());
            }
        }

        return true;
    }

    public Scroller getScroller() {
        return computeScrollHandler.getScroller();
    }

    public int getTouchState() {
        return touchState;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public class ChartGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            Log.d("onDown", "onDown");

            touchState = 0;

            computeScrollHandler.stopForce();
            computeScrollHandler.setDownPosition(e.getX(), e.getY());
            computeScrollHandler.setPreviousOffest(e.getX(), e.getY(), chartCompute);

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("onScroll", "onScroll");

            computeScrollHandler.startScroll((int) e2.getX(), (int) e2.getY(), distanceX, distanceY, chartCompute);
            computeScrollHandler.setDownPosition(e2.getX(), e2.getY());

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.d("onFling", "onFling");

            touchState = -1;
            computeScrollHandler.stopForce();
            computeScrollHandler.startFling((int) velocityX, (int) velocityY, chartCompute);

            return true;
        }
    }
}
