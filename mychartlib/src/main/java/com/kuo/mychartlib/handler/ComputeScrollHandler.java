package com.kuo.mychartlib.handler;

import android.content.Context;
import android.util.Log;
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

    private float offsetX, offsetY;

    public static final int SCROLL_MODE = 0;
    public static final int FLING_MODE = 1;

    public int SCROLLER_MODE = -1;

    private Scroller mScroller;
    private OverScroller mOverScroller;
    private ViewConfiguration mViewConfiguration;
    private VelocityTracker mVelocityTracker;

    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;

    private float dX, dY;

    private ChartCompute chartCompute;

    public ComputeScrollHandler(Context context) {

        mScroller = new Scroller(context);

        mViewConfiguration = ViewConfiguration.get(context);

        mScaledMinimumFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();

    }

    protected void setDownPosition(float dX, float dY) {
        this.dX = dX;
        this.dY = dY;
    }

    protected void setPreviousOffest(float x, float y, ChartCompute chartCompute) {
        offsetX = chartCompute.getCurViewport().left - x;
        offsetY = chartCompute.getCurViewport().top - y;
    }

    protected void startScroll(int x, int y, float distanceX, float distanceY, ChartCompute chartCompute) {
        SCROLLER_MODE = SCROLL_MODE;

        //將我們當前View的高度與顯示的高度做除法運算，在乘上當前的X||Y位移的亮，來避免因為點擊而造成位移量亂改變
        float viewportOffsetX = -distanceX * chartCompute.getCurViewport().width() / chartCompute.getMinViewport().width();
        float viewportOffsetY = -distanceY * chartCompute.getCurViewport().height() / chartCompute.getMinViewport().height();

        float left = chartCompute.getCurViewport().left + viewportOffsetX;
        float top = chartCompute.getCurViewport().top + viewportOffsetY;
        float right  = left + chartCompute.getCurViewport().width();
        float bottom = top + chartCompute.getCurViewport().height();

        chartCompute.setCurViewport(left, top, right, bottom);
    }

    protected void startFling(int velocityX, int velocityY, ChartCompute chartCompute) {

        SCROLLER_MODE = FLING_MODE;

        int minX = ((int) chartCompute.getMinViewport().width() + (int) chartCompute.getMinViewport().left)
                - (int) chartCompute.getCurViewport().width();

        int maxX = (int) chartCompute.getMinViewport().left;

        int minY = ((int) chartCompute.getMinViewport().height() + (int) chartCompute.getMinViewport().top)
                - (int) chartCompute.getCurViewport().height();

        int maxY = (int) chartCompute.getMinViewport().top;

        mScroller.fling((int) chartCompute.getCurViewport().left, (int) chartCompute.getCurViewport().top,
                velocityX, velocityY,
                minX, maxX, minY, maxY);
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

    protected void stopForce() {
        mScroller.forceFinished(true);
    }

    protected Scroller getScroller() {
        return mScroller;
    }

    protected int getCurScrollerMode() {
        return SCROLLER_MODE;
    }

}
