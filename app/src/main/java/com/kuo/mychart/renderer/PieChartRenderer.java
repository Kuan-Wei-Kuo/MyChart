package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;

import com.kuo.mychart.model.PieData;
import com.kuo.mychart.R;
import com.kuo.mychart.until.ChartRendererUntil;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/7.
 */
public class PieChartRenderer extends AbsChartRenderer {

    private Paint bgPaint;
    private Paint linePaint;
    private RectF rectF;
    private PointF pointF = new PointF();
    private ArrayList<PieData> pieDatas;

    private float radius;

    public PieChartRenderer(Context context, int width, int height, ArrayList<PieData> pieDatas) {
        super(context, width, height);

        this.pieDatas = pieDatas;

        initPaint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        new DrawPieRendererRunnable(canvas).run();
    }

    @Override
    public void touch(MotionEvent event, int state) {

    }

    private void comparePieData() {

        int totalPoint = 0;

        for(PieData pieData : pieDatas) {
            totalPoint += pieData.getPoint();
        }

        for(PieData pieData : pieDatas) {
            pieData.setPersent(pieData.getPoint() / totalPoint * 100);
            pieData.setAngle(pieData.getPersent() * 0.01f * 360f);
        }
    }

    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        bgPaint.setStrokeWidth(10);
        bgPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setStrokeWidth(10);
        linePaint.setAntiAlias(true);
    }

    private void drawGraph(Canvas canvas) {

        int pieSpec = getWidth() >= getHeight() ? getHeight() / 2 : getWidth() / 2;

        int width = getWidth();
        int height = getHeight();

        float left = (width - pieSpec) / 2;
        float top = (height - pieSpec) / 2;
        float right = getWidth() - left;
        float bottom = getHeight() - top;

        rectF = new RectF(left, top, right, bottom);

        radius = pieSpec / 2;

        int angles = 0;

        for(PieData pieData : pieDatas) {
            bgPaint.setColor(pieData.getColor());

            canvas.drawArc(rectF, angles, pieData.getAngle(), true, bgPaint);

            angles += pieData.getAngle();
        }
    }

    private void drawSeparationLines(Canvas canvas) {

        int angles = 0;

        for(PieData pieData : pieDatas) {

            pointF.set((float) (Math.cos(Math.toRadians(angles))),
                    (float) (Math.sin(Math.toRadians(angles))));
            normalizeVector(pointF);

            float x1 = pointF.x * (radius + ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerX();
            float y1 = pointF.y * (radius + ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerY();

            linePaint.setColor(Color.WHITE);
            canvas.drawLine(rectF.centerX(), rectF.centerY(), x1, y1, linePaint);

            angles += pieData.getAngle();
        }

    }

    private void drawLables(Canvas canvas) {

        linePaint.setTextSize(50);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.FILL);

        int angles = 0;
        int centerAngle = 0;

        for(int i = 0 ; i < pieDatas.size() ; i++) {

            angles += pieDatas.get(i).getAngle();

            centerAngle = (int) (angles - (pieDatas.get(i).getAngle() / 2));

            pointF.set((float) (Math.cos(Math.toRadians(centerAngle))),
                    (float) (Math.sin(Math.toRadians(centerAngle))));
            normalizeVector(pointF);

            float x1 = pointF.x * (radius+ ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerX();
            float y1 = pointF.y * (radius + ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerY();

            float x2 = pointF.x * (radius * 1.5f  + ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerX();
            float y2 = pointF.y * (radius * 1.5f  + ChartRendererUntil.px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + rectF.centerY();


            linePaint.setColor(Color.BLACK);
            canvas.drawLine(x1, y1, x2, y2, linePaint);

            bgPaint.setColor(pieDatas.get(i).getColor());
            drawCircleLables(canvas, x2, y2);

            Rect textBounds = new Rect();
            linePaint.getTextBounds((int) pieDatas.get(i).getPersent() + "%", 0, ((int) pieDatas.get(i).getPersent() + "%").length(), textBounds);//get text bounds, that can get the text width and height
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.left - textBounds.right;

            linePaint.setColor(Color.WHITE);
            canvas.drawText((int) pieDatas.get(i).getPersent() + "%", x2 + textWidth / 2, y2 + textHeight / 2, linePaint);
        }

    }

    private void drawCircleLables(Canvas canvas, float cx, float cy) {

        canvas.drawCircle(cx, cy, radius / 3, bgPaint);

    }

    private void drawCenterCircle(Canvas canvas) {
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.WHITE);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius/2, linePaint);
    }

    private void normalizeVector(PointF point) {
        final float abs = point.length();
        point.set(point.x / abs, point.y / abs);
    }


    public class DrawPieRendererRunnable implements Runnable {

        private Canvas canvas;

        public DrawPieRendererRunnable(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {

            comparePieData();

            drawGraph(canvas);
            drawSeparationLines(canvas);
            drawLables(canvas);
            drawCenterCircle(canvas);
        }

    }
}
