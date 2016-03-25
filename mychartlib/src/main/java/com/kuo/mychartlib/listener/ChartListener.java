package com.kuo.mychartlib.listener;

import com.kuo.mychartlib.renderer.AbsChartRenderer;
import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    public ChartCompute getChartCompute();

    public void setChartCompute(ChartCompute chartCompute);

    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer);
}
