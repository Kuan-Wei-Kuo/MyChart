package com.kuo.mychart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.kuo.mychart.listener.ChartListener;
import com.kuo.mychart.listener.ColumnChartListener;
import com.kuo.mychart.model.ColumnData;
import com.kuo.mychart.model.Viewport;
import com.kuo.mychart.until.ChartRendererUntil;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/22.
 */
public class ColumnChartRenderer extends AbsChartRenderer {

    private ColumnChartListener columnChartListener;
    private ChartListener chartListener;

    private ArrayList<RectF> rectFs = new ArrayList<>();

    public ColumnChartRenderer(Context context, ChartListener chartListener, ColumnChartListener columnChartListener) {
        super(context);
        this.chartListener = chartListener;
        this.columnChartListener = columnChartListener;
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void touch(MotionEvent event, int state) {

    }

    private void drawRects(Canvas canvas) {

        Paint paint = getRectPaint();

        for(RectF rectF : rectFs) {
            paint.setColor(ChartRendererUntil.CHART_GREEN);
            canvas.drawRect(rectF, paint);
        }
    }

    private void prepareRects() {

        rectFs.clear();

        Viewport curViewport = chartListener.getChartCompute().getCurViewport();
        ArrayList<ColumnData> columnDatas = columnChartListener.getColumnData();

        int count = 0;

        float columnWidth = curViewport.width() / columnDatas.size() * 0.7f;

        float columnMargin = curViewport.width() / columnDatas.size() * 0.3f;

        for(ColumnData columnData : columnDatas) {

            float left = curViewport.left + count * (columnWidth + columnMargin);
            float top = 0;
            float right = left + columnWidth;
            float bottom = 100;

            rectFs.add(new Viewport(left, top, right, bottom));

            count++;
        }
    }


}
