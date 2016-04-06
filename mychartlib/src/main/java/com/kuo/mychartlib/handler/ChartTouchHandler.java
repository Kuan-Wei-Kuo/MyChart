package com.kuo.mychartlib.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
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

    public ChartTouchHandler(Context context, ChartListener chartListener) {

        this.chartListener = chartListener;

        computeScrollHandler = new ComputeScrollHandler(context);

        computeZoomHandler = new ComputeZoomHandler(context, chartListener.getOrientation());
    }

    public boolean onTouchEvent(MotionEvent event, final ChartCompute chartCompute) {

        if(enable) {

            boolean isInvalidate = computeZoomHandler.startZoom(event, chartCompute);

            if(!computeZoomHandler.isScale())
                computeScrollHandler.startOnTouch(event, chartCompute);

                computeScrollHandler.obtainVelocityTracker(event);

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    
                    touchState = TOUCH_SCROLL;

                    float offsetX = chartCompute.getCurViewport().left - event.getX();
                    float offsetY = chartCompute.getCurViewport().top - event.getY();

                    computeScrollHandler.stopAnimation();
                    computeScrollHandler.setPreviousOffest(offsetX, offsetY);

                    break;

                case MotionEvent.ACTION_POINTER_DOWN:

                    touchState = TOUCH_ZOOM;

                    break;

                case MotionEvent.ACTION_MOVE:

                    if(touchState == TOUCH_SCROLL) {

                        //computeScrollHandler.startScroll(event);

                        isInvalidate = true;
                    }

                    break;

                case MotionEvent.ACTION_UP:

                    if(touchState != TOUCH_SCROLL) {

                        computeScrollHandler.computeCurrentVelocity(1000);

                        computeScrollHandler.startFling(chartCompute.getCurViewport().left, chartCompute.getCurViewport().top, chartCompute);

                        computeScrollHandler.releaseVelocityTracker();

                        isInvalidate = true;
                    }

                    touchState = TOUCH_NONE;

                    break;
            }

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

    public void setTouchType(int touchType) {
        this.touchType = touchType;
    }
}
