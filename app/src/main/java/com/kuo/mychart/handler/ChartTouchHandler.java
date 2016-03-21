package com.kuo.mychart.handler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.view.AbsChartView;

/*
 * Created by Kuo on 2016/3/21.
 */
public class ChartTouchHandler {

    private Context context;
    private ScaleGestureDetector scaleGestureDetector;
    private Viewport chartViewport;
    private AbsChartView absChartView;

    public ChartTouchHandler(Context context, AbsChartView absChartView) {
        this.context = context;
        this.absChartView = absChartView;
        chartViewport = absChartView.getViewport();
    }

    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());
    }

    /* Record to point down X value */
    private float offestX;

    public boolean onTouchEvent(MotionEvent event) {

        if(event.getPointerCount() > 1) {

            scaleGestureDetector.onTouchEvent(event);

        } else {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    offestX = event.getX();

                    break;
                case MotionEvent.ACTION_MOVE:

                    float left = event.getX() + offestX;
                    float right = left + chartViewport.width();

                    chartViewport.set(left, chartViewport.top, right, chartViewport.bottom);

                    ViewCompat.postInvalidateOnAnimation(absChartView);
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

            float newWidth = scale * chartViewport.width();

            float offestX = (detector.getFocusX() - chartViewport.left) * scale - (detector.getFocusX() - chartViewport.left);

            float left = chartViewport.left - offestX;

            float right = left + newWidth;

            if (right - left < chartViewport.width()) {
                right = left + chartViewport.width();
                if (left < 0) {
                    left = 0;
                    right = left + chartViewport.width();
                } else if (right > chartViewport.width()) {
                    right = chartViewport.width();
                    left = right - chartViewport.width();
                }
            }

            chartViewport.left = Math.min(chartViewport.left, left);
            chartViewport.right = Math.min(chartViewport.right, right);

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
