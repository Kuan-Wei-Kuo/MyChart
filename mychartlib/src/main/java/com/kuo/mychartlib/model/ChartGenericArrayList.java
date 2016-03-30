package com.kuo.mychartlib.model;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/30.
 */
public class ChartGenericArrayList<T extends ArrayList<? extends ChartData>> {

    private T chartData;

    public void setChartData(T chartData) {
        this.chartData = chartData;
    }

    public T getChartData() {
        return chartData;
    }
}
