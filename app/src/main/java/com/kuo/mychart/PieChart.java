package com.kuo.mychart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.kuo.mychart.model.PieData;

import java.util.ArrayList;

/**
 * Created by User on 2016/2/28.
 */
public class PieChart extends LinearLayout {

    private PieView pieView;

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        onCreateView();
    }

    public PieChart(Context context) {
        super(context);

        onCreateView();
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        onCreateView();
    }

    private void onCreateView() {

        pieView = new PieView(getContext());

        addView(pieView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int pieSpec = width >= height ? height : width;

        pieView.setLayoutParams(new LayoutParams(pieSpec, pieSpec));

        this.setGravity(Gravity.CENTER);
    }

    public void setPieData(ArrayList<PieData> pieDatas) {
        pieView.setPieData(pieDatas);
    }
}
