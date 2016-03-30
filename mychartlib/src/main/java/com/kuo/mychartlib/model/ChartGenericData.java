package com.kuo.mychartlib.model;

/*
 * Created by Kuo on 2016/3/30.
 */
public class ChartGenericData<T>  {

    private T chartData;

    public void setChartData(T chartData) {
        this.chartData = chartData;
    }

    public T getChartData() {
        return chartData;
    }
}

