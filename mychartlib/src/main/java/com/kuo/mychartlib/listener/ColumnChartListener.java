package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.model.ColumnData;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ColumnChartListener {

    public ArrayList<ColumnData> getColumnData();

    public void setColumnData(ArrayList<ColumnData> columnData);
}
