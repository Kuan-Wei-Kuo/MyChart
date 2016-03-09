package com.kuo.mychart.until;

import android.graphics.Color;

/**
 * Created by Kuo on 2016/3/7.
 */
public class ChartRendererUntil {

    public static int CHART_RED = Color.parseColor("#EF5350");
    public static int CHART_GREEN = Color.parseColor("#66BB6A");
    public static int CHART_YELLOW = Color.parseColor("#FFEE58");
    public static int CHART_GREY = Color.parseColor("#BDBDBD");

    public static int dp2px(float density, int dp){
        return (int) Math.ceil(dp * density);
    }
    public static int px2dp(float density, int px) {
        return (int) Math.ceil(px / density);
    }
}
