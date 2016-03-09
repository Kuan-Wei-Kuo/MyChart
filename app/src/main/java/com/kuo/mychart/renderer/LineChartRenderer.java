package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.kuo.mychart.model.LineData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/9.
 */
public class LineChartRenderer extends AbsChartRenderer {

    private ArrayList<LineData> lineDatas;

    private int maxPoint = 0;
    private int padding = 100;

    private float x, y, xRange, yRange;

    private Paint linePaint;
    private Paint textPaint;

    public LineChartRenderer(Context context, int width, int height, ArrayList<LineData> lineDatas) {
        super(context, width, height);

        this.lineDatas = lineDatas;

        initPaint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        new DrawLineChartRenderer(canvas).run();
    }

    private void initPaint() {

        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(getLineColor());

        textPaint = new Paint();
        textPaint.setTextSize(getTextSize());
        textPaint.setColor(getTextColor());
    }

    private void drawXYLines(Canvas canvas) {

        x = padding;
        y = getHeight() - padding;

        xRange = getWidth() - padding - padding;
        yRange = getHeight() - padding - padding;

        //draw y line;
        canvas.drawLine(padding, padding, padding, getHeight() - padding, linePaint);

        //draw x line;
        canvas.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding, linePaint);
    }

    private void drawSeparationLines(Canvas canvas) {

        for(int i = 1 ; i <= 3 ; i++) {
            float line = y - (yRange * 0.25f * i);
            canvas.drawLine(padding, line, getWidth() - padding, line, linePaint);;
        }
    }

    private void drawLabelsText(Canvas canvas) {
        for(int i = 0 ; i < 5 ; i++) {
            float line = y - (yRange * 0.25f * i);
            String point = (int) (maxPoint * 0.25f * i) + "";

            Rect textBounds = new Rect();
            //get text bounds, that can get the text width and height
            textPaint.getTextBounds(point, 0, point.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            //int textWidth = textBounds.left - textBounds.right;

            canvas.drawText(point, 0, line + textHeight / 2, textPaint);
        }
    }

    private void drawAxisX(Canvas canvas) {

        float specText = xRange / lineDatas.size();

        int count = 0;

        for(LineData lineData : lineDatas) {
            count++;

            Rect textBounds = new Rect();
            textPaint.getTextBounds(lineData.getAxisX(), 0, lineData.getAxisX().length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.left - textBounds.right;

            canvas.drawText(lineData.getAxisX(), specText * count + textWidth / 2, y + textHeight * 2, textPaint);
        }
    }

    private void drawGraph(Canvas canvas) {

        float specText = xRange / lineDatas.size();
        int count = 0;

        for(LineData lineData : lineDatas) {
            Path path = new Path();
            path.moveTo(100, 320);//设置Path的起点
            path.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
            canvas.drawPath(path2, p);//画出贝塞尔曲线

            //画点
            canvas.drawPoint(60, 390, linePaint);/
            canvas.drawPoints(new float[]{60,400,65,400,70,400}, p);//画多个点
        }
    }

    public class DrawLineChartRenderer implements Runnable {

        private Canvas canvas;

        public DrawLineChartRenderer(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {
            drawXYLines(canvas);
            drawSeparationLines(canvas);
            drawLabelsText(canvas);
            drawAxisX(canvas);
        }
    }
}
