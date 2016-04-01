package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    ChartCompute getChartCompute();

    void setChartCompute(ChartCompute chartCompute);

    void setAbsChartRenderer(AbsChartRenderer absChartRenderer);
}
