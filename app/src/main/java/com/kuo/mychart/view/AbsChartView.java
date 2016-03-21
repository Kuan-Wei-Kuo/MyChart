package com.kuo.mychart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kuo.mychart.handler.ChartTouchHandler;
import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartView extends View {

    protected Viewport chartViewport;
    protected ChartTouchHandler chartTouchHandler;
    protected ChartCompute chartCompute;

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
    public boolean onTouchEvent(MotionEvent event) {
        return chartTouchHandler.onTouchEvent(event, chartCompute);
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
}
