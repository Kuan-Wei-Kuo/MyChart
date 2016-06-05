package com.kuo.mychartlib.handler;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;

/**
 * Compute scroll and horizontal or vertical.
 *
 * @author Kuo
 */
public class ComputeScroll {

    private Scroller mScroller;

    public ComputeScroll(Context context) {
        mScroller = new Scroller(context);
    }

    private static final int SLIDE_THRESHOLD = 10;

    private boolean canScrollX = false;
    private boolean canScrollY = false;
    
    protected boolean startScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, ChartCompute chartCompute) {

        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();

        float left = curViewport.left - distanceX;
        float top = curViewport.top - distanceY;
        float right  = left + curViewport.width();
        float bottom = top + curViewport.height();

        canScrollX = false;
        canScrollY = false;

        if(curViewport.left < minViewport.left && distanceX <= 0) {
            canScrollX = true;
        } else if(curViewport.right > minViewport.right && distanceX >= 0) {
            canScrollX = true;
        }

        if(curViewport.top < minViewport.top && distanceY <= 0) {
            canScrollY = true;
        } else if(curViewport.bottom > minViewport.bottom && distanceY >= 0) {
            canScrollY = true;
        }


        /**
         * For ScrollView and ViewPager with chart, I need to check horizontal or vertical.
         *
         * */
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SLIDE_THRESHOLD && canScrollX && !canScrollY) {
                canScrollY = true;
            }
        } else if (Math.abs(diffX) < Math.abs(diffY))  {
            if (Math.abs(diffY) > SLIDE_THRESHOLD && !canScrollX && canScrollY) {
                canScrollX = true;
            }
        }

        if(canScrollY || canScrollX)
            chartCompute.containsCurrentViewport(left, top, right, bottom);

        return canScrollX || canScrollY;
    }

    /**
     * Compute max and min rang to start fling.
     * */
    protected void startFling(int velocityX, int velocityY, ChartCompute chartCompute) {

        float minX = chartCompute.getMinViewport().width() + (int) chartCompute.getMinViewport().left
                - chartCompute.getCurViewport().width();

        float maxX = chartCompute.getMinViewport().left;

        float minY = chartCompute.getMinViewport().height() + chartCompute.getMinViewport().top
                - chartCompute.getCurViewport().height();

        float maxY = chartCompute.getMinViewport().top;

        mScroller.fling((int) chartCompute.getCurViewport().left, (int) chartCompute.getCurViewport().top,
                velocityX, velocityY,
                (int) minX, (int) maxX, (int) minY, (int) maxY);
    }

    protected boolean computeFling(ChartCompute chartCompute) {

        if(mScroller.computeScrollOffset()) {

            float left, right, top, bottom;

            left = mScroller.getCurrX();
            top = mScroller.getCurrY();
            right = left + chartCompute.getCurViewport().width();
            bottom = top + chartCompute.getCurViewport().height();

            chartCompute.setCurViewport(left, top, right, bottom);

            return true;
        }

        return false;
    }

    protected void stopAnimation() {
        mScroller.abortAnimation();
    }

    public boolean isCanScrollX() {
        return canScrollX;
    }

    public boolean isCanScrollY() {
        return canScrollY;
    }

    protected Scroller getScroller() {
        return mScroller;
    }

}
