package com.kuo.mychartlib.listener;

import android.view.View;

import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

/*
 * Created by Kuo on 2016/3/22.
 */
public interface ChartListener {

    View getView();

    ChartCompute getChartCompute();

    int getOrientation();

    boolean isTouchEnable();

    void setAbsChartRenderer(AbsChartRenderer absChartRenderer);

    void setOrientation(int orientation);

    void setTouchEnable(boolean enable);
}
