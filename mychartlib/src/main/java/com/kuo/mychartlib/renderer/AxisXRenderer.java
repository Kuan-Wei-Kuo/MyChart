package com.kuo.mychartlib.renderer;

import com.kuo.mychartlib.listener.ChartListener;

/**
 * Created by Kuo on 2016/4/14.
 */
public class AxisXRenderer {

    protected int labelWidth;

    private ChartListener chartListener;

    public AxisXRenderer(ChartListener chartListener) {
        this.chartListener = chartListener;
    }

    private void computeAxisX() {

        labelWidth = chartListener.getChartCompute().getChartWidth() + 4;



    }
}
