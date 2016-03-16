package com.kuo.mychart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.kuo.mychart.model.ViewPort;
import com.kuo.mychart.until.ChartRendererUntil;

import java.util.ArrayList;

/**
 * Created by User on 2016/3/12.
 */
public class ViewAnamatorTest extends View {

    private Paint axisPaint;
    private float padding = 0;
    private float textWidth, maxTextHeight;
    private float textTotalWidth;

    private ScaleGestureDetector scaleGestureDetector;

    private Canvas canvas;

    public ViewAnamatorTest(Context context) {
        super(context);

        init();
    }

    public ViewAnamatorTest(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ViewAnamatorTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private Matrix mScaleMatrix = new Matrix();

    private void init() {

        axisPaint = new Paint();
        axisPaint.setColor(ChartRendererUntil.CHART_GREY);
        axisPaint.setTextSize(ChartRendererUntil.dp2px(getContext().getResources().getDisplayMetrics().density, 15));

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                float scale = 2.0f - detector.getScaleFactor();

                if (Float.isInfinite(scale)) {
                    scale = 1;
                }

                for(ViewPort viewPort : viewPorts) {

                    float newWidth = scale * viewPort.width();

                    float left = viewPort.left * scale;
                    float right = left + newWidth;

                    viewPort.left = left;
                    viewPort.right = right;

                    Log.d("left", left + "");
                }

                Log.d("scale", scale + "");

                invalidate();
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        for(String string : axisXs) {

            float left = count * (graphWidth + graphPadding);
            float top = 0;
            float right = left + graphWidth;
            float bottom = graphheight;

            ViewPort viewPort = new ViewPort(left, top, right, bottom);
            viewPorts.add(viewPort);

            count++;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxisX(canvas);

        //canvas.scale(newScale, 1, pointF.x, 1);
        drawGraph(canvas);
        //drawTest(canvas);
    }

    private float scrollX = 0;
    private float tLeft = 150, tRight = 300, tTop = 10, tBottom = 100;
    private RectF rectF = new RectF(0, 0, 300, 300);

    private void drawTest(Canvas canvas) {
        canvas.drawRect(rectF, axisPaint);
    }

    private void drawAxisX(Canvas canvas) {

        String[] axisXs = {"01/01", "01/02", "01/03", "01/04", "01/05", "01/06", "01/07", "01/08"};
        int count = 0;

        textTotalWidth = (textWidth + padding) * axisXs.length;

        Rect textBounds = new Rect();
        axisPaint.getTextBounds(axisXs[0], 0, axisXs[0].length(), textBounds);
        textWidth = textBounds.right - textBounds.left;

        for(String string : axisXs) {

            axisPaint.getTextBounds(string, 0, string.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            int textWidth = textBounds.right - textBounds.left;

            if(this.textWidth < textWidth) {
                this.textWidth = textWidth;
            }

            if(maxTextHeight < textHeight) {
                maxTextHeight = textHeight;
            }
        }

        for(int i = 0 ; i < axisXs.length ; i++) {
            //canvas.drawText(string, count * (textWidth + padding) + scrollX, maxTextHeight / 2 + 100, axisPaint);
            //canvas.drawText(string, count * textWidth + scrollX, maxTextHeight / 2 + 100, axisPaint);
            canvas.drawText(axisXs[i], viewPorts.get(i).left, viewPorts.get(i).bottom + 30, axisPaint);
        }

    }

    private float graphPadding = 15;
    private float graphWidth = 100;
    private float graphheight = 200;

    private ArrayList<ViewPort> viewPorts = new ArrayList<>();

    private void drawGraph(Canvas canvas) {
        for(ViewPort rectF : viewPorts) {

            canvas.drawRect(rectF, axisPaint);
        }
    }

    private float downX, downY, dScrollX, dPaddingX, dScale, dPadding;
    private int motionState;
    private static final int MOTION_STATE_DRAG = 0;
    private static final int MOTION_STATE_ZOOM = 1;
    private static final int MOTION_STATE_NONE = -1;
    private PointF pointF = new PointF();

    Matrix matrix = new Matrix();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                dScrollX = scrollX - event.getX();

                motionState = MOTION_STATE_DRAG;

                break;
            case MotionEvent.ACTION_MOVE:

                if(motionState == MOTION_STATE_DRAG) {
                    //scroll(event, dScrollX);
                } else if(event.getPointerCount() >= 2) {
                    //zoom(event, dPaddingX, dScale, dPadding);
                    testZoom(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                dPadding = padding - (float) Spacing(event);
                //dPadding = (float) Spacing(event) - padding;
                //dPadding = padding;
                //dPaddingX = (float) Spacing(event) + padding;
                dPaddingX = (float) Spacing(event);

                dScale = newScale;


                if(event.getPointerCount() >= 2) {
                    if(Spacing(event) > 10) {
                        motionState = MOTION_STATE_ZOOM;
                        matrix.set(getMatrix());
                        MidPoint(pointF, event);
                    }
                }

                break;
            case MotionEvent.ACTION_POINTER_UP :
                motionState = MOTION_STATE_NONE;
                break;
        }

        invalidate();*/
        return scaleGestureDetector.onTouchEvent(event);
    }

    private void scroll(MotionEvent event, float dX) {
        scrollX = event.getX() + dX;

        if(textTotalWidth + scrollX + textWidth / 2  - padding <= getWidth()) {
            scrollX = getWidth() - textTotalWidth - textWidth / 2 + padding;
        } else if(scrollX >= 0) {
            scrollX = 0;
        }

    }

    private void zoom(MotionEvent event, float dX, float dScale, float dPadding) {

        getMatrix().set(matrix);
        newScale = (float) Spacing(event) / dX * dScale;

        if(newScale <= 1) {
            newScale = 1;
        }

        Log.d("Spacing(event) / dX", "" + (float) Spacing(event) / dX);
        Log.d("newScale", newScale + "");

        padding = dPadding + (float) Spacing(event) * (newScale - dScale);
        //padding = (float) Spacing(event) + (float) Spacing(event) / dX * dPadding;
        //dScrollX = scrollX + textTotalWidth;

        //Log.d("scrollX", "" + scrollX);
        //Log.d("padding", "" + padding);

        Log.d("padding", padding + "");

        if(padding <= 0) {
            padding = 0;
        }
    }

    private void testZoom(MotionEvent event) {


        //float left = pointF.x - event.getX(0)
    }

    private float newScale = 1f;

    private double Spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }

    //兩點中心
    private void MidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}
