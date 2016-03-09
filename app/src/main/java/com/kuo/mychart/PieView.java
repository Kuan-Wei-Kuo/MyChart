package com.kuo.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kuo.mychart.model.PieData;

import java.util.ArrayList;

/*
 * Created by User on 2016/2/27.
 */
public class PieView extends ImageView {

    private ArrayList<PieData> pieDatas;
    private ArrayList<PointF> pointFs = new ArrayList<>();

    private PointF pointF = new PointF();

    private Paint p = new Paint();
    private Paint p1 = new Paint();

    private RectF oval2;

    private float radius;

    private boolean isFirst = true;

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(pieDatas != null) {
            comParePieData();

            p.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            p.setStrokeWidth(10);
            p.setAntiAlias(true);

            //p1.setColor(ContextCompat.getColor(getContext(), R.color.Grey_50));
            p1.setStrokeWidth(10);
            p1.setAntiAlias(true);

            oval2 = new RectF(getWidth() / 4, getHeight() / 4, getWidth() - (getWidth() / 4), getHeight() - (getHeight() / 4));

            radius = Math.abs(((getWidth() / 4) - (getWidth() - (getWidth() / 4)))) / 2;

            int angles = 0;

            for(int j = 0 ; j < pieDatas.size() ; j++) {


                if(j == 0) {
                    //p.setColor(pieDatas.get(j).getColor());
                    p.setColor(pieDatas.get(j).getColor());
                    canvas.drawArc(oval2, 0, pieDatas.get(j).getAngle(), true, p);
                } else {
                    //p.setColor(pieDatas.get(j).getColor());
                    p.setColor(pieDatas.get(j).getColor());
                    angles += pieDatas.get(j - 1).getAngle();
                    canvas.drawArc(oval2, angles, pieDatas.get(j).getAngle(), true, p);
                }

            }

            drawSeparationLines(canvas);
            //drawCircleLine(canvas);
            drawCenterCircle(canvas);
            drawLables(canvas);
        }

    }

    private void drawSeparationLines(Canvas canvas) {

        int angles = 0;

        for(PieData pieData : pieDatas) {

            pointF.set((float) (Math.cos(Math.toRadians(angles))),
                    (float) (Math.sin(Math.toRadians(angles))));
            normalizeVector(pointF);

            float x1 = pointF.x * (radius + px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerX();
            float y1 = pointF.y * (radius + px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerY();

            pointFs.add(new PointF(x1, y1));

            p1.setColor(Color.WHITE);
            canvas.drawLine(oval2.centerX(), oval2.centerY(), x1, y1, p1);

            angles += pieData.getAngle();
        }

    }

    private void drawLables(Canvas canvas) {

        p1.setTextSize(50);
        p1.setStrokeWidth(5);
        p1.setStyle(Paint.Style.FILL);

        int angles = 0;
        int centerAngle = 0;

        for(int i = 0 ; i < pieDatas.size() ; i++) {

            angles += pieDatas.get(i).getAngle();

            centerAngle = (int) (angles - (pieDatas.get(i).getAngle() / 2));

            pointF.set((float) (Math.cos(Math.toRadians(centerAngle))),
                    (float) (Math.sin(Math.toRadians(centerAngle))));
            normalizeVector(pointF);

            float x1 = pointF.x * (radius+ px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerX();
            float y1 = pointF.y * (radius + px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerY();

            float x2 = pointF.x * (radius * 1.5f  + px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerX();
            float y2 = pointF.y * (radius * 1.5f  + px2dp(getContext().getResources().getDisplayMetrics().density, 8)) + oval2.centerY();


            p1.setColor(Color.BLACK);
            canvas.drawLine(x1, y1, x2, y2, p1);

            p.setColor(pieDatas.get(i).getColor());
            drawCircleLables(canvas, x2, y2);

            Rect textBounds = new Rect();
            p1.getTextBounds((int) pieDatas.get(i).getPersent() + "%", 0, ((int) pieDatas.get(i).getPersent() + "%").length(), textBounds);//get text bounds, that can get the text width and height
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.left - textBounds.right;

            p1.setColor(Color.WHITE);
            canvas.drawText((int) pieDatas.get(i).getPersent() + "%", x2 + textWidth / 2, y2 + textHeight / 2, p1);
        }

    }

    private void drawCircleLables(Canvas canvas, float cx, float cy) {

        canvas.drawCircle(cx, cy, radius / 3, p);

    }

    private void drawCircleLine(Canvas canvas) {
        canvas.drawCircle(oval2.centerX(), oval2.centerY(), getWidth() / 4, p1);
    }

    private void drawCenterCircle(Canvas canvas) {
        p1.setStyle(Paint.Style.FILL);
        p1.setColor(Color.WHITE);
        canvas.drawCircle(oval2.centerX(), oval2.centerY(), radius/2, p1);
    }

    public void setPieData(ArrayList<PieData> pieDatas) {
        this.pieDatas = pieDatas;
        invalidate();
    }

    private void comParePieData() {

        int totalPoint = getTotalPoint();

        for(int i = 0 ; i < pieDatas.size() ; i++) {
            pieDatas.get(i).setPersent(pieDatas.get(i).getPoint() / totalPoint * 100);
            pieDatas.get(i).setAngle(pieDatas.get(i).getPersent() * 0.01f * 360f);
        }

    }

    private int getTotalPoint() {

        int totalPoint = 0;

        for(int i = 0 ; i < pieDatas.size() ; i++) {
            totalPoint += pieDatas.get(i).getPoint();
        }

        return totalPoint;
    }

    private void normalizeVector(PointF point) {
        final float abs = point.length();
        point.set(point.x / abs, point.y / abs);
    }

    public static int px2dp(float density, int px) {
        return (int) Math.ceil(px / density);
    }

}
