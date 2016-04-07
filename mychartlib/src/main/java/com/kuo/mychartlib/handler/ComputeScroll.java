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

    /**
     * 這邊並不打算使用Scroller去做滑動的運算，直接算出當前位置可以避免一些問題。
     * */
    protected void startScroll(float distanceX, float distanceY, ChartCompute chartCompute) {

        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();

        //將我們當前View的高度與顯示的高度做除法運算，在乘上當前的X||Y位移的亮，來避免因為點擊而造成位移量亂改變
        float OffsetX = -distanceX * chartCompute.getCurViewport().width() / chartCompute.getMinViewport().width();
        float OffsetY = -distanceY * chartCompute.getCurViewport().height() / chartCompute.getMinViewport().height();

        float left = chartCompute.getCurViewport().left + OffsetX;
        float top = chartCompute.getCurViewport().top + OffsetY;
        float right  = left + curViewport.width();
        float bottom = top + curViewport.height();

        boolean enableX = curViewport.left > minViewport.left || curViewport.right < minViewport.right;
        boolean enableY = curViewport.top < minViewport.top || curViewport.bottom > minViewport.bottom;

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
