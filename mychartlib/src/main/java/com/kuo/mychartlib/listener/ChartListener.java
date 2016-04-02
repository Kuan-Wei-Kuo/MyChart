package com.kuo.mychartlib.listener;

import android.view.View;

import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    View getView();

    void setAbsChartRenderer(AbsChartRenderer absChartRenderer);

    void setChartCompute(ChartCompute chartCompute);

    ChartCompute getChartCompute();

    void setOrientation(int orientation);

    int getOrientation();
}
