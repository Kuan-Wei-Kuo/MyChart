package com.kuo.mychartlib.animation;

import android.animation.ObjectAnimator;

/*
 * Created by Kuo on 2016/3/22.
 */
public class ChartAnimation {

    private ObjectAnimator.AnimatorUpdateListener animatorUpdateListener;

    public ChartAnimation(ObjectAnimator.AnimatorUpdateListener listener) {
        animatorUpdateListener = listener;
    }

    public void animateX(int duration, float start, float end) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", start, end);
        animatorX.setDuration(duration);
        animatorX.addUpdateListener(animatorUpdateListener);
        animatorX.start();
    }

}
