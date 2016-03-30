package com.kuo.mychartlib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychartlib.listener.ChartGenericListener;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.ChartGenericArrayList;
import com.kuo.mychartlib.model.LineData;
import com.kuo.mychartlib.renderer.AbsChartRenderer;
import com.kuo.mychartlib.renderer.LineChartRenderer;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/9.
 */
public class LineChartView extends AbsChartView implements ChartGenericListener {

    ChartGenericArrayList<ArrayList<LineData>> lineData = new ChartGenericArrayList<>();

    public LineChartView(Context context) {
        this(context, null, 0);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lineData.setChartData(new ArrayList<LineData>());
        setAbsChartRenderer(new LineChartRenderer(getContext(), this, this));
    }

    @Override
    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        super.setAbsChartRenderer(absChartRenderer);
    }

    @Override
    public ArrayList<? extends ChartData> getChartData() {
        return lineData.getChartData();
    }

    @Override
    public void setChartData(ArrayList<? extends ChartData> chartData) {
        lineData.setChartData((ArrayList<LineData>) chartData);
    }
}
