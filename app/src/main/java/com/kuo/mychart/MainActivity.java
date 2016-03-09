package com.kuo.mychart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kuo.mychart.model.PieData;
import com.kuo.mychart.view.PieChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PieData> pieDatas = new ArrayList<>();

        int[] points = {80, 40, 50, 20};
        int[] colors = {Color.BLUE, Color.RED, Color.CYAN, Color.GREEN};

        for(int i = 0 ; i < points.length ; i++) {
            PieData pieData = new PieData(points[i], colors[i], "", -1);
            pieDatas.add(pieData);
        }

        PieChartView pieChart = (PieChartView) findViewById(R.id.pieChart);
        pieChart.setPieData(pieDatas);
    }
}
