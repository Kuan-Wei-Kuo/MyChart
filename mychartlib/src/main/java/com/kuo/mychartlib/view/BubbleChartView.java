package com.kuo.mychartlib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychartlib.model.BubbleData;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.renderer.BubbleChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/4/8.
 */
public class BubbleChartView extends AbsChartView{

    public BubbleChartView(Context context) {
        this(context, null, 0);
    }

    public BubbleChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAbsChartRenderer(new BubbleChartRenderer(context, this));
    }

    public void setBubbleData(ArrayList<BubbleData> bubbleDatas) {
        setChartData(bubbleDatas);
    }

    public ArrayList<? extends ChartData> getBubbleData() {
        return getChartData();
    }

}
