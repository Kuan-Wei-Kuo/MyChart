package com.kuo.mychart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kuo.mychartlib.model.ColumnData;
import com.kuo.mychartlib.model.LineData;
import com.kuo.mychartlib.model.PieData;
import com.kuo.mychartlib.until.ChartRendererUntil;
import com.kuo.mychartlib.view.ColumnChartView;
import com.kuo.mychartlib.view.LineChartView;
import com.kuo.mychartlib.view.PieChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PieData> pieDatas = new ArrayList<>();
        ArrayList<LineData> lineDatas = new ArrayList<>();
        ArrayList<ColumnData> columnDatas = new ArrayList<>();

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int[] values = {80, 40, 50, 20, 150, 200, 100, 25};
        int[] colors = {ChartRendererUntil.CHART_GREEN, ChartRendererUntil.CHART_PINK, ChartRendererUntil.CHART_RED, ChartRendererUntil.CHART_YELLOW, ChartRendererUntil.CHART_BROWN, ChartRendererUntil.CHART_ORANGE, ChartRendererUntil.CHART_GREY, ChartRendererUntil.CHART_PURPLE};

        for(int i = 0 ; i < values.length ; i++) {
            PieData pieData = new PieData(values[i], colors[i], axisXs[i], 0);
            pieDatas.add(pieData);

            LineData lineData = new LineData(axisXs[i], values[i], colors[i]);
            lineDatas.add(lineData);

            ColumnData columnData = new ColumnData(axisXs[i], values[i], colors[i]);
            columnDatas.add(columnData);
        }

        PieChartView pieChartView = (PieChartView) findViewById(R.id.pieChartView);
        pieChartView.setPieData(pieDatas);

        LineChartView lineChartView = (LineChartView) findViewById(R.id.lineChartView);
        lineChartView.setLineData(lineDatas);

        ColumnChartView columnChartView = (ColumnChartView) findViewById(R.id.columnChartView);
        columnChartView.setColumnData(columnDatas);

    }
}
