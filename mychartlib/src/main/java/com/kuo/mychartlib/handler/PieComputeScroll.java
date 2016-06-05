package com.kuo.mychartlib.handler;

import android.content.Context;
import android.view.MotionEvent;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.presenter.ChartCompute;

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
    protected boolean startScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, ChartCompute chartCompute) {
        return super.startScroll(e1, e2, distanceX, distanceY, chartCompute);
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
