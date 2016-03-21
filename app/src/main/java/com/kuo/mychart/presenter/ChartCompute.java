package com.kuo.mychart.presenter;

import com.kuo.mychart.model.Viewport;

/**
 * Created by User on 2016/3/21.
 */
public class ChartCompute {

    protected Viewport curViewport;
    protected Viewport minViewport;

    public ChartCompute(Viewport minViewport) {
        this.minViewport = minViewport;
        curViewport = new Viewport(minViewport.left, minViewport.top, minViewport.right, minViewport.bottom);
    }

    public Viewport getCurViewport() {
        return curViewport;
    }

    public Viewport getMinViewport() {
        return minViewport;
    }
}
