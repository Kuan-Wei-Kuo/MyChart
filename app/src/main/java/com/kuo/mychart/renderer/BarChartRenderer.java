package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychart.model.BarData;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/7.
 */
public class BarChartRenderer extends AbsChartRenderer {

    private RectF rectF;

    private ArrayList<BarData> barDatas;

    private int maxPoint = 0;
    private int padding = 100;

    private float x, y, xRange, yRange;

    private Paint linePaint;
    private Paint textPaint;

    public BarChartRenderer(Context context, int width, int height, ArrayList<BarData> barDatas) {
        super(context, width, height);

        this.barDatas = barDatas;

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

        for(BarData barData : barDatas) {
            if(maxPoint < barData.getPoint()) {
                maxPoint = barData.getPoint();
            }
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
        for(int i = 0 ; i < (barDatas.size() + 1) ; i++) {
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

        int count = 0;
        int padding = 30;

        float width = (xRange - padding) / barDatas.size() / 2;
        float left, right;

        for(BarData barData : barDatas) {

            Rect textBounds = new Rect();
            textPaint.getTextBounds(barData.getAxisX(), 0, barData.getAxisX().length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            left = x + padding + ((width * 2) * count);

            canvas.drawText(barData.getAxisX(), left + (width / 2) - textWidth / 2, y + textHeight, textPaint);

            count++;
        }

    }

    private void drawGraph(Canvas canvas) {

        int count = 0;
        int padding = 30;

        float width = (xRange - padding) / barDatas.size() / 2;

        float left, top, right, bottom;

        for(BarData barData : barDatas) {

            left = x + padding + ((width * 2) * count);
            top = y - yRange / maxPoint * barData.getPoint();
            right = left + width;
            bottom = y;

            RectF rectF = new RectF(left, top, right, bottom);

            textPaint.setColor(barData.getColor());
            canvas.drawRect(rectF, textPaint);

            count++;
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
