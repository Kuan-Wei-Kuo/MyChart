package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.kuo.mychart.model.LineData;
import com.kuo.mychart.renderer.AbsChartRenderer;
import com.kuo.mychart.renderer.LineChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/8.
 */
public class LineChartView extends AbsChartView {

    private AbsChartRenderer absChartRenderer;
    private ArrayList<LineData> lineDatas;

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        absChartRenderer = new LineChartRenderer(getContext(), getWidth(), getHeight());
        absChartRenderer.onDraw(canvas);
    }

    public void setLineDatas(ArrayList<LineData> lineDatas) {
        this.lineDatas = lineDatas;
        invalidate();
    }
}
