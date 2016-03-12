package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kuo.mychart.renderer.AbsChartRenderer;
import com.kuo.mychart.until.ChartRendererUntil;

/**
 * Created by User on 2016/3/12.
 */
public class ViewAnamatorTest extends View {

    private Paint axisPaint;
    private float padding = 50;
    private float textWidth;
    private float textTotalWidth;

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

    private void init() {

        axisPaint = new Paint();
        axisPaint.setColor(ChartRendererUntil.CHART_GREY);
        axisPaint.setTextSize(ChartRendererUntil.dp2px(getContext().getResources().getDisplayMetrics().density, 15));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxisX(canvas);
        drawGraph(canvas);
    }

    private float scrollX = 0;

    private void drawAxisX(Canvas canvas) {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        textTotalWidth = (textWidth + padding) * axisXs.length;

        Rect textBounds = new Rect();
        axisPaint.getTextBounds(axisXs[0], 0, axisXs[0].length(), textBounds);
        textWidth = textBounds.right - textBounds.left;

        for(String string : axisXs) {

            axisPaint.getTextBounds(string, 0, string.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            canvas.drawText(string, count * (textWidth + padding) + scrollX, textHeight / 2 + 100, axisPaint);

            count++;
        }

    }

    private void drawGraph(Canvas canvas) {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        Rect textBounds = new Rect();
        axisPaint.setColor(ChartRendererUntil.CHART_GREEN);

        for(String string : axisXs) {

            axisPaint.getTextBounds(string, 0, string.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            float left = count * (textWidth + padding) + scrollX;
            float top = 150;
            float right = left + textWidth / 2;
            float bottom = 250;

            RectF rectF = new RectF(left, top, right, bottom);

            canvas.drawRect(rectF, axisPaint);

            count++;
        }
    }

    private float downX, downY, dX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downX = event.getX();
                downY = event.getY();

                dX = scrollX - event.getX();

                break;
            case MotionEvent.ACTION_MOVE:

                scroll(event, dX);
                //zoom(event, dX);
                break;
        }

        invalidate();
        return true;
    }

    private void scroll(MotionEvent event, float dX) {
        scrollX = event.getX() + dX;

        if(textTotalWidth + scrollX <= getWidth()) {
            scrollX = getWidth() - textTotalWidth;
        } else if(scrollX >= 0) {
            scrollX = 0;
        }

        Log.d("scrollX", scrollX + "");
    }

    private void zoom(MotionEvent event, float dX) {
        padding = event.getX() + dX;

        if(padding <= textWidth) {
            padding = textWidth;
        }
    }
}
