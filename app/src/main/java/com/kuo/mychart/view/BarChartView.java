package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kuo.mychart.model.BarData;
import com.kuo.mychart.renderer.AbsChartRenderer;
import com.kuo.mychart.renderer.BarChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/8.
 */
public class BarChartView extends AbsChartView {

    private AbsChartRenderer absChartRenderer;
    private ArrayList<BarData> barDatas;

    public BarChartView(Context context) {
        super(context);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(barDatas != null) {
            absChartRenderer.onDraw(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        absChartRenderer.touch(event, 0);
        invalidate();
        return true;
    }

    public void setBarDatas(ArrayList<BarData> barDatas) {
        this.barDatas = barDatas;
        absChartRenderer = new BarChartRenderer(getContext(), getWidth(), getHeight(), barDatas);
        invalidate();
    }
}
