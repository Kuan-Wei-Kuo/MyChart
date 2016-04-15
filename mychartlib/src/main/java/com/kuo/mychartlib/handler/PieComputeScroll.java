package com.kuo.mychartlib.handler;

import android.content.Context;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.PieChartRenderer;

/*
 * Created by Kuo on 2016/4/15.
 */
public class PieComputeScroll extends ComputeScroll {

    private ChartListener chartListener;

    public PieComputeScroll(Context context, ChartListener chartListener) {
        super(context);
        this.chartListener = chartListener;
    }

    @Override
    protected void startScroll(float distanceX, float distanceY, ChartCompute chartCompute) {
        super.startScroll(distanceX, distanceY, chartCompute);

    }

    @Override
    protected void startFling(int velocityX, int velocityY, ChartCompute chartCompute) {
        super.startFling(velocityX, velocityY, chartCompute);
    }

    @Override
    protected boolean computeFling(ChartCompute chartCompute) {
        return false;
    }
}
