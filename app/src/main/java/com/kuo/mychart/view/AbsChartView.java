package com.kuo.mychart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kuo on 2016/3/7.
 */
public abstract class AbsChartView extends View {

    public AbsChartView(Context context) {
        super(context);
    }

    public AbsChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
