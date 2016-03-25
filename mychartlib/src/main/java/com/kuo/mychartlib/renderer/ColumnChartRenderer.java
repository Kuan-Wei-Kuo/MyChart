package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.listener.ColumnChartListener;
import com.kuo.mychartlib.model.ColumnData;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartRenderer extends AbsChartRenderer {

    private ColumnChartListener columnChartListener;
    private ChartListener chartListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    private float columnWidth = 0f;

    public ColumnChartRenderer(Context context, ChartListener chartListener, ColumnChartListener columnChartListener) {
        super(context);
        this.chartListener = chartListener;
        this.columnChartListener = columnChartListener;
    }

    @Override
    public void prepareCompute() {
        prepareChartCompute();
    }

    @Override
    public void computeGraph() {
        prepareRects();
    }

    @Override
    public void drawGraph(Canvas canvas) {
        drawSeparationLines(canvas);
        drawRects(canvas);
        drawXY(canvas);
        drawAxisX(canvas);
        drawAxisY(canvas);
    }

    private void drawXY(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        /*Draw Y Line*/
        canvas.drawLine(minViewport.left,
                minViewport.top,
                minViewport.left,
                minViewport.bottom, linePaint);

        /*Draw X Line*/
        canvas.drawLine(minViewport.left,
                minViewport.bottom,
                minViewport.right,
                minViewport.bottom, linePaint);
    }

    private void drawSeparationLines(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int size = columnDatas.size() / 2;

        float maxValue = chartCompute.getMaxValue();

        for(int i = 1 ; i <= size ; i++) {
            canvas.drawLine(minViewport.left,
                    minViewport.bottom - minViewport.height() / maxValue * maxValue / size * i,
                    minViewport.right,
                    minViewport.bottom - minViewport.height() / maxValue * maxValue / size * i, linePaint);
        }

    }

    private void drawAxisY(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int size = columnDatas.size() / 2;

        float maxValue = chartCompute.getMaxValue();

        for(int i = 1 ; i <= size ; i++) {
            canvas.drawText(String.valueOf(maxValue / size * i),
                    0,
                    minViewport.bottom - minViewport.height() / maxValue * maxValue / size * i + chartCompute.getMaxTextHeight() / 2, textPaint);
        }

    }


    private void drawAxisX(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();

        Viewport minViewport = chartCompute.getMinViewport();

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        PointF pointF = new PointF(-chartCompute.getMaxTextWidth(), 0);

        for(RectF rectF : rectFs) {

            float x = rectF.centerX() - chartCompute.getMaxTextWidth() / 2;
            float y = rectF.bottom + chartCompute.getMaxTextHeight();

            if(x - pointF.x > chartCompute.getMaxTextWidth() &&
                    x >= minViewport.left - chartCompute.getMaxTextWidth() / 2 &&
                    rectF.width() >= columnWidth / 2) {

                canvas.drawText(columnDatas.get(count).getValueName(),
                        x,
                        y, textPaint);

                pointF.x = x;
            }

            count++;
        }
    }

    private void drawRects(Canvas canvas) {

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        for(RectF rectF : rectFs) {
            rectPaint.setColor(columnDatas.get(count).getValueColor());
            canvas.drawRect(rectF, rectPaint);
            count++;
        }
    }

    /**
     * Compute ChartCompute
     *
     * Calling prepareChartCompute on onSizeChanged.*/
    private void prepareChartCompute() {

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int maxTextWidth = 0;

        int maxTextHeight = 0;

        float maxValue = 0;

        for(ColumnData columnData : columnDatas) {

            Rect rectText = new Rect();
            textPaint.getTextBounds(columnData.getValueName(), 0, columnData.getValueName().length(), rectText);

            if(maxTextWidth < rectText.width()) {
                maxTextWidth = rectText.width();
            }

            if(maxTextHeight < rectText.height()) {
                maxTextHeight = rectText.height();
            }

            if(maxValue < columnData.getValue()) {
                maxValue = columnData.getValue();
            }
        }

        chartCompute.setMaxValue(maxValue);
        chartCompute.setMaxTextWidth(maxTextWidth);
        chartCompute.setMaxTextHeight(maxTextHeight);

        chartCompute.setMinViewport(new Viewport(
                maxTextWidth,
                maxTextHeight,
                chartCompute.getChartWidth() - maxTextHeight,
                chartCompute.getChartHeight() - maxTextHeight));

    }

    /**
     * Compute New CurrentRect
     *
     * We need to calling prepareRect in every time.*/
    private void prepareRects() {

        rectFs.clear();

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();
        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        float columnMargin = curViewport.width() / columnDatas.size() * 0.3f;

        columnWidth = curViewport.width() / columnDatas.size() * 0.7f;


        for(ColumnData columnData : columnDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = minViewport.bottom - minViewport.height() / chartCompute.getMaxValue() * columnData.getValue();
            float right = left + columnWidth - chartCompute.getPadding();
            float bottom = minViewport.bottom;

            if(left < minViewport.left) {
                left = minViewport.left;
            }else if(right > minViewport.right) {
                right = minViewport.right;
            }

            rectFs.add(new Viewport(left, top, right, bottom));

            Rect rectText = new Rect();
            rectPaint.getTextBounds(columnData.getValueName(), 0, columnData.getValueName().length(), rectText);

            count++;
        }

        chartCompute.setCurMargin(columnMargin);
    }


}