package com.kuo.mychartlib.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.kuo.mychartlib.animation.ChartAnimation;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.view.AbsChartView;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    private Context context;
    private ScaleGestureDetector scaleGestureDetector;
    private OverScroller overScroller;
    private AbsChartView absChartView;
    private ChartAnimation chartAnimation;

    public ChartTouchHandler(Context context, AbsChartView absChartView) {
        this.context = context;
        this.absChartView = absChartView;
        computeScrollHandler = new ComputeScrollHandler(context);
        init();
    }

    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());
        overScroller = new OverScroller(context);
    }

    /* Record to point down X value */
    private float offestX, offestY;
    private ChartCompute chartCompute;
    private ComputeScrollHandler computeScrollHandler;

    /* 追蹤滑動速度 */
    private VelocityTracker velocityTracker = VelocityTracker.obtain();

    private int state = -1;

    private static final int SCROLL = 0;
    private static final int ZOOM = 1;

    private int slide = -1;
    private static final int SLIDE_RIGHT = 0;
    private static final int SLIDE_LEFT = 1;

    public boolean onTouchEvent(MotionEvent event, final ChartCompute chartCompute) {

        this.chartCompute = chartCompute;

        if (velocityTracker == null)
            velocityTracker = VelocityTracker.obtain();

        velocityTracker.addMovement(event);

        if(event.getPointerCount() > 1) {
            state = ZOOM;
            chartCompute.setTouchState(ZOOM);
            return scaleGestureDetector.onTouchEvent(event);
        } else {

            computeScrollHandler.obtainVelocityTracker(event);

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    state = SCROLL;
                    float offestX = chartCompute.getCurViewport().left - event.getX();
                    float offestY = chartCompute.getCurViewport().top - event.getY();

                    computeScrollHandler.setPreviousOffest(offestX, offestY);

                    break;
                case MotionEvent.ACTION_MOVE:

                    if(state == SCROLL) {
                        chartCompute.setTouchState(SCROLL);
                        computeScrollHandler.startScroll(event);
                        ViewCompat.postInvalidateOnAnimation(absChartView);
                    }

                    break;
                case MotionEvent.ACTION_UP:

                    if(state == SCROLL) {
                        computeScrollHandler.computeCurrentVelocity(1000);
                        computeScrollHandler.startFling(chartCompute.getCurViewport().left, chartCompute.getCurViewport().top, chartCompute);
                        computeScrollHandler.releaseVelocityTracker();
                        ViewCompat.postInvalidateOnAnimation(absChartView);
                    }
                    break;
            }
        }
        return true;
    }

    public class ChartScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scale = detector.getScaleFactor();
            if (Float.isInfinite(scale)) {
                scale = 1;
            }

            if(detector.getCurrentSpanX() > detector.getCurrentSpanY()) {

                float newWidth = scale * chartCompute.getCurViewport().width();

                float offestX = detector.getFocusX() - chartCompute.getCurViewport().left;
                offestX = offestX * scale - offestX;

                float left = chartCompute.getCurViewport().left - offestX;

                float right = left + newWidth;

                if (right - left < chartCompute.getMinViewport().width()) {
                    right = left + chartCompute.getMinViewport().width();
                    if (left < chartCompute.getMinViewport().left) {
                        left = chartCompute.getMinViewport().left;
                        right = left + chartCompute.getMinViewport().width();
                    } else if (right > chartCompute.getMinViewport().right) {
                        right = chartCompute.getMinViewport().right;
                        left = right - chartCompute.getMinViewport().width();
                    }
                }

                chartCompute.getCurViewport().left = Math.min(chartCompute.getMinViewport().left, left);
                chartCompute.getCurViewport().right = Math.max(chartCompute.getMinViewport().right, right);

                ViewCompat.postInvalidateOnAnimation(absChartView);

            } else {

                float newHeight = scale * chartCompute.getCurViewport().height();

                float offestX = detector.getFocusX() - chartCompute.getCurViewport().top;
                offestX = offestX * scale - offestX;

                float top = chartCompute.getCurViewport().top - offestX;
                float bottom = top + newHeight;

                if (bottom - top < chartCompute.getMinViewport().height()) {
                    bottom = top + chartCompute.getMinViewport().height();
                    if (top < chartCompute.getMinViewport().top) {
                        top = chartCompute.getMinViewport().top;
                        bottom = top + chartCompute.getMinViewport().height();
                    } else if (bottom > chartCompute.getMinViewport().bottom) {
                        bottom = chartCompute.getMinViewport().bottom;
                        top = bottom - chartCompute.getMinViewport().height();
                    }
                }

                chartCompute.getCurViewport().top = Math.min(chartCompute.getMinViewport().top, top);
                chartCompute.getCurViewport().bottom = Math.max(chartCompute.getMinViewport().bottom, bottom);

                ViewCompat.postInvalidateOnAnimation(absChartView);
            }

            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

    }

    public Scroller getScroller() {
        return computeScrollHandler.getScroller();
    }

    public int getCurScrollerMode() {
        return computeScrollHandler.getCurScrollerMode();
    }
}
