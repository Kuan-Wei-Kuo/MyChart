package com.kuo.mychart.handler;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.OverScroller;

import com.kuo.mychart.model.Viewport;
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

    private Viewport startScrollViewport;

    public ChartTouchHandler(Context context, AbsChartView absChartView) {
        this.context = context;
        this.absChartView = absChartView;

        init();
    }

    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());
        overScroller = new OverScroller(context);
    }

    /* Record to point down X value */
    private float offestX;
    private ChartCompute chartCompute;

    private int state = -1;

    private static final int SCROLL = 0;
    private static final int ZOOM = 1;

    public boolean onTouchEvent(MotionEvent event, final ChartCompute chartCompute) {

        this.chartCompute = chartCompute;

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

                        chartCompute.getCurViewport().left = left;
                        chartCompute.getCurViewport().right = right;

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
