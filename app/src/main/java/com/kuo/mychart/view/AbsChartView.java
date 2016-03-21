package com.kuo.mychart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kuo.mychart.handler.ChartTouchHandler;
import com.kuo.mychart.model.Viewport;

/*
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartView extends View {

    private Viewport chartViewport;

    private ChartTouchHandler chartTouchHandler;

    public AbsChartView(Context context) {
        super(context);

    }

    public AbsChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public AbsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        chartTouchHandler = new ChartTouchHandler(context, this);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chartTouchHandler.onTouchEvent(event);
    }

    public void setViewport(Viewport viewport) {
        this.chartViewport = viewport;
    }

    public Viewport getViewport() {
        return chartViewport;
    }


}
