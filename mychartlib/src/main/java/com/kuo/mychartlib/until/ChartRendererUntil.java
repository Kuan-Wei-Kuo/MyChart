package com.kuo.mychartlib.until;

import android.graphics.Color;

/*
 * Created by Kuo on 2016/3/7.
 */

public class ChartRendererUntil {

    public static int CHART_RED = Color.parseColor("#EF5350");
    public static int CHART_GREEN = Color.parseColor("#66BB6A");
    public static int CHART_YELLOW = Color.parseColor("#FFEE58");
    public static int CHART_GREY = Color.parseColor("#424242");
    public static int CHART_ORANGE = Color.parseColor("#FF7043");
    public static int CHART_BROWN = Color.parseColor("#8D6E63");
    public static int CHART_PURPLE = Color.parseColor("#AB47BC");
    public static int CHART_PINK = Color.parseColor("#EC407A");
    public static int CHART_WHITE = Color.parseColor("#ECEFF1");
    public static int CHART_WHITE_ALPHA = Color.parseColor("#96eceff1");

    public static int dp2px(float density, int dp){
        return (int) Math.ceil(dp * density);
    }
    public static int px2dp(float density, int px) {
        return (int) Math.ceil(px / density);
    }
}