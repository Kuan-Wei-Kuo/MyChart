package com.kuo.mychart.listener;

import com.kuo.mychart.model.LineData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/23.
 */
public interface LineChartListener {

    public ArrayList<LineData> getLineData();

    public void setLineData(ArrayList<LineData> lineDatas);

}
