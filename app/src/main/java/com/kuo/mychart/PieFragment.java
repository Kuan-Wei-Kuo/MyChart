package com.kuo.mychart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.kuo.mychartlib.model.PieData;
import com.kuo.mychartlib.until.ChartRendererUntil;
import com.kuo.mychartlib.view.PieChartView;

import java.util.ArrayList;
import java.util.Random;

/*
 * Created by Kuo on 2016/3/28.
 */
public class PieFragment extends Fragment {

    private View rootView;
    private ArrayList<PieData> pieDatas = new ArrayList<>();

    private SeekBar size_seek, value_seek;
    private PieChartView pieChartView;

    private int size = 8;
    private int maxValue = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_pie, container, false);

            init();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {

        pieChartView = (PieChartView) rootView.findViewById(R.id.pieChartView);

        size_seek = (SeekBar) rootView.findViewById(R.id.size_seek);
        value_seek = (SeekBar) rootView.findViewById(R.id.value_seek);

        size_seek.setMax(100);
        value_seek.setMax(1000);

        size_seek.setOnSeekBarChangeListener(seekBarChangeListener);
        value_seek.setOnSeekBarChangeListener(seekBarChangeListener);

    }

    private ArrayList<PieData> computePieData(int size, int maxValue) {

        ArrayList<PieData> pieDatas = new ArrayList<>();

        int[] colors = {ChartRendererUntil.CHART_GREEN, ChartRendererUntil.CHART_PINK, ChartRendererUntil.CHART_RED, ChartRendererUntil.CHART_YELLOW, ChartRendererUntil.CHART_BROWN, ChartRendererUntil.CHART_ORANGE, ChartRendererUntil.CHART_GREY, ChartRendererUntil.CHART_PURPLE};

        Random random = new Random();

        for(int i = 0 ; i < size ; i++) {
            PieData pieData = new PieData(random.nextInt(maxValue), colors[random.nextInt(colors.length)], "" + i, 0);
            pieDatas.add(pieData);
        }

        return pieDatas;
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            int id = seekBar.getId();

            if(id == R.id.size_seek) {
                size = progress;
            } else {
                maxValue = progress <= 0 ? 100 : progress;
            }

            pieChartView.setPieData(computePieData(size, maxValue));
            pieChartView.invalidate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
