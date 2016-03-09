package com.kuo.mychart;

import java.io.Serializable;

/**
 * Created by Kuo on 2016/2/26.
 */
public class Until implements Serializable {
    String name;
    int point;

    Until(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
