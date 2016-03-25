package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.model.PieData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/24.
 */
public interface PieDataListener {

    public ArrayList<PieData> getPieData();

    public void setPieData(ArrayList<PieData> pieData);

}
