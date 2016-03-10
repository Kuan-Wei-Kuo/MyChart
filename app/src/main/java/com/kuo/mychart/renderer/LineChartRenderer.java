package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

import com.kuo.mychart.model.LineData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/9.
 */
public class LineChartRenderer extends AbsChartRenderer {

    private static final float DEFAULT_SEPARATION = 0.25f;

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

    private void compareData() {

        for(LineData lineData : lineDatas) {
            if(maxPoint < lineData.getPoint()) {
                maxPoint = lineData.getPoint();
            }
        }
    }

    private void init() {

        Rect textBounds = new Rect();
        textPaint.getTextBounds(String.valueOf(maxPoint), 0, String.valueOf(maxPoint).length(), textBounds);

        padding = textBounds.left - textBounds.right;

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
            float line = y - (yRange * DEFAULT_SEPARATION * i);
            canvas.drawLine(padding, line, getWidth() - padding, line, linePaint);;
        }
    }

    private void drawLabelsText(Canvas canvas) {
        for(int i = 0 ; i < (lineDatas.size() + 1) ; i++) {
            float line = y - (yRange * DEFAULT_SEPARATION * i);
            String point = (int) (maxPoint * DEFAULT_SEPARATION * i) + "";

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

        PointF pointF = new PointF(0, 0);

        for(LineData lineData : lineDatas) {
            count++;

            PointF centerPointF = new PointF((specText * count + pointF.x) / 2, ((y - yRange / maxPoint * lineData.getPoint()) + pointF.y) / 2);

            float nowX = specText * count;
            float nowY = y - yRange / maxPoint * lineData.getPoint();

            Path path = new Path();
            path.moveTo(pointF.x, pointF.y);

            if(pointF.y != 0 && pointF.x != 0) {
                if(nowY < pointF.y)
                    path.cubicTo(centerPointF.x, pointF.y, nowX - nowX / 4, nowY, nowX, nowY);
                else
                    path.cubicTo(centerPointF.x, pointF.y, centerPointF.x, centerPointF.y, nowX, nowY);

                linePaint.setStyle(Paint.Style.STROKE);
                linePaint.setColor(lineData.getColor());
                canvas.drawPath(path, linePaint);
            }

            pointF.set(specText * count, y - yRange / maxPoint * lineData.getPoint());
        }

        count = 0;

        for(LineData lineData : lineDatas) {
            count++;
            textPaint.setColor(lineData.getColor());
            textPaint.setStrokeWidth(36);
            canvas.drawPoint(specText * count, y - yRange / maxPoint * lineData.getPoint(), textPaint);
        }
    }

    public class DrawLineChartRenderer implements Runnable {

        private Canvas canvas;

        public DrawLineChartRenderer(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {

            compareData();

            drawXYLines(canvas);
            drawSeparationLines(canvas);
            drawLabelsText(canvas);
            drawAxisX(canvas);
            drawGraph(canvas);
        }
    }
}
