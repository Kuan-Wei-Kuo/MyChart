package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.AxisData;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/30.
 */
public abstract class AbsColumnBase extends AbsChartRenderer {

    protected ChartListener chartListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    private ArrayList<AxisData> axisDatas = new ArrayList<>();

    private PointF pointF = new PointF(0, 0);

    private float columnWidth = 0f;

    public AbsColumnBase(Context context, ChartListener chartListener) {
        super(context);

        this.chartListener = chartListener;
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
        drawValues(canvas);
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

    public float getRawX(RectF rectF) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        return rectF.right == minViewport.right ? rectF.left : rectF.right - columnWidth;
    }

    private void drawValues(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<? extends ChartData> chartDatas = chartListener.getChartData();
        Viewport minViewport = chartCompute.getMinViewport();

        int count = 0;

        for(RectF rectF : rectFs) {

            String text = String.valueOf(chartDatas.get(count).getValue());

            float x = getRawX(rectF) + columnWidth / 2;
            float y = rectF.top - chartCompute.getMaxTextHeight();

            if (minViewport.contains(getRawX(rectF) + columnWidth / 2, y) && columnWidth > chartCompute.getMaxTextWidth() / 5) {
                canvas.drawText(text, x, y, textPaint);
            }

            count++;
        }

    }

    private void drawSeparationLines(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport curViewport = chartCompute.getCurViewport();
        Viewport minViewport = chartCompute.getMinViewport();

        float margin = ChartRendererUntil.dp2px(getContext().getResources().getDisplayMetrics().density, 60);

        float size = curViewport.height() / margin;

        float maxValue = chartCompute.getMaxValue();


        for(int i = 1 ; i <= size; i++) {

            float y = curViewport.bottom - curViewport.height() / maxValue * maxValue / size * i;

            if(minViewport.contains((int) minViewport.left, (int) y)) {

                canvas.drawLine(minViewport.left,
                        y,
                        minViewport.right,
                        y, linePaint);
            }

        }

    }

    private void drawAxisY(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport curViewport = chartCompute.getCurViewport();
        Viewport minViewport = chartCompute.getMinViewport();

        float margin = ChartRendererUntil.dp2px(getContext().getResources().getDisplayMetrics().density, 60);

        float size = curViewport.height() / margin;

        float maxValue = chartCompute.getMaxValue();

        String floatFormat = "%.1f";

        for(int i = 1 ; i <= size ; i++) {

            float y = curViewport.bottom - curViewport.height() / maxValue * maxValue / size * i;

            if(minViewport.contains((int) minViewport.left, (int) y)) {
                String text = String.format(floatFormat, maxValue / size * i);
                canvas.drawText(text,
                        ChartRendererUntil.getTextWidth(textPaint, text) / 2,
                        y, textPaint);
            }
        }
    }

    private void drawAxisX(Canvas canvas) {

        for(AxisData axisData : axisDatas) {
            if(axisData.isEnable()) {
                canvas.drawText(axisData.getAxis(), axisData.x, axisData.y, textPaint);
            }
        }

    }

    /**
     * Compute ChartCompute
     *
     * Calling prepareChartCompute on onSizeChanged.*/
    private void prepareChartCompute() {

        rectFs.clear();
        axisDatas.clear();

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<? extends ChartData> mChartData = chartListener.getChartData();

        int maxTextWidth = 0;

        int maxTextHeight = 0;

        float maxValue = 0;

        for(ChartData chartData : mChartData) {

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

            Viewport viewport = new Viewport(0, 0, 0, 0);
            rectFs.add(viewport);

            AxisData axisData = new AxisData(0, 0);
            axisData.setAxis(chartData.getValueName());
            axisDatas.add(axisData);

        }

        chartCompute.setMaxValue(maxValue + 1);
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

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();
        ArrayList<? extends ChartData> mChartData = chartListener.getChartData();

        int count = 0;

        float columnMargin = (curViewport.width() - chartCompute.getPadding()) / mChartData.size() * 0.25f;

        columnWidth = (curViewport.width() - chartCompute.getPadding()) / mChartData.size() * 0.75f;

        SelectData selectData = chartListener.getSelectData();

        for(RectF rectF : rectFs) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = curViewport.bottom - curViewport.height() / (chartCompute.getMaxValue()) * mChartData.get(count).getValue();
            float right = left + columnWidth;
            float bottom = curViewport.bottom;

            if(left < minViewport.left) {
                left = minViewport.left;
            }

            if(right > minViewport.right) {
                right = minViewport.right;
            }

            if(bottom > minViewport.bottom) {
                bottom = minViewport.bottom;
            }

            rectF.set(left, top, right, bottom);

            if(rectF.contains(selectData.getX(), selectData.getY()) && selectData.isSelect()) {
                selectData.setPosition(count);
            }

            count++;
        }

        chartCompute.setCurMargin(columnMargin);

        if(columnWidth + columnMargin * 2 > chartCompute.getMaxTextWidth() || pointF.x == 0) {
            prepareAxisX();
            computeAxisX();
        }
    }

    private void prepareAxisX() {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        int count = 0;

        for(RectF rectF : rectFs) {

            float x = rectF.right == minViewport.right ? rectF.left + columnWidth / 2 : rectF.right - columnWidth + columnWidth / 2;
            float y = rectF.bottom + chartCompute.getMaxTextHeight();

            axisDatas.get(count).setIsEnable(false);
            axisDatas.get(count).set(x, y);

            count++;
        }
    }

    private void computeAxisX() {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        AxisData oldAxisData = null;

        int labelSubWidth = Math.round(chartCompute.getMaxTextWidth() * 1.3f);

        for(AxisData axisData : axisDatas) {

            if(oldAxisData != null) {

                float currSubWidth = Math.abs(oldAxisData.x - axisData.x);

                boolean isEnable = Math.round(currSubWidth) > labelSubWidth;

                if(isEnable) {
                    if(minViewport.contains(axisData.x, minViewport.bottom - 1))
                        axisData.setIsEnable(true);

                    oldAxisData = axisData;
                }

            } else {
                if(minViewport.contains(axisData.x, minViewport.bottom - 1))
                    axisData.setIsEnable(true);

                oldAxisData = axisData;
            }
        }
    }

    protected ArrayList<RectF> getRectFs() {
        return rectFs;
    }

    protected int getValueColor(int index) {
        ArrayList<? extends ChartData> columnDatas = chartListener.getChartData();
        return columnDatas.get(index).getValueColor();
    }

    public float getColumnWidth() {
        return columnWidth;
    }

    public abstract void drawRects(Canvas canvas);
}
