package com.kuo.mychartlib.handler;

import android.content.Context;
import android.widget.Scroller;

import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/4/1.
 */
public class ComputeScroll {

    private Scroller mScroller;

    public ComputeScroll(Context context) {
        mScroller = new Scroller(context);
    }

    float OffsetX = 0;
    float OffsetY = 0;
    /**
     * 這邊並不打算使用Scroller去做滑動的運算，直接算出當前位置可以避免一些問題。
     * */
    protected void startScroll(float distanceX, float distanceY, ChartCompute chartCompute) {

        //為了多點觸碰的問題，我們滑動時使用distance來做主要方法。

        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();

        float left = curViewport.left - distanceX;
        float top = curViewport.top - distanceY;
        float right  = left + curViewport.width();
        float bottom = top + curViewport.height();

        boolean enableX = false;
        boolean enableY = false;

        if(curViewport.left < minViewport.left && distanceX <= 0) {
            enableX = true;
        } else if(curViewport.right > minViewport.right && distanceX >= 0) {
            enableX = true;
        }

        if(curViewport.top < minViewport.top && distanceY <= 0) {
            enableY = true;
        } else if(curViewport.bottom > minViewport.bottom && distanceY >= 0) {
            enableY = true;
        }

        if(enableX || enableY)
            chartCompute.containsCurrentViewport(left, top, right, bottom);
    }

    /**
     * 算出我們最小與最大的滑動範圍並且啟動Fling。
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

}
