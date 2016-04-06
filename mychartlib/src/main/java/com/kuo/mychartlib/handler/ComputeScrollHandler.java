package com.kuo.mychartlib.handler;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/4/1.
 */
public class ComputeScrollHandler{

    private float offestX, offestY;

    public static final int SCROLL_MODE = 0;
    public static final int FLING_MODE = 1;

    public int SCROLLER_MODE = -1;

    private Scroller mScroller;
    private OverScroller mOverScroller;
    private ViewConfiguration mViewConfiguration;
    private VelocityTracker mVelocityTracker;

    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;

    private GestureDetector gestureDetector;

    private ChartCompute chartCompute;

    public ComputeScrollHandler(Context context) {

        mScroller = new Scroller(context);
        mOverScroller = new OverScroller(context);

        mViewConfiguration = ViewConfiguration.get(context);

        mScaledMinimumFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();

        gestureDetector = new GestureDetector(context, new ChartGestureDetector());
    }

    protected void setPreviousOffest(float offestX, float offestY) {
        this.offestX = offestX;
        this.offestY = offestY;
    }

    protected void startScroll(MotionEvent event) {
        SCROLLER_MODE = SCROLL_MODE;
        mScroller.startScroll((int) event.getX(), (int) event.getY(), (int) offestX, (int) offestY, 1);
    }

    protected boolean startOnTouch(MotionEvent event, ChartCompute chartCompute) {
        this.chartCompute = chartCompute;
        return gestureDetector.onTouchEvent(event);
    }

    protected void startFling(float startX, float startY, ChartCompute chartCompute) {

        SCROLLER_MODE = FLING_MODE;

        int initialVelocity = (int) mVelocityTracker.getXVelocity();

        if (Math.abs(initialVelocity) > mScaledMinimumFlingVelocity) {

            int minX = ((int) chartCompute.getMinViewport().width() + (int) chartCompute.getMinViewport().left)
                    - (int) chartCompute.getCurViewport().width();

            int maxX = (int) chartCompute.getMinViewport().left;

            int minY = ((int) chartCompute.getMinViewport().height() + (int) chartCompute.getMinViewport().top)
                    - (int) chartCompute.getCurViewport().height();

            int maxY = (int) chartCompute.getMinViewport().top;

            mScroller.fling((int) startX, (int) startY,
                    (int) mVelocityTracker.getXVelocity(), (int) mVelocityTracker.getYVelocity(),
                    minX, maxX, minY, maxY);

        }
    }

    protected void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    protected void computeCurrentVelocity(int units) {
        mVelocityTracker.computeCurrentVelocity(units, mScaledMaximumFlingVelocity);
    }

    protected void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    protected void stopAnimation() {
        mScroller.abortAnimation();
    }

    protected Scroller getScroller() {
        return mScroller;
    }

    protected int getCurScrollerMode() {
        return SCROLLER_MODE;
    }

    private float offsetX, offsetY;
    private float lastX, lastY;

    public class ChartGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            mScroller.forceFinished(true);

            offsetX = chartCompute.getCurViewport().left - e.getX();
            offsetY = chartCompute.getCurViewport().top - e.getY();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            mScroller.startScroll((int) e2.getX(), (int) e2.getY(),
                    (int) (offsetX), (int) (offsetY), 1);

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(velocityX > mScaledMinimumFlingVelocity) {

                SCROLLER_MODE = FLING_MODE;

                int minX = ((int) chartCompute.getMinViewport().width() + (int) chartCompute.getMinViewport().left)
                        - (int) chartCompute.getCurViewport().width();

                int maxX = (int) chartCompute.getMinViewport().left;

                int minY = ((int) chartCompute.getMinViewport().height() + (int) chartCompute.getMinViewport().top)
                        - (int) chartCompute.getCurViewport().height();

                int maxY = (int) chartCompute.getMinViewport().top;

                mScroller.forceFinished(true);

                mScroller.fling((int) chartCompute.getCurViewport().left, (int) chartCompute.getCurViewport().top,
                        (int) velocityX, (int) velocityY,
                        minX, maxX, minY, maxY);
            }

            return true;
        }
    }
}
