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
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/30.
 */
public abstract class AbsColumnBase extends AbsChartRenderer {

    protected ChartListener chartListener;
    protected ChartGenericListener chartGenericListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    private PointF pointF = new PointF(0, 0);

    private float columnWidth = 0f;

    public AbsColumnBase(Context context, ChartListener chartListener, ChartGenericListener chartGenericListener) {
        super(context);

        this.chartListener = chartListener;
        this.chartGenericListener = chartGenericListener;
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

        float x = rectF.right == minViewport.right ? rectF.left : rectF.right - columnWidth;

        return x;
    }

    public float getRawY(RectF rectF, float value) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport curViewport = chartCompute.getCurViewport();
        Viewport minViewport = chartCompute.getMinViewport();

        float y = rectF.bottom == minViewport.bottom ? rectF.top : curViewport.bottom - curViewport.height() / (chartCompute.getMaxValue() + 20) * value;

        return y;
    }

    private void drawValues(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<? extends ChartData> chartDatas = chartGenericListener.getChartData();
        Viewport minViewport = chartCompute.getMinViewport();

        int count = 0;

        for(RectF rectF : rectFs) {

            String text = String.valueOf(chartDatas.get(count).getValue());

            Rect rect = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), rect);

            float x = getRawX(rectF) + columnWidth / 2 - rect.width() / 2;
            float y = rectF.top - chartCompute.getMaxTextHeight();

            if (minViewport.contains(getRawX(rectF) + columnWidth / 2, y)) {
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

        float maxValue = chartCompute.getMaxValue() + 20;


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

        float maxValue = chartCompute.getMaxValue() + 20;

        String floatFormat = "%.1f";

        for(int i = 1 ; i <= size ; i++) {

            float y = curViewport.bottom - curViewport.height() / maxValue * maxValue / size * i +
                    chartCompute.getMaxTextHeight() / 2;

            if(minViewport.contains((int) minViewport.left, (int) y)) {
                canvas.drawText(String.format(floatFormat, maxValue / size * i),
                        0,
                        y, textPaint);
            }
        }

    }

    private void drawAxisX(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ChartCompute chartCompute = chartListener.getChartCompute();

        AxisData oldAxisData = null;

        for(AxisData axisData : axisDatas) {

            Rect rect = new Rect();
            textPaint.getTextBounds(axisData.getAxis(), 0, axisData.getAxis().length(), rect);

            if(oldAxisData != null) {

                Rect rectT = new Rect();
                textPaint.getTextBounds(oldAxisData.getAxis(), 0, oldAxisData.getAxis().length(), rectT);

                float oldX = oldAxisData.x + rectT.width() / 2 - chartCompute.getMaxTextWidth() / 2;
                float nowX = axisData.x + rect.width() / 2 - chartCompute.getMaxTextWidth() / 2;

                if(Math.abs((int) oldX - (int) nowX) > chartCompute.getMaxTextWidth()
                        || columnWidth > chartCompute.getMaxTextWidth()) {

                    if(minViewport.contains(axisData.x + rect.width() / 2, minViewport.bottom - 1)) {
                        canvas.drawText(axisData.getAxis(), axisData.x, axisData.y, textPaint);
                    }

                    oldAxisData = axisData;
                }
            } else {
                if(minViewport.contains(axisData.x + rect.width() / 2, minViewport.bottom - 1)) {
                    canvas.drawText(axisData.getAxis(), axisData.x, axisData.y, textPaint);
                }
                oldAxisData = axisData;
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

    private float columnMargin;
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

        columnMargin = (curViewport.width() - chartCompute.getPadding()) / chartDatas.size() * 0.25f;

        columnWidth = (curViewport.width() - chartCompute.getPadding()) / chartDatas.size() * 0.75f;

        for(ChartData chartData : chartDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = curViewport.bottom - curViewport.height() / (chartCompute.getMaxValue() + 20) * chartData.getValue();
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

            rectFs.add(new Viewport(left, top, right, bottom));

            count++;
        }

        chartCompute.setCurMargin(columnMargin);

        if(columnWidth + columnMargin * 2 > chartCompute.getMaxTextWidth() || pointF.x == 0) {
            prepareAxisX();
        }
    }

    private ArrayList<AxisData> axisDatas = new ArrayList<>();

    private void prepareAxisX() {

        axisDatas.clear();

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();

        ArrayList<? extends ChartData> chartDatas = chartGenericListener.getChartData();

        int count = 0;

        for(RectF rectF : rectFs) {

            Rect rect = new Rect();
            textPaint.getTextBounds(chartDatas.get(count).getValueName(), 0, chartDatas.get(count).getValueName().length(), rect);
            float textWidth = rect.width();

            float x = rectF.right == minViewport.right ? rectF.left + columnWidth / 2 - textWidth / 2 : rectF.right - columnWidth + columnWidth / 2 - textWidth / 2;
            float y = rectF.bottom + chartCompute.getMaxTextHeight();

            AxisData axisData = new AxisData(x, y);
            axisData.setAxis(chartDatas.get(count).getValueName());

            axisData.setIsEnable(true);
            axisDatas.add(axisData);

            count++;
        }



    }

    protected ArrayList<RectF> getRectFs() {
        return rectFs;
    }

    protected int getValueColor(int index) {
        ArrayList<? extends ChartData> columnDatas = chartGenericListener.getChartData();
        return columnDatas.get(index).getValueColor();
    }

    public float getColumnWidth() {
        return columnWidth;
    }

    public abstract void drawRects(Canvas canvas);
}
