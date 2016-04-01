package com.kuo.mychartlib.handler;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Kuo on 2016/4/1.
 */
public class ChartScroller extends Scroller {

    public ChartScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ChartScroller(Context context) {
        super(context);
    }


}
