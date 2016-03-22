package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kuo.mychart.handler.ChartTouchHandler;
import com.kuo.mychart.listener.ChartListener;
import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.presenter.ChartCompute;
import com.kuo.mychart.renderer.AbsChartRenderer;

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

        //setAbsChartRenderer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        absChartRenderer.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chartTouchHandler.onTouchEvent(event, chartCompute);
    }

    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        this.absChartRenderer = absChartRenderer;
    }

    public Viewport getViewport() {
        return chartCompute.getCurViewport();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        chartTouchHandler = new ChartTouchHandler(getContext(), this);
        chartCompute = new ChartCompute(new Viewport(0, 0, MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)));
    }

    @Override
    public ChartCompute getChartCompute() {
        return chartCompute;
    }

    @Override
    public void setChartCompute(ChartCompute chartCompute) {
        this.chartCompute = chartCompute;
    }
}
