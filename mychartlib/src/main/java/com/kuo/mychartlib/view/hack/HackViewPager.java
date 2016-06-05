package com.kuo.mychartlib.view.hack;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * When ScaleGestureDetector repeat touching seems to mess up the ViewGroup onInterceptTouchEvent.
 * A lot of IllegalArgumentException: pointerIndex out of range. So, I found an answer, Just catching the problem and ignoring.
 * It's a simple solution, but it's not perfect.
 *
 * @author Kuo
 */

public class HackViewPager extends ViewPager {

    public HackViewPager(Context context) {
        super(context);
    }

    public HackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException i) {
            return false;
        }

    }
}
