package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.listener.ColumnChartListener;
import com.kuo.mychartlib.model.AxisData;
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
    private ArrayList<PointF> textPoints = new ArrayList<>();
    private ArrayList<Integer> textCount = new ArrayList<>();

    private ArrayList<AxisData> axisDatas = new ArrayList<>();

    private float columnWidth = 0f;

    private PointF pointF;

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

        //int size = columnDatas.size() / 2;

        int size = 5;

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

        //int size = columnDatas.size() / 2;
        int size = 5;

        float maxValue = chartCompute.getMaxValue();

        String floatFormat = "%.1f";

        for(int i = 1 ; i <= size ; i++) {
            canvas.drawText(String.format(floatFormat, maxValue / size * i),
                    0,
                    minViewport.bottom - minViewport.height() / maxValue * maxValue / size * i + chartCompute.getMaxTextHeight() / 2, textPaint);
        }

    }


    private void drawAxisX(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();

        Viewport minViewport = chartCompute.getMinViewport();

        for(AxisData axisData : axisDatas) {

            Rect rectText = new Rect();
            textPaint.getTextBounds(axisData.getAxis(), 0, axisData.getAxis().length(), rectText);

            int textWidth = rectText.width();

            if(axisData.x + textWidth / 2 > minViewport.left &&
                    axisData.x + textWidth / 2 < minViewport.right && axisData.isEnable())  {

                canvas.drawText(axisData.getAxis(),
                        axisData.x,
                        axisData.y, textPaint);
            }
            
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

        pointF = new PointF(chartCompute.getPadding() - chartCompute.getMaxTextWidth(), 0);

        chartCompute.setMaxValue(maxValue);
        chartCompute.setMaxTextWidth(maxTextWidth);
        chartCompute.setMaxTextHeight(maxTextHeight);

        String floatFormat = "%.1f";

        Rect rectText = new Rect();
        textPaint.getTextBounds(String.format(floatFormat, maxValue), 0, String.format(floatFormat, maxValue).length(), rectText);

        chartCompute.setMinViewport(new Viewport(
                rectText.width() + chartCompute.getPadding(),
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
        axisDatas.clear();

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();
        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        float columnMargin = curViewport.width() / columnDatas.size() * 0.25f;

        columnWidth = curViewport.width() / columnDatas.size() * 0.75f;

        for(ColumnData columnData : columnDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = minViewport.bottom - minViewport.height() / chartCompute.getMaxValue() * columnData.getValue();
            float right = left + columnWidth;
            float bottom = minViewport.bottom;

            if(left < minViewport.left) {
                left = minViewport.left;
            }else if(right > minViewport.right) {
                right = minViewport.right;
            }

            rectFs.add(new Viewport(left, top, right, bottom));

            Rect rectText = new Rect();
            textPaint.getTextBounds(columnDatas.get(count).getValueName(), 0, columnDatas.get(count).getValueName().length(), rectText);

            int textWidth = rectText.width();

            float x = right == minViewport.right ? left + columnWidth / 2 - textWidth / 2 : right - columnWidth + columnWidth / 2 - textWidth / 2;
            float y = bottom + chartCompute.getMaxTextHeight();

            AxisData axisData = new AxisData(x, y);
            axisData.setAxis(columnData.getValueName());

            if(Math.abs(x - pointF.x) > chartCompute.getMaxTextWidth() ||
                    right - left > chartCompute.getMaxTextWidth()) {
                pointF.x = x;
                axisData.setIsEnable(true);
            }

            axisDatas.add(axisData);

            count++;
        }

        chartCompute.setCurMargin(columnMargin);
    }


}
