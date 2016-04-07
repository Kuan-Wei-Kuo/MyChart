package com.kuo.mychartlib.handler;

import android.graphics.PointF;
import android.graphics.RectF;

import com.kuo.mychartlib.presenter.ChartCompute;

/*
 * Created by Kuo on 2016/4/1.
 */

public class ComputeZoom {

    private float lastSpan;

    private PointF viewfocus = new PointF();

    protected void computeZoom(float scale, float focusX, float focusY, ChartCompute chartCompute) {

        float newWidth = scale * chartCompute.getCurViewport().width();

        float newHeight = scale * chartCompute.getCurViewport().height();

        hitTest(focusX, focusY, viewfocus, chartCompute.getMinViewport(), chartCompute.getCurViewport());

        float left = viewfocus.x - newWidth * (focusX - chartCompute.getMinViewport().left)
                / chartCompute.getMinViewport().width();


        float top = viewfocus.y - newHeight * (chartCompute.getMinViewport().bottom - focusY)
                / chartCompute.getMinViewport().height();

        float right = left + newWidth;

        float bottom = top + newHeight;

        setCurViewport(left, top, right, bottom, chartCompute);
    }

    protected void computeHorizontalZoom(float scale, float focusX, float focusY, ChartCompute chartCompute) {

        float newWidth = scale * chartCompute.getCurViewport().width();

        hitTest(focusX, focusY, viewfocus, chartCompute.getMinViewport(), chartCompute.getCurViewport());

        float left = viewfocus.x - newWidth * (focusX - chartCompute.getMinViewport().left)
                / chartCompute.getMinViewport().width();

        float top = chartCompute.getCurViewport().top;
        float right = left + newWidth;
        float bottom = chartCompute.getCurViewport().bottom;

        setCurViewport(left, top, right, bottom, chartCompute);
    }

    protected void computeVerticalZoom(float scale, float focusX, float focusY, ChartCompute chartCompute) {

        float newHeight = scale * chartCompute.getCurViewport().height();

        hitTest(focusX, focusY, viewfocus, chartCompute.getMinViewport(), chartCompute.getCurViewport());

        float left = chartCompute.getCurViewport().left;

        float top = viewfocus.y - newHeight * (chartCompute.getMinViewport().bottom - focusY)
                / chartCompute.getMinViewport().height();

        float right = chartCompute.getCurViewport().right;

        float bottom = top + newHeight;


        setCurViewport(left, top, right, bottom, chartCompute);
    }

    private void setCurViewport(float left, float top, float right, float bottom, ChartCompute chartCompute) {

        if (right - left < chartCompute.getMinViewport().width()) {

            right = left + chartCompute.getMinViewport().width();

            if (left < chartCompute.getMinViewport().left) {

                left = chartCompute.getMinViewport().left;
                right = left + chartCompute.getMinViewport().width();

            } else if (right > chartCompute.getMinViewport().right) {

                right = chartCompute.getMinViewport().right;
                left = right - chartCompute.getMinViewport().width();

            }
        }

        if (bottom - top < chartCompute.getMinViewport().height()) {

            bottom = top + chartCompute.getMinViewport().height();

            if (top < chartCompute.getMinViewport().top) {

                top = chartCompute.getMinViewport().top;
                bottom = top + chartCompute.getMinViewport().height();

            } else if (bottom > chartCompute.getMinViewport().bottom) {

                bottom = chartCompute.getMinViewport().bottom;
                top = bottom - chartCompute.getMinViewport().height();

            }
        }

        chartCompute.getCurViewport().left = Math.min(chartCompute.getMinViewport().left, left);
        chartCompute.getCurViewport().top = Math.min(chartCompute.getMinViewport().top, top);
        chartCompute.getCurViewport().right = Math.max(chartCompute.getMinViewport().right, right);
        chartCompute.getCurViewport().bottom = Math.max(chartCompute.getMinViewport().bottom, bottom);
    }

    private boolean hitTest(float x, float y, PointF dest, RectF contentRectF, RectF currentViewport) {
        if (!contentRectF.contains((int) x, (int) y)) {
            return false;
        }

        dest.set(
                currentViewport.left
                        + currentViewport.width()
                        * (x - contentRectF.left) / contentRectF.width(),
                currentViewport.top
                        + currentViewport.height()
                        * (y - contentRectF.bottom) / -contentRectF.height());
        return true;
    }

    public void setLastSpan(float lastSpan) {
        this.lastSpan = lastSpan;
    }

    public float getScale(float currSpan) {

        float scale = 2.0f - lastSpan / currSpan;

        if(Float.isInfinite(scale))
            scale = 1;

        lastSpan = currSpan;

        return scale;
    }
}
