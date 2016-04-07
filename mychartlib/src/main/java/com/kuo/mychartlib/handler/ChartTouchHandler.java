package com.kuo.mychartlib.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    public static final int HORIZONTAL_VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation = HORIZONTAL_VERTICAL;

    private ComputeScroll computeScroll;

    private ComputeZoom computeZoom;

    private GestureDetector gestureDetector;

    private ScaleGestureDetector scaleGestureDetector;

    private ChartListener chartListener;

    private ChartCompute chartCompute;

    private boolean enable = true;

    public ChartTouchHandler(Context context, ChartListener chartListener) {

        this.chartListener = chartListener;

        computeScroll = new ComputeScroll(context);

        computeZoom = new ComputeZoom();

        gestureDetector = new GestureDetector(context, new ChartGestureDetector());

        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());

        chartCompute = chartListener.getChartCompute();
    }

    public boolean onTouchEvent(MotionEvent event) {

        if(enable) {
            boolean isInvalidate = scaleGestureDetector.onTouchEvent(event) && gestureDetector.onTouchEvent(event);

            if(isInvalidate)
                ViewCompat.postInvalidateOnAnimation(chartListener.getView());

            return true;
        }

        return false;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean compueScroll() {
        return enable && computeScroll.computeFling(chartCompute);
    }

    public class ChartScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scale = computeZoom.getScale(detector.getCurrentSpan());

            switch (orientation) {
                case HORIZONTAL_VERTICAL:
                    computeZoom.computeZoom(scale, detector.getFocusX(), detector.getFocusY(), chartCompute);
                    break;
                case HORIZONTAL:
                    computeZoom.computeHorizontalZoom(scale, detector.getFocusX(), detector.getFocusY(), chartCompute);
                    break;
                case VERTICAL:
                    computeZoom.computeVerticalZoom(scale, detector.getFocusX(), detector.getFocusY(), chartCompute);
                    break;

            }

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            computeZoom.setLastSpan(detector.getCurrentSpan());

            return true;
        }

    }

    public class ChartGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            computeScroll.stopAnimation();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            computeScroll.startScroll(distanceX, distanceY, chartCompute);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            computeScroll.stopAnimation();
            computeScroll.startFling((int) velocityX, (int) velocityY, chartCompute);

            return true;
        }

    }
}
