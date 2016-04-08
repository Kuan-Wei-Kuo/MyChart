package com.kuo.mychartlib.listener;

import android.view.View;

import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    View getView();

    ChartCompute getChartCompute();

    SelectData getSelectData();

    ArrayList<? extends ChartData> getChartData();

    int getOrientation();

    boolean isTouchEnable();

    void setAbsChartRenderer(AbsChartRenderer absChartRenderer);

    void setOrientation(int orientation);

    void setTouchEnable(boolean enable);

    void setChartData(ArrayList<? extends ChartData> chartData);

}
