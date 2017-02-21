###New Update 
>Add HackViewpager 
>Add ViewPager and ScrollView with chart.
>>暫時解決ScaleGestureDetector與Viewpager相衝的問題。
>>讓Chart可以與Viewpager ScrollView 三者同時使用。
>>
>>When IllegalArgumentException Catching ViewPager onInterceptTouchEvent.
>>Update Compute for ViewPager and ScrollView with chart.
***

>MyChart is a simple & easy to use chart library for Android. It runs on API level 15 and upwards.
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

###長條圖 Column Chart
<img width="500" height="300" src=""/>

###摺線圖 Line Chart
<img width="500" height="300" src=""/>

###圓餅圖 Pie Chart
<img width="500" height="300" src="https://lh3.googleusercontent.com/9DFkbnCTwEg0B2Z99xLT7fjcIasGsLI287NYbf6Njd7X1wY7nEcgaT2uoqnbKwE06sfsE79HxRjH66Q_GwhdN4FbbAg8wS1Vc0pqkjbiiUBho055GXp_VnK3qUPGuPwV9xUfM2FGS3rI-E8Piwy4KeJOEM1YtSOK86kcpAytif6vw13EEKCIJvcR7UhhZqnRX7tkF7iOdxzMmnjB_Dxzp2MhMAzX-CT75BTbt3-lhLZ4DvLltqlfliPA7BJBPmI_7Qxu80C2wAOzHd4hnwGZXB0Pjwyp7pSvrM5y10oG0n8h58aqvu-uyCtlG4pj3rsdsk26XogotdaKmMrYwc_YByRjFrNYaS3cz2XkJ9RjAWMuFWWIf3yIAMQNqOWOBCYosozsDoD97sIkCcXENYGEZgo3N-_Rl5Xs665L-HGdk7zNMkDXf0Nk3it5ErlLwhVYc2B5A3ur3NtKodZETy3ATtHsYCpKDXNLB-m3Z8TLA3aqw7OE1QJ5CD712mF3GDXjPKm8WdwSQQxes5gKu29G2dXATl8t5biQHGEqj6mIPLS7vITlo5cK_tF66Qah46ecAmB63h6lMXaDhHTdBc2YoI3n8QJ830Q7AireKs6E5WpZsb5nCFX8=w852-h512-no"/>

#How to Use

##Set XML
```xml
<com.kuo.mychartlib.view.ColumnChartView
        android:id="@+id/columnChartView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

##Set Java
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
