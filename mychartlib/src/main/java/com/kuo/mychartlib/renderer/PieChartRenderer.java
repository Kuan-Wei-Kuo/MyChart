package com.kuo.mychartlib.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.listener.PieDataListener;
import com.kuo.mychartlib.model.PieData;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/7.
 */
public class PieChartRenderer extends AbsChartRenderer {

    private RectF rectF;
    private PointF pointF = new PointF();

    private float radius;

    private  ChartListener chartListener;
    private  PieDataListener pieDataListener;

    public PieChartRenderer(Context context, ChartListener chartListener, PieDataListener pieDataListener) {
        super(context);

        this.chartListener = chartListener;
        this.pieDataListener = pieDataListener;
    }

    @Override
    public void prepareCompute() {

    }

    @Override
    public void computeGraph() {
        comparePieData();
    }

    @Override
    public void drawGraph(Canvas canvas) {
        drawCircleGraph(canvas);
        drawSeparationLines(canvas);
        drawLables(canvas);
        drawCenterCircle(canvas);
    }

    private void comparePieData() {

        int totalPoint = 0;

        ArrayList<PieData> pieDatas = pieDataListener.getPieData();

        for(PieData pieData : pieDatas) {
            totalPoint += pieData.getPoint();
        }

        for(PieData pieData : pieDatas) {
            pieData.setPersent(pieData.getPoint() / totalPoint * 100);
            pieData.setAngle(pieData.getPersent() * 0.01f * 360f);
        }
    }

    public void drawCircleGraph(Canvas canvas) {

        ChartCompute chartCompute = chartListener.getChartCompute();

        int pieSpec = chartCompute.getChartWidth() >= chartCompute.getChartHeight() ? chartCompute.getChartHeight() / 2 : chartCompute.getChartWidth() / 2;

        int width = chartCompute.getChartWidth();
        int height = chartCompute.getChartHeight();

        float left = (width - pieSpec) / 2;
        float top = (height - pieSpec) / 2;
        float right = width - left;
        float bottom = height - top;

        rectF = new RectF(left, top, right, bottom);

        radius = pieSpec / 2;

        float angles = 0;

        ArrayList<PieData> pieDatas = pieDataListener.getPieData();

        rectPaint.setStrokeWidth(ChartRendererUntil.dp2px(context.getResources().getDisplayMetrics().density, 5));

        for(PieData pieData : pieDatas) {
            rectPaint.setColor(pieData.getColor());

            canvas.drawArc(rectF, angles, pieData.getAngle(), true, rectPaint);

            angles += pieData.getAngle();
        }
    }

    private void drawSeparationLines(Canvas canvas) {

        float angles = 0;

        ArrayList<PieData> pieDatas = pieDataListener.getPieData();

        for(PieData pieData : pieDatas) {

            pointF.set((float) (Math.cos(Math.toRadians(angles))),
                    (float) (Math.sin(Math.toRadians(angles))));

            float x1 = pointF.x * radius + rectF.centerX();
            float y1 = pointF.y * radius + rectF.centerY();

            linePaint.setColor(ChartRendererUntil.CHART_WHITE);
            canvas.drawLine(rectF.centerX(), rectF.centerY(), x1, y1, linePaint);

            angles += pieData.getAngle();
        }

    }

    private void drawLables(Canvas canvas) {

        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.FILL);

        float angles = 0;
        float centerAngle;

        ArrayList<PieData> pieDatas = pieDataListener.getPieData();

        for (int i = 0; i < pieDatas.size() ; i++) {

            angles += pieDatas.get(i).getAngle();

            centerAngle = (int) (angles - (pieDatas.get(i).getAngle() / 2));

            pointF.set((float) (Math.cos(Math.toRadians(centerAngle))),
                    (float) (Math.sin(Math.toRadians(centerAngle))));

            float x1 = pointF.x * radius + rectF.centerX();
            float y1 = pointF.y * radius + rectF.centerY();

            float x2 = pointF.x * radius * 1.5f + rectF.centerX();
            float y2 = pointF.y * radius * 1.5f + rectF.centerY();

            linePaint.setColor(pieDatas.get(i).getColor());
            canvas.drawLine(x1, y1, x2, y2, linePaint);

            rectPaint.setColor(pieDatas.get(i).getColor());
            drawCircleLables(canvas, x2, y2, (int) pieDatas.get(i).getPersent() + "%");
        }

    }

    private void drawCircleLables(Canvas canvas, float cx, float cy, String text) {

        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0,
                text.length(), textBounds);

        int textHeight = textBounds.bottom - textBounds.top;
        int textWidth = textBounds.right - textBounds.left;

        canvas.drawCircle(cx,
                cy,
                (textHeight > textWidth ? textHeight : textWidth) * 0.8f, rectPaint);

        textPaint.setColor(ChartRendererUntil.CHART_WHITE);

        canvas.drawText(text,
                cx - textWidth / 2,
                cy + textHeight / 2, textPaint);
    }

    private void drawCenterCircle(Canvas canvas) {

        rectPaint.setColor(ChartRendererUntil.CHART_WHITE_ALPHA);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius / 2, rectPaint);

        rectPaint.setColor(ChartRendererUntil.CHART_WHITE);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius / 2.5f, rectPaint);
    }
}
