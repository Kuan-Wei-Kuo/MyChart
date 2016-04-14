package com.kuo.mychartlib.until;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/*
 * Created by Kuo on 2016/3/7.
 */

public class ChartRendererUntil {

    public static int CHART_RED = Color.parseColor("#EF5350");
    public static int CHART_GREEN = Color.parseColor("#66BB6A");
    public static int CHART_YELLOW = Color.parseColor("#FFEE58");
    public static int CHART_GREY = Color.parseColor("#424242");
    public static int CHART_GREY_500 = Color.parseColor("#9E9E9E");
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

    public static int getTextWidth(Paint paint, String text) {

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);

        return rect.width();
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
