package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.listener.LineChartListener;
import com.kuo.mychartlib.model.LineData;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.model.Viewport;
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/9.
 */
public class LineChartRenderer extends AbsChartRenderer {

    private LineChartListener lineChartListener;
    private ChartListener chartListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    private float columnWidth = 0f;

    public LineChartRenderer(Context context, ChartListener chartListener, LineChartListener lineChartListener) {
        super(context);
        this.chartListener = chartListener;
        this.lineChartListener = lineChartListener;
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
        drawLines(canvas);
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

        ArrayList<LineData> lineData = lineChartListener.getLineData();

        int size = lineData.size() / 2;

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

        ArrayList<LineData> lineData = lineChartListener.getLineData();

        int size = lineData.size() / 2;

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

        ArrayList<LineData> lineData = lineChartListener.getLineData();

        int count = 0;

        PointF pointF = new PointF(-chartCompute.getMaxTextWidth(), 0);

        for(RectF rectF : rectFs) {

            float x = rectF.centerX() - chartCompute.getMaxTextWidth() / 2;
            float y = rectF.bottom + chartCompute.getMaxTextHeight();

            if(x - pointF.x > chartCompute.getMaxTextWidth() &&
                    x >= minViewport.left - chartCompute.getMaxTextWidth() / 2 &&
                    rectF.width() >= columnWidth / 2) {

                canvas.drawText(lineData.get(count).getValueName(),
                        x,
                        y, textPaint);

                pointF.x = x;
            }

            count++;
        }
    }

    private void drawRects(Canvas canvas) {

        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<LineData> lineData = lineChartListener.getLineData();

        int count = 0;

        rectPaint.setStrokeWidth(30);

        for(RectF rectF : rectFs) {
            if(rectF.centerX() > minViewport.left) {

                ColorDrawable colorDrawable = new ColorDrawable(lineData.get(count).getValueColor());
                colorDrawable.setAlpha(150);

                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(rectF.centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8), rectPaint);

                colorDrawable.setAlpha(255);
                rectPaint.setColor(colorDrawable.getColor());
                canvas.drawCircle(rectF.centerX(),
                        rectF.top,
                        ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 8) * 0.7f, rectPaint);

            }
            count++;
        }
    }

    private void drawLines(Canvas canvas) {
        Viewport minViewport = chartListener.getChartCompute().getMinViewport();

        ArrayList<LineData> lineData = lineChartListener.getLineData();

        int count = 0;

        PointF pointF = new PointF(0, 0);

        rectPaint.setStrokeWidth(30);

        for(RectF rectF : rectFs) {
            if(rectF.centerX() > minViewport.left) {

                if(pointF.x != 0 && pointF.y != 0) {
                    canvas.drawLine(pointF.x, pointF.y, rectF.centerX(), rectF.top, linePaint);
                }

                pointF.x = rectF.centerX();
                pointF.y = rectF.top;
            }
        }
    }

    /**
     * Compute ChartCompute
     *
     * Calling prepareChartCompute on onSizeChanged.*/
    private void prepareChartCompute() {

        ChartCompute chartCompute = chartListener.getChartCompute();
        ArrayList<LineData> lineDatas = lineChartListener.getLineData();

        int maxTextWidth = 0;

        int maxTextHeight = 0;

        float maxValue = 0;

        for(LineData lineData : lineDatas) {

            Rect rectText = new Rect();
            textPaint.getTextBounds(lineData.getValueName(), 0, lineData.getValueName().length(), rectText);

            if(maxTextWidth < rectText.width()) {
                maxTextWidth = rectText.width();
            }

            if(maxTextHeight < rectText.height()) {
                maxTextHeight = rectText.height();
            }

            if(maxValue < lineData.getValue()) {
                maxValue = lineData.getValue();
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
        ArrayList<LineData> lineDatas = lineChartListener.getLineData();

        int count = 0;

        float columnMargin = curViewport.width() / lineDatas.size() * 0.3f;

        columnWidth = curViewport.width() / lineDatas.size() * 0.7f;


        for(LineData lineData : lineDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin) + chartCompute.getPadding();
            float top = minViewport.bottom - minViewport.height() / chartCompute.getMaxValue() * lineData.getValue();
            float right = left + columnWidth - chartCompute.getPadding();
            float bottom = minViewport.bottom;

            if(left < minViewport.left) {
                left = minViewport.left;
            }else if(right > minViewport.right) {
                right = minViewport.right;
            }

            rectFs.add(new Viewport(left, top, right, bottom));

            Rect rectText = new Rect();
            rectPaint.getTextBounds(lineData.getValueName(), 0, lineData.getValueName().length(), rectText);

            count++;
        }

        chartCompute.setCurMargin(columnMargin);
    }

}
