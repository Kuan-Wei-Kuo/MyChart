package com.kuo.mychartlib.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.until.ChartRendererUntil;
import com.kuo.mychartlib.view.AbsChartView;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    private ComputeScrollHandler computeScrollHandler;

    private ComputeZoomHandler computeZoomHandler;

    private ChartListener chartListener;

    private int touchState = -1;

    private static final int TOUCH_SCROLL = 0;
    private static final int TOUCH_ZOOM = 1;

    public ChartTouchHandler(Context context, ChartListener chartListener) {

        this.chartListener = chartListener;

        computeScrollHandler = new ComputeScrollHandler(context);

        computeZoomHandler = new ComputeZoomHandler(context, chartListener.getOrientation());
    }

    public boolean onTouchEvent(MotionEvent event, final ChartCompute chartCompute) {

        computeScrollHandler.obtainVelocityTracker(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                computeScrollHandler.stopAnimation();

                touchState = TOUCH_SCROLL;

                float offestX = chartCompute.getCurViewport().left - event.getX();
                float offestY = chartCompute.getCurViewport().top - event.getY();

                computeScrollHandler.stopAnimation();
                computeScrollHandler.setPreviousOffest(offestX, offestY);

                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                computeScrollHandler.stopAnimation();

                if (ChartRendererUntil.getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1)) > 10)
                    touchState = TOUCH_ZOOM;

                break;
            case MotionEvent.ACTION_MOVE:

                if (touchState == TOUCH_SCROLL)
                    computeScrollHandler.startScroll(event);
                else if(touchState == TOUCH_ZOOM) {
                    computeZoomHandler.startZoom(event, chartCompute);
                }

                ViewCompat.postInvalidateOnAnimation(chartListener.getView());

                break;
            case MotionEvent.ACTION_UP:

                if(touchState == TOUCH_SCROLL) {

                    computeScrollHandler.computeCurrentVelocity(1000);

                    computeScrollHandler.startFling(chartCompute.getCurViewport().left, chartCompute.getCurViewport().top, chartCompute);

                    computeScrollHandler.releaseVelocityTracker();

                    ViewCompat.postInvalidateOnAnimation(chartListener.getView());

                }

                touchState = -1;

                break;
        }
        return true;
    }

    public Scroller getScroller() {
        return computeScrollHandler.getScroller();
    }

    public int getCurScrollerMode() {
        return computeScrollHandler.getCurScrollerMode();
    }
}
