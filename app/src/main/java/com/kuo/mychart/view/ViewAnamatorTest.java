package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.until.ChartRendererUntil;

import java.util.ArrayList;

/*
 * Created by User on 2016/3/12.
 */
public class ViewAnamatorTest extends AbsChartView {

    private Paint axisPaint;
    private float textWidth, maxTextHeight;

    public ViewAnamatorTest(Context context) {
        super(context);

        init();
    }

    public ViewAnamatorTest(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ViewAnamatorTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private ArrayList<Viewport> viewports = new ArrayList<>();

    private void init() {

        axisPaint = new Paint();
        axisPaint.setColor(ChartRendererUntil.CHART_GREY);
        axisPaint.setTextSize(ChartRendererUntil.dp2px(getContext().getResources().getDisplayMetrics().density, 15));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        prepareRect();

        drawAxisX(canvas);
        drawGraph(canvas);
    }

    private void prepareRect() {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        float columnWidth = getViewport().getMaxWidth() / axisXs.length;

        for(String string : axisXs) {

            float left = count * (columnWidth + graphPadding);
            float top = 0;
            float right = left + columnWidth;
            float bottom = graphheight;

            viewports.add(new Viewport(left, top, right, bottom));

            count++;
        }

    }

    private void drawAxisX(Canvas canvas) {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};

        Rect textBounds = new Rect();
        axisPaint.getTextBounds(axisXs[0], 0, axisXs[0].length(), textBounds);
        textWidth = textBounds.right - textBounds.left;

        for(String string : axisXs) {

            axisPaint.getTextBounds(string, 0, string.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            if(this.textWidth < textWidth) {
                this.textWidth = textWidth;
            }

            if(maxTextHeight < textHeight) {
                maxTextHeight = textHeight;
            }
        }

        for(int i = 0 ; i < axisXs.length ; i++) {
            canvas.drawText(axisXs[i], viewports.get(i).left, viewports.get(i).bottom + 30, axisPaint);
        }

    }

    private float graphPadding = 15;
    private float graphWidth = 100;
    private float graphheight = 200;

    private void drawGraph(Canvas canvas) {
        for(int i = 0 ; i < viewports.size() ; i++) {
            axisPaint.setColor(ChartRendererUntil.CHART_GREEN);
            canvas.drawRect(viewports.get(i), axisPaint);
        }
        axisPaint.setColor(ChartRendererUntil.CHART_GREY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setViewport(new Viewport(0, 0, MeasureSpec.getSize(widthMeasureSpec),  MeasureSpec.getSize(heightMeasureSpec)));
    }
}
