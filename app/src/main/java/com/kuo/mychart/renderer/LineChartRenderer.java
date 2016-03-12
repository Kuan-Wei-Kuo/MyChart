package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

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

    private float rangeX, rangeY;

    private Paint linePaint;
    private Paint textPaint;

    private float horizontalStartX, horizontalStartY, horizontalEndX, horizontalEndY;
    private float verticalStartX, verticalStartY, verticalEndX, verticalEndY;
    private float positionX, positionY;

    public LineChartRenderer(Context context, int width, int height, ArrayList<LineData> lineDatas) {
        super(context, width, height);

        this.lineDatas = lineDatas;

        initPaint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        new DrawLineChartRenderer(canvas).run();
    }

    @Override
    public void touch(MotionEvent event, int state) {

    }

    private void compareData() {

        for(LineData lineData : lineDatas) {
            if(maxPoint < lineData.getPoint()) {
                maxPoint = lineData.getPoint();
            }
        }

        Rect textBounds = new Rect();
        textPaint.getTextBounds(String.valueOf(maxPoint), 0, String.valueOf(maxPoint).length(), textBounds);

        padding = Math.abs(textBounds.right - textBounds.left);

        verticalStartX = padding;
        verticalStartY = padding;
        verticalEndX = padding;
        verticalEndY = getHeight() - padding;

        horizontalStartX = verticalEndX;
        horizontalStartY = verticalEndY;
        horizontalEndX = getWidth() - padding;
        horizontalEndY = verticalEndY;

        rangeX = getWidth() - padding - padding;
        rangeY = getHeight() - padding - padding;

        positionX = verticalStartX;
        positionY = verticalEndY;
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
        //draw vertical line;
        canvas.drawLine(verticalStartX, verticalStartY, verticalEndX, verticalEndY, linePaint);
        //draw horizontal line;
        canvas.drawLine(horizontalStartX, horizontalStartY, horizontalEndX, horizontalEndY, linePaint);
    }

    private void drawSeparationLines(Canvas canvas) {

        for(int i = 1 ; i <= 3 ; i++) {
            float line = horizontalEndY - (rangeY * DEFAULT_SEPARATION * i);
            canvas.drawLine(horizontalStartX, line, horizontalEndX, line, linePaint);
        }
    }

    private void drawAxisY(Canvas canvas) {
        for(int i = 0 ; i < (lineDatas.size() + 1) ; i++) {
            float line = positionY - (rangeY * DEFAULT_SEPARATION * i);
            String point = (int) (maxPoint * DEFAULT_SEPARATION * i) + "";

            Rect textBounds = new Rect();
            //get text bounds, that can get the text width and height
            textPaint.getTextBounds(point, 0, point.length(), textBounds);
            int textHeight = Math.abs(textBounds.bottom - textBounds.top);
            int textWidth = Math.abs(textBounds.right - textBounds.left);

            canvas.drawText(point, 0, line + textHeight / 2, textPaint);
        }
    }

    private void drawAxisX(Canvas canvas) {

        float specText = rangeX / lineDatas.size();

        int count = 0;

        for(LineData lineData : lineDatas) {
            count++;

            Rect textBounds = new Rect();
            textPaint.getTextBounds(lineData.getAxisX(), 0, lineData.getAxisX().length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            canvas.drawText(lineData.getAxisX(), specText * count - textWidth / 2, positionY + textHeight, textPaint);
        }
    }

    private void drawGraph(Canvas canvas) {

        float specText = rangeX / lineDatas.size();
        int count = 0;

        PointF pointF = new PointF(0, 0);

        for(LineData lineData : lineDatas) {
            count++;

            PointF centerPointF = new PointF((specText * count + pointF.x) / 2, ((positionY - rangeY / maxPoint * lineData.getPoint()) + pointF.y) / 2);

            float nowX = specText * count;
            float nowY = positionY - rangeY / maxPoint * lineData.getPoint();

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

            pointF.set(specText * count, positionY - rangeY / maxPoint * lineData.getPoint());
        }

        count = 0;

        for(LineData lineData : lineDatas) {
            count++;
            textPaint.setColor(lineData.getColor());
            textPaint.setStrokeWidth(36);
            canvas.drawPoint(specText * count, positionY - rangeY / maxPoint * lineData.getPoint(), textPaint);
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
            drawAxisY(canvas);
            drawAxisX(canvas);
            drawGraph(canvas);
        }
    }
}
