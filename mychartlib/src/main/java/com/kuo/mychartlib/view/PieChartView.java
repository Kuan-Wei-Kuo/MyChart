package com.kuo.mychartlib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.kuo.mychartlib.listener.PieDataListener;
import com.kuo.mychartlib.model.PieData;
import com.kuo.mychartlib.renderer.PieChartRenderer;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/7.
 */
public class PieChartView extends AbsChartView implements PieDataListener {

    private ArrayList<PieData> pieDatas = new ArrayList<>();

    public PieChartView(Context context) {
        this(context, null, 0);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAbsChartRenderer(new PieChartRenderer(getContext(), this, this));
    }

    @Override
    public ArrayList<PieData> getPieData() {
        return pieDatas;
    }

    @Override
    public void setPieData(ArrayList<PieData> pieData) {
        this.pieDatas = pieData;
    }
}
