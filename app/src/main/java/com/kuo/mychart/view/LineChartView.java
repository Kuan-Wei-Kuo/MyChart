package com.kuo.mychart.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychart.listener.LineChartListener;
import com.kuo.mychart.model.LineData;
import com.kuo.mychart.renderer.AbsChartRenderer;
import com.kuo.mychart.renderer.LineChartRenderer;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/9.
 */
public class LineChartView extends AbsChartView implements LineChartListener{

    private ArrayList<LineData> lineData;

    public LineChartView(Context context) {
        this(context, null, 0);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAbsChartRenderer(new LineChartRenderer(getContext(), this, this));
    }

    @Override
    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        super.setAbsChartRenderer(absChartRenderer);
    }

    @Override
    public ArrayList<LineData> getLineData() {
        return lineData;
    }

    @Override
    public void setLineData(ArrayList<LineData> lineData) {
        this.lineData = lineData;
    }
}
