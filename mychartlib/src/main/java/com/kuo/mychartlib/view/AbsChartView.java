package com.kuo.mychartlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    public Viewport getViewport() {
        return chartCompute.getCurViewport();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartTouchHandler = new ChartTouchHandler(getContext(), this);

        chartCompute = new ChartCompute(new Viewport(0, 0, w, h));
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

    public void upadteChart() {
        absChartRenderer.prepareCompute();
        invalidate();
    }
}
