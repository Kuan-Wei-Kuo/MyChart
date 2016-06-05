package com.kuo.mychartlib.handler;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewParent;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    public static final int HORIZONTAL_VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation = HORIZONTAL_VERTICAL;

    protected ComputeScroll computeScroll;

    protected ComputeZoom computeZoom;

    protected ComputeSelect computeSelect;

    protected GestureDetector gestureDetector;

    protected ScaleGestureDetector scaleGestureDetector;

    protected ChartListener chartListener;

    protected ChartCompute chartCompute;

    protected SelectData selectData;

    protected boolean enable = true;

    protected ViewParent viewParent;

    public ChartTouchHandler(Context context, ChartListener chartListener) {

        this.chartListener = chartListener;

        computeScroll = new ComputeScroll(context);

        computeZoom = new ComputeZoom();

        gestureDetector = new GestureDetector(context, new ChartGestureDetector());

        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());

        computeSelect = new ComputeSelect();

        chartCompute = chartListener.getChartCompute();

        orientation = chartListener.getOrientation();

        selectData = chartListener.getSelectData();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean onTouchEvent(MotionEvent event, ViewParent viewParent) {

        this.viewParent = viewParent;

        boolean isInvalidate = false;

        if(enable) {

            isInvalidate = gestureDetector.onTouchEvent(event);
            isInvalidate = scaleGestureDetector.onTouchEvent(event) || isInvalidate;

            isInvalidate = computeSelect.onTouchEvent(event, chartListener.getSelectData()) || isInvalidate;

            if(scaleGestureDetector.isInProgress())
                disallowParentInterceptTouchEvent();

        }

        return isInvalidate;
    }

    private void disallowParentInterceptTouchEvent() {

        if (viewParent != null)
            viewParent.requestDisallowInterceptTouchEvent(true);

    }

    private void allowParentInterceptTouchEvent() {

        if (viewParent != null) {

            if (!computeScroll.isCanScrollX()
                    && !scaleGestureDetector.isInProgress()) {
                viewParent.requestDisallowInterceptTouchEvent(false);
            } else if (!computeScroll.isCanScrollY()
                    && !scaleGestureDetector.isInProgress()) {
                viewParent.requestDisallowInterceptTouchEvent(false);
            }
        }
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

            float scale = detector.getScaleFactor();

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
    }

    public class ChartGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            computeScroll.stopAnimation();
            disallowParentInterceptTouchEvent();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            boolean isScroll = computeScroll.startScroll(e1, e2, distanceX, distanceY, chartCompute);

            allowParentInterceptTouchEvent();

            return isScroll;


        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            computeScroll.stopAnimation();
            computeScroll.startFling((int) velocityX, (int) velocityY, chartCompute);

            return true;
        }

    }

}
