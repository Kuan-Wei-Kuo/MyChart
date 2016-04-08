package com.kuo.mychartlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kuo.mychartlib.handler.ChartTouchHandler;
import com.kuo.mychartlib.listener.ChartListener;
import com.kuo.mychartlib.model.ChartData;
import com.kuo.mychartlib.model.ChartGenericArrayList;
import com.kuo.mychartlib.model.SelectData;
import com.kuo.mychartlib.presenter.ChartCompute;
import com.kuo.mychartlib.renderer.AbsChartRenderer;

import java.util.ArrayList;

/*
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartView extends View implements ChartListener {

    private ChartTouchHandler chartTouchHandler;

    private ChartCompute chartCompute;

    private AbsChartRenderer absChartRenderer;

    private ChartGenericArrayList<ArrayList<? extends ChartData>> chartData = new ChartGenericArrayList<>();

    private SelectData selectData;

    public AbsChartView(Context context) {
        this(context, null, 0);
    }

    public AbsChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        chartCompute = new ChartCompute();
        chartTouchHandler = new ChartTouchHandler(getContext(), this);

        //初始化避免錯誤
        chartData.setChartData(new ArrayList<ChartData>());

        selectData = new SelectData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        absChartRenderer.computeGraph();
        absChartRenderer.drawGraph(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chartTouchHandler.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartCompute.setChartWidth(w);
        chartCompute.setChartHeight(h);

        absChartRenderer.prepareCompute();
    }

    @Override
    public void computeScroll() {

        if (chartTouchHandler.compueScroll())
            ViewCompat.postInvalidateOnAnimation(this);

        super.computeScroll();
    }

    @Override
    public void setAbsChartRenderer(AbsChartRenderer absChartRenderer) {
        this.absChartRenderer = absChartRenderer;
    }

    @Override
    public void setOrientation(int orientation) {
        chartCompute.setOrientation(orientation);
    }

    @Override
    public void setTouchEnable(boolean enable) {
        chartTouchHandler.setEnable(enable);
    }

    @Override
    public void setChartData(ArrayList<? extends ChartData> chartData) {
        if(chartData == null)
            this.chartData.setChartData(new ArrayList<ChartData>());
        else
            this.chartData.setChartData(chartData);
    }

    public void updateChart() {
        absChartRenderer.prepareCompute();
        invalidate();
    }

    @Override
    public ChartCompute getChartCompute() {
        return chartCompute;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public int getOrientation() {
        return chartCompute.getOrientation();
    }

    @Override
    public boolean isTouchEnable() {
        return chartTouchHandler.isEnable();
    }

    @Override
    public ArrayList<? extends ChartData> getChartData() {
        return chartData.getChartData();
    }

    @Override
    public SelectData getSelectData() {
        return selectData;
    }

}
