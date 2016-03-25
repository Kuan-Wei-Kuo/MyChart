package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.model.LineData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/23.
 */
public interface LineChartListener {

    public ArrayList<LineData> getLineData();

    public void setLineData(ArrayList<LineData> lineDatas);

}
