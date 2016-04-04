package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.model.ChartData;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/30.
 */
public interface ChartGenericListener {

    ArrayList<? extends ChartData> getChartData();

}
