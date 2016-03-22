package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.kuo.mychart.listener.ChartListener;
import com.kuo.mychart.listener.ColumnChartListener;
import com.kuo.mychart.model.ColumnData;
import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.presenter.ChartCompute;
import com.kuo.mychart.until.ChartRendererUntil;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartRenderer extends AbsChartRenderer {

    private ColumnChartListener columnChartListener;
    private ChartListener chartListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    public ColumnChartRenderer(Context context, ChartListener chartListener, ColumnChartListener columnChartListener) {
        super(context);
        this.chartListener = chartListener;
        this.columnChartListener = columnChartListener;
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void touch(MotionEvent event, int state) {

    }

    private void drawRectRange(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

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
                    maxValue / size * i,
                    minViewport.right,
                    maxValue / size * i, linePaint);
        }

    }

    private void drawAxisY(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int size = columnDatas.size() / 2;

        float maxValue = chartCompute.getMaxValue();

        for(int i = 1 ; i <= size ; i++) {
            canvas.drawText(String.valueOf(maxValue / size * i),
                    0,
                    maxValue / size * i + chartCompute.getMaxTextHeight() / 2, linePaint);
        }

    }


    private void drawAxisX(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();

        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        for(RectF rectF : rectFs) {

            canvas.drawText(columnDatas.get(count).getValueName(),
                    rectF.centerX() - chartCompute.getMaxTextWidth() / 2,
                    rectF.bottom + chartCompute.getMaxTextHeight(), rectPaint);

            count++;
        }
    }

    private void drawRects(Canvas canvas) {

        for(RectF rectF : rectFs) {
            rectPaint.setColor(ChartRendererUntil.CHART_GREEN);
            canvas.drawRect(rectF, rectPaint);
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
            rectPaint.getTextBounds(columnData.getValueName(), 0, columnData.getValueName().length(), rectText);

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
        chartCompute.setMinViewport(new Viewport(maxTextWidth, maxTextHeight, getWidth() - maxTextHeight, getHeight() - maxTextHeight));

    }

    /**
     * Compute New CurrentRect
     *
     * We need to calling prepareRects in evety time.*/
    private void prepareRects() {

        rectFs.clear();

        ChartCompute chartCompute = chartListener.getChartCompute();
        Viewport minViewport = chartCompute.getMinViewport();
        Viewport curViewport = chartCompute.getCurViewport();
        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        int size = columnDatas.size() / 2;

        float columnWidth = curViewport.width() / columnDatas.size() * 0.7f;

        float columnMargin = curViewport.width() / columnDatas.size() * 0.3f;

        for(ColumnData columnData : columnDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin);
            float top = minViewport.bottom - minViewport.height() / chartCompute.getMaxValue() * columnData.getValue();
            float right = left + columnWidth;
            float bottom = minViewport.bottom;

            rectFs.add(new Viewport(left, top, right, bottom));

            Rect rectText = new Rect();
            rectPaint.getTextBounds(columnData.getValueName(), 0, columnData.getValueName().length(), rectText);

            count++;
        }
    }


}
