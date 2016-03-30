package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartGenericListener;
import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.AxisData;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/30.
 */
public abstract class AbsColumnBase extends AbsChartRenderer {

    protected ChartListener chartListener;
    protected ChartGenericListener chartGenericListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();
    private ArrayList<AxisData> axisDatas = new ArrayList<>();

    private PointF pointF;

    private float columnWidth = 0f;

    public AbsColumnBase(Context context, ChartListener chartListener, ChartGenericListener chartGenericListener) {
        super(context);

        this.chartListener = chartListener;
        this.chartGenericListener = chartGenericListener;
    }

    @Override
    public void prepareCompute() {
        pointF = new PointF(0, 0);
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

    /**
     * Compute ChartCompute
     *
     * Calling prepareChartCompute on onSizeChanged.*/
    private void prepareChartCompute() {

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<? extends ChartData> chartDatas = chartGenericListener.getChartData();

        int maxTextWidth = 0;

        int maxTextHeight = 0;

        float maxValue = 0;

        for(ChartData chartData : chartDatas) {

            Rect rectText = new Rect();
            textPaint.getTextBounds(chartData.getValueName(), 0, chartData.getValueName().length(), rectText);

            if(maxTextWidth < rectText.width()) {
                maxTextWidth = rectText.width();
            }

            if(maxTextHeight < rectText.height()) {
                maxTextHeight = rectText.height();
            }

            if(maxValue < chartData.getValue()) {
                maxValue = chartData.getValue();
            }
        }

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
        ArrayList<? extends ChartData> chartDatas = chartGenericListener.getChartData();

        int count = 0;

        float columnMargin = (curViewport.width() - chartCompute.getPadding()) / chartDatas.size() * 0.25f;

        columnWidth = (curViewport.width() - chartCompute.getPadding()) / chartDatas.size() * 0.75f;

        for(ChartData chartData : chartDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = minViewport.bottom - minViewport.height() / chartCompute.getMaxValue() * chartData.getValue();
            float right = left + columnWidth;
            float bottom = minViewport.bottom;

            if(left < minViewport.left) {
                left = minViewport.left;
            }else if(right > minViewport.right) {
                right = minViewport.right;
            }

            rectFs.add(new Viewport(left, top, right, bottom));

            Rect rectText = new Rect();
            textPaint.getTextBounds(chartData.getValueName(),
                    0,
                    chartData.getValueName().length(), rectText);

            int textWidth = rectText.width();

            float x = right == minViewport.right ? left + columnWidth / 2 - textWidth / 2 : right - columnWidth + columnWidth / 2 - textWidth / 2;
            float y = bottom + chartCompute.getMaxTextHeight();

            AxisData axisData = new AxisData(x, y);
            axisData.setAxis(chartData.getValueName());

            if(Math.abs(x - pointF.x) > textWidth ||
                    right - left > chartCompute.getMaxTextWidth() || pointF.x == 0) {
                pointF.x = x;
                axisData.setIsEnable(true);
            }

            axisDatas.add(axisData);

            count++;
        }

        chartCompute.setCurMargin(columnMargin);
    }
    protected ArrayList<RectF> getRectFs() {
        return rectFs;
    }

    protected int getValueColor(int index) {
        ArrayList<? extends ChartData> columnDatas = chartGenericListener.getChartData();
        return columnDatas.get(index).getValueColor();
    }

    public abstract void drawRects(Canvas canvas);
}
