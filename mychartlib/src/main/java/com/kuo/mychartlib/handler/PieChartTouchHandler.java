package com.kuo.mychartlib.handler;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Scroller;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.renderer.PieChartRenderer;

/*
 * Created by Kuo on 2016/4/15.
 */
public class PieChartTouchHandler extends ChartTouchHandler {

    private Scroller mScroller;

    private int lastCurrY = 0;

    public PieChartTouchHandler(Context context, ChartListener chartListener) {
        super(context, chartListener);

        mScroller = new Scroller(context);

        scaleGestureDetector = new ScaleGestureDetector(context, new ChartScaleGestureListener());

        gestureDetector = new GestureDetector(context, new ChartGestureDetector());
    }

    @Override
    public boolean compueScroll() {

        if (mScroller.computeScrollOffset()) {

            ((PieChartRenderer) chartListener.getAbsChartRenderer()).setAngles(mScroller.getCurrY() - lastCurrY);
            lastCurrY = mScroller.getCurrY();

            return true;
        }

        return false;
    }

    public class ChartScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return true;
        }
    }

    public class ChartGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            mScroller.abortAnimation();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Viewport curViewport = chartCompute.getCurViewport();

            //利用數學算式取得位移長度
            float length = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            // e2為手指最後位置減去中間座標，我們可以得知在圓心的左邊或右邊
            // 然而為什麼distanceY與distanceX對調，在於我們需要明白是否垂直與平行移動
            float scrollPos = (e2.getX() - curViewport.centerX()) * distanceY - (e2.getY() - curViewport.centerY()) * distanceX;

            ((PieChartRenderer) chartListener.getAbsChartRenderer()).setAngles(-length * Math.signum(scrollPos) / 3);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            mScroller.abortAnimation();

            lastCurrY = 0;

            Viewport curViewport = chartCompute.getCurViewport();

            float length = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            float scrollPos = (e2.getX() - curViewport.centerX()) * velocityY - (e2.getY() - curViewport.centerY()) * velocityX;

            int curAngle = (int) ((PieChartRenderer) chartListener.getAbsChartRenderer()).getAngles();

            //這邊看你要放在x或y其實都可以，因為角度沒有差別
            mScroller.fling(0, curAngle, 0, (int) (length * Math.signum(scrollPos) / 3),
                    0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

            return true;
        }
    }
}
