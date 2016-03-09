package com.kuo.mychart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kuo.mychart.model.BarData;
import com.kuo.mychart.model.LineData;
import com.kuo.mychart.model.PieData;
import com.kuo.mychart.view.BarChartView;
import com.kuo.mychart.view.LineChartView;
import com.kuo.mychart.view.PieChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PieData> pieDatas = new ArrayList<>();

        String[] axisXs = {"1", "2", "3", "4"};
        int[] points = {80, 40, 50, 20};
        int[] colors = {Color.BLUE, Color.RED, Color.CYAN, Color.GREEN};

        for(int i = 0 ; i < points.length ; i++) {
            PieData pieData = new PieData(points[i], colors[i], "", -1);
            pieDatas.add(pieData);
        }

        PieChartView pieChart = (PieChartView) findViewById(R.id.pieChart);
        pieChart.setPieData(pieDatas);

        ArrayList<BarData> barDatas = new ArrayList<>();
        ArrayList<LineData> lineDatas = new ArrayList<>();

        for(int i = 0 ; i < points.length ; i++) {
            BarData barData = new BarData(points[i], "", colors[i]);
            barData.setAxisX(axisXs[i]);
            barDatas.add(barData);

            LineData lineData = new LineData(points[i], "", colors[i]);
            lineData.setAxisX(axisXs[i]);
            lineDatas.add(lineData);
        }

        BarChartView barChart = (BarChartView) findViewById(R.id.barChart);
        barChart.setBarDatas(barDatas);

        LineChartView lineChart = (LineChartView) findViewById(R.id.lineChart);
        lineChart.setLineDatas(lineDatas);
    }
}
