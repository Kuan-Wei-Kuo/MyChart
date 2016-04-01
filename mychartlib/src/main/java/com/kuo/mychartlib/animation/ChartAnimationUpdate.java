package com.kuo.mychartlib.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import com.kuo.mychartlib.presenter.ChartCompute;

/**
 * Created by Kuo on 2016/4/1.
 */
public class ChartAnimationUpdate implements ObjectAnimator.AnimatorUpdateListener {

    protected ChartCompute chartCompute;

    public ChartAnimationUpdate(ChartCompute chartCompute) {
        this.chartCompute = chartCompute;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
