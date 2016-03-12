package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
    }

    private void drawAxisX(Canvas canvas) {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        for(String string : axisXs) {

            Rect textBounds = new Rect();
            axisPaint.getTextBounds(string, 0, string.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            canvas.drawText(string, count * (textWidth + padding), textHeight / 2 + padding, axisPaint);

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

                dX = padding - event.getX();

                break;
            case MotionEvent.ACTION_MOVE:

                padding = event.getX() + dX;

                Log.d("padding", padding + "");

                break;
        }

        invalidate();
        return true;
    }
}
