package com.kuo.mychartlib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychartlib.listener.ChartGenericListener;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.ChartGenericArrayList;
import com.kuo.mychartlib.model.ColumnData;
import com.kuo.mychartlib.renderer.AbsChartRenderer;
import com.kuo.mychartlib.renderer.ColumnChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartView extends AbsChartView implements ChartGenericListener{

    protected ArrayList<ColumnData> columnData = new ArrayList<>();
    protected ChartGenericArrayList<ArrayList<ColumnData>> chartData = new ChartGenericArrayList<>();

    public ColumnChartView(Context context) {
        this(context, null, 0);
    }

    public ColumnChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColumnChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartData.setChartData(new ArrayList<ColumnData>());
        setAbsChartRenderer(new ColumnChartRenderer(getContext(), this, this));
    }

    @Override
    public ArrayList<ColumnData> getChartData() {
        return chartData.getChartData();
    }

    @Override
    public void setChartData(ArrayList<? extends ChartData> chartData) {
        this.chartData.setChartData((ArrayList<ColumnData>) chartData);
    }

    @Override
    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        super.setAbsChartRenderer(absChartRenderer);
    }

}
