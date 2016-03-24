package com.kuo.mychart.listener;

import com.kuo.mychart.model.PieData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/24.
 */
public interface PieDataListener {

    public ArrayList<PieData> getPieData();

    public void setPieData(ArrayList<PieData> pieData);

}
