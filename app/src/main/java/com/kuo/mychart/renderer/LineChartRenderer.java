package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychart.model.LineData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/7.
 */
public class LineChartRenderer extends AbsChartRenderer {

    private RectF rectF;

    private ArrayList<LineData> lineDatas = new ArrayList<>();

    private int totalPoint;
    private int maxPoint = 100;
    private int padding = 100;

    private float x, y, xRange, yRange;

    private Paint linePaint;
    private Paint textPaint;

    public LineChartRenderer(Context context, int width, int height) {
        super(context, width, height);

        initPaint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        new DrawLineChartRunnable(canvas).run();
    }

    private void initPaint() {

        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(getLineColor());

        textPaint = new Paint();
        textPaint.setTextSize(getTextSize());
        textPaint.setColor(getTextColor());
    }

    private void compareData() {

        totalPoint = 0;

        for(LineData lineData : lineDatas) {
            totalPoint += lineData.getPoint();
        }

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

        String[] axisX = {"1", "2", "3", "4", "5"};

        float specText = xRange / axisX.length;

        for(int i = 1 ; i <= axisX.length ; i++) {

            Rect textBounds = new Rect();
            //get text bounds, that can get the text width and height
            textPaint.getTextBounds(axisX[i-1], 0, axisX[i-1].length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.left - textBounds.right;

            canvas.drawText(axisX[i-1], specText * i + textWidth / 2, y + textHeight * 2, textPaint);
        }
    }

    private void drawGraph(Canvas canvas) {

        int[] points = {50, 100, 30, 80, 70};

        float specText = xRange / points.length;

        for(int i = 1 ; i <= points.length ; i++) {

            RectF rectF = new RectF(specText * i - (specText / 4), y - yRange / maxPoint * points[i-1],
                    specText * i + (specText / 4), y);

            canvas.drawRect(rectF, textPaint);
        }

    }

    public class DrawLineChartRunnable implements Runnable {

        private Canvas canvas;

        private DrawLineChartRunnable(Canvas canvas) {
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
