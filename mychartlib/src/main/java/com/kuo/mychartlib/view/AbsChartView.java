package com.kuo.mychartlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.kuo.mychartlib.handler.ChartTouchHandler;
import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

/*
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartView extends View implements ChartListener {

    protected Viewport chartViewport;
    protected ChartTouchHandler chartTouchHandler;
    protected ChartCompute chartCompute;

    protected AbsChartRenderer absChartRenderer;

    public AbsChartView(Context context) {
        this(context, null, 0);

    }

    public AbsChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AbsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        chartCompute = new ChartCompute();
    }

    public void setTouchEnable(boolean enable) {
        chartTouchHandler.setEnable(enable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        absChartRenderer.computeGraph();
        absChartRenderer.drawGraph(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chartTouchHandler.onTouchEvent(event, chartCompute);
    }

    @Override
    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        this.absChartRenderer = absChartRenderer;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartTouchHandler = new ChartTouchHandler(getContext(), this);

        chartCompute.setChartWidth(w);
        chartCompute.setChartHeight(h);

        absChartRenderer.prepareCompute();
    }

    @Override
    public ChartCompute getChartCompute() {
        return chartCompute;
    }

    @Override
    public void setChartCompute(ChartCompute chartCompute) {
        this.chartCompute = chartCompute;
    }

    public void upadateChart() {
        absChartRenderer.prepareCompute();
        invalidate();
    }

    @Override
    public void setOrientation(int orientation) {
        chartCompute.setOrientation(orientation);
    }

    @Override
    public int getOrientation() {
        return chartCompute.getOrientation();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void computeScroll() {

        Scroller mScroller = chartTouchHandler.getScroller();

        if (mScroller.computeScrollOffset()) {

            float left, right, top, bottom;

            if(chartTouchHandler.getTouchState() == ChartTouchHandler.TOUCH_SCROLL) {
                left = mScroller.getFinalX();
                top = mScroller.getFinalY();
            } else {
                left = mScroller.getCurrX();
                top = mScroller.getCurrY();
            }

            right = left + chartCompute.getCurViewport().width();
            bottom = top + chartCompute.getCurViewport().height();

            chartCompute.setCurViewport(left, top, right, bottom);

            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }
}
