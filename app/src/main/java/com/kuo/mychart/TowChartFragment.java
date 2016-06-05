package com.kuo.mychart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuo.mychart.common.SlidingTabLayout;
import com.kuo.mychartlib.model.ColumnData;
import com.kuo.mychartlib.model.LineData;
import com.kuo.mychartlib.until.ChartRendererUntil;
import com.kuo.mychartlib.view.ColumnChartView;
import com.kuo.mychartlib.view.LineChartView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by User on 2016/6/4.
 */
public class TowChartFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_towchart, container, false);
            init();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {

        LineChartView lineChartView = (LineChartView) rootView.findViewById(R.id.lineChartView);
        lineChartView.setLineData(computeLineData(5, 100));

        ColumnChartView columnChartView = (ColumnChartView) rootView.findViewById(R.id.columnChartView);
        columnChartView.setColumnData(computeColumnData(5, 100));

    }

    private ArrayList<LineData> computeLineData(int size, int maxValue) {

        ArrayList<LineData> lineDatas = new ArrayList<>();

        int[] colors = {ChartRendererUntil.CHART_GREEN, ChartRendererUntil.CHART_PINK, ChartRendererUntil.CHART_RED, ChartRendererUntil.CHART_YELLOW, ChartRendererUntil.CHART_BROWN, ChartRendererUntil.CHART_ORANGE, ChartRendererUntil.CHART_GREY, ChartRendererUntil.CHART_PURPLE};

        Random random = new Random();

        for(int i = 0 ; i < size ; i++) {
            LineData lineData = new LineData("Axis-" + i, random.nextInt(maxValue), colors[random.nextInt(colors.length)]);
            lineDatas.add(lineData);
        }

        return lineDatas;
    }

    private ArrayList<ColumnData> computeColumnData(int size, int maxValue) {

        int[] colors = {ChartRendererUntil.CHART_GREEN, ChartRendererUntil.CHART_PINK, ChartRendererUntil.CHART_RED, ChartRendererUntil.CHART_YELLOW, ChartRendererUntil.CHART_BROWN, ChartRendererUntil.CHART_ORANGE, ChartRendererUntil.CHART_GREY, ChartRendererUntil.CHART_PURPLE};

        ArrayList<ColumnData> test = new ArrayList<>();

        Random random = new Random();

        for(int i = 0 ; i < size ; i++) {
            test.add(new ColumnData("Axis " + i, random.nextInt(maxValue), colors[random.nextInt(colors.length)]));
        }

        return test;
    }
}
