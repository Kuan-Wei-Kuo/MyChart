package com.kuo.mychart.listener;

import com.kuo.mychart.presenter.ChartCompute;
import com.kuo.mychart.renderer.AbsChartRenderer;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    public ChartCompute getChartCompute();

    public void setChartCompute(ChartCompute chartCompute);

    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer);
}
