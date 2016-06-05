package com.kuo.mychart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuo.mychart.ColumnFragment;
import com.kuo.mychart.LineFragment;
import com.kuo.mychart.PieFragment;
import com.kuo.mychart.R;
import com.kuo.mychart.adapter.ViewPagerAdapter;
import com.kuo.mychart.common.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by User on 2016/6/4.
 */
public class ViewPagerWithChartFragment extends Fragment {

    private View rootView;

    private SlidingTabLayout slidingTabLayout;

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
            init();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragments(), getTitles(), getChildFragmentManager()));

        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabLayout);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

    }

    private ArrayList<Fragment> getFragments() {

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new ColumnFragment());
        fragments.add(new TowChartFragment());
        fragments.add(new PieFragment());

        return fragments;

    }

    private ArrayList<String> getTitles() {

        ArrayList<String> titles = new ArrayList<>();

        titles.add("Column Chart");
        titles.add("Scroll Chart");
        titles.add("Pie Chart");

        return titles;
    }
}
