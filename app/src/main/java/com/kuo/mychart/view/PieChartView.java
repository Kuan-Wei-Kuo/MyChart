package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.kuo.mychart.model.PieData;
import com.kuo.mychart.renderer.AbsChartRenderer;
import com.kuo.mychart.renderer.PieChartRenderer;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/7.
 */
public class PieChartView extends AbsChartView {

    private AbsChartRenderer absChartRenderer;
    private ArrayList<PieData> pieDatas;

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(pieDatas != null) {
            absChartRenderer = new PieChartRenderer(getContext(), getWidth(), getHeight(), pieDatas);
            absChartRenderer.onDraw(canvas);
        }

    }

    public void setPieData(ArrayList<PieData> pieDatas) {
        this.pieDatas = pieDatas;
        invalidate();
    }
}
