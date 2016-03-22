package com.kuo.mychart.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychart.listener.ColumnChartListener;
import com.kuo.mychart.model.ColumnData;
import com.kuo.mychart.presenter.ChartCompute;
import com.kuo.mychart.renderer.AbsChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartView extends AbsChartView implements ColumnChartListener{

    protected ArrayList<ColumnData> columnData;
    protected AbsChartRenderer columnChartRenderer;

    public ColumnChartView(Context context) {
        this(context, null, 0);
    }

    public ColumnChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColumnChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ArrayList<ColumnData> getColumnData() {
        return columnData;
    }

    @Override
    public void setColumnData(ArrayList<ColumnData> columnData) {
        this.columnData = columnData;
    }
}
