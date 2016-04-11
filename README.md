###New Update 2016/04/01
>Update ChartTouchHandler.class and Add ChartScroll.class
>>更新水平滑動演算法，使用Scroller與VelocityTracker達到慣性移動的效果。
>>原先打算自己寫個Buffer但有點懶...你們知道的。
>>
>>Use Scroller with VelocityTracker to reach smooth move and fling move.
***

>MyChart is a sample & easy to use chart library for Android. It runs on API level 15 and upwards.
>It's had no animation and powerful, so it's very sample example.
>if you have any question, you can writing e-mail for me.
>Okay, thank you for wacthing.
>
>MyChart 是一個功能簡單與方便使用的圖表在Android上，Android版本為15以上。
>它沒有動畫與強大的功能，所以這只是一個非常簡單的範例。
>如果你有任何問題，歡迎寄信給我看看。
>
>MyChart just had only three chart view. I'll keep on update.
>
>目前只有製作三種圖表，未來會繼續斟酌更新。

###長條圖
<img width="500" height="300" src="https://googledrive.com/host/0B5fOJF9g7N2SMXktVDRRei1SdEU"/>

###摺線圖
<img width="500" height="300" src="https://googledrive.com/host/0B5fOJF9g7N2SN3NCWW00WXNrQWs"/>

###圓餅圖
<img width="500" height="300" src="https://googledrive.com/host/0B5fOJF9g7N2SUE5SWmlkSmNDMGs"/>

#How to Use

##Set XML
```xml
<com.kuo.mychartlib.view.ColumnChartView
        android:id="@+id/columnChartView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

#S#et Java
```java
private ArrayList<ColumnData> computeColumnData(int size, int maxValue) {

        int[] colors = {ChartRendererUntil.CHART_GREEN, ChartRendererUntil.CHART_PINK, 
        ChartRendererUntil.CHART_RED, ChartRendererUntil.CHART_YELLOW,
        ChartRendererUntil.CHART_BROWN, ChartRendererUntil.CHART_ORANGE, 
        ChartRendererUntil.CHART_GREY, ChartRendererUntil.CHART_PURPLE};

        ArrayList<ColumnData> test = new ArrayList<>();

        Random random = new Random();

        for(int i = 0 ; i < size ; i++) {
            test.add(new ColumnData("Axis " + i, 
                random.nextInt(maxValue), 
                colors[random.nextInt(colors.length)]));
        }

        return test;
}

columnChartView = (ColumnChartView) rootView.findViewById(R.id.columnChartView);
columnChartView.setColumnData(computeColumnData(size, maxValue));
```
