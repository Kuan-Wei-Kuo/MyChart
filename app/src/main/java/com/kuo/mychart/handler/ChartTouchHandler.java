package com.kuo.mychart.handler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.widget.OverScroller;

import com.kuo.mychart.animation.ChartAnimation;
import com.kuo.mychart.presenter.ChartCompute;
import com.kuo.mychart.view.AbsChartView;

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

        init();
    }

    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());
        overScroller = new OverScroller(context);
        chartAnimation = new ChartAnimation(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float left = (float) animation.getAnimatedValue("phaseX");
                float right = left + chartCompute.getCurViewport().width();

                if(left > chartCompute.getMinViewport().left) {
                    left = chartCompute.getMinViewport().left;
                    right = chartCompute.getCurViewport().right;
                }

                if(right < chartCompute.getMinViewport().right) {
                    left = chartCompute.getCurViewport().left;
                    right = chartCompute.getMinViewport().right;
                }

                chartCompute.getCurViewport().left = left;
                chartCompute.getCurViewport().right = right;

                ViewCompat.postInvalidateOnAnimation(absChartView);
            }
        });
    }

    /* Record to point down X value */
    private float offestX;
    private ChartCompute chartCompute;

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
            return scaleGestureDetector.onTouchEvent(event);
        } else {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    state = SCROLL;
                    offestX = chartCompute.getCurViewport().left - event.getX();

                    break;
                case MotionEvent.ACTION_MOVE:

                    if(state == SCROLL) {
                        float left = event.getX() + offestX;
                        float right = left + chartCompute.getCurViewport().width();

                        if(left > chartCompute.getMinViewport().left) {
                            left = chartCompute.getMinViewport().left;
                            right = chartCompute.getCurViewport().right;
                        }

                        if(right < chartCompute.getMinViewport().right) {
                            left = chartCompute.getCurViewport().left;
                            right = chartCompute.getMinViewport().right;
                        }

                        if(left > chartCompute.getCurViewport().left) {
                            slide = SLIDE_RIGHT;
                        } else {
                            slide = SLIDE_LEFT;
                        }

                        chartCompute.getCurViewport().left = left;
                        chartCompute.getCurViewport().right = right;

                        velocityTracker.computeCurrentVelocity(1, 2f);

                        ViewCompat.postInvalidateOnAnimation(absChartView);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(state == SCROLL) {
                        if(Math.abs(velocityTracker.getXVelocity()) > 1.5f && velocityTracker.getXVelocity() < 0) {
                            chartAnimation.animateX(250, chartCompute.getCurViewport().left, chartCompute.getCurViewport().left - 1000);
                        } else if(Math.abs(velocityTracker.getXVelocity()) > 1.5f && velocityTracker.getXVelocity() > 0) {
                            chartAnimation.animateX(250, chartCompute.getCurViewport().left, chartCompute.getCurViewport().left +
                                    1000);
                        }
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

            float newWidth = scale * chartCompute.getCurViewport().width();

            float offestX = detector.getFocusX() - chartCompute.getCurViewport().left;
            offestX = offestX * scale - offestX;

            float left = chartCompute.getCurViewport().left - offestX;

            float right = left + newWidth;

            if (right - left < chartCompute.getMinViewport().width()) {
                right = left + chartCompute.getMinViewport().width();
                if (left < 0) {
                    left = 0;
                    right = left + chartCompute.getMinViewport().width();
                } else if (right > chartCompute.getMinViewport().width()) {
                    right = chartCompute.getMinViewport().width();
                    left = right - chartCompute.getMinViewport().width();
                }
            }

            chartCompute.getCurViewport().left = Math.min(chartCompute.getMinViewport().left, left);
            chartCompute.getCurViewport().right = Math.max(chartCompute.getMinViewport().right, right);

            ViewCompat.postInvalidateOnAnimation(absChartView);

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

}
