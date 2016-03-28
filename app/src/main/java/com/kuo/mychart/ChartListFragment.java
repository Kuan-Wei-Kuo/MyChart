package com.kuo.mychart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuo.mychart.adapter.RecyclerViewAdapter;
import com.kuo.mychart.model.ChartItemData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/28.
 */
public class ChartListFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

            init();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), computeChartItemData());
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                String sub_text = "";
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                switch (position) {
                    case 0:
                        PieFragment pieFragment = new PieFragment();
                        fragmentTransaction.replace(R.id.frame_layout, pieFragment, "pieFragment");
                        fragmentTransaction.addToBackStack("pieFragment");
                        sub_text = "Pie Chart";
                        break;
                    case 1:
                        ColumnFragment columnFragment = new ColumnFragment();
                        fragmentTransaction.replace(R.id.frame_layout, columnFragment, "columnFragment");
                        fragmentTransaction.addToBackStack("columnFragment");
                        sub_text = "Horizontal Column Chart";
                        break;
                    case 2:
                        LineFragment lineFragment = new LineFragment();
                        fragmentTransaction.replace(R.id.frame_layout, lineFragment, "lineFragment");
                        fragmentTransaction.addToBackStack("lineFragment");
                        sub_text = "Horizontal Line Chart";
                        break;
                }

                fragmentTransaction.commit();

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getSupportActionBar().setSubtitle(sub_text);
                mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private ArrayList<ChartItemData> computeChartItemData() {

        ArrayList<ChartItemData> chartItemDatas = new ArrayList<>();

        String interviewD = "A simple demonstration of the ";

        String[] name = {"Pie Chart", "Horizontal Column Chart", "Horizontal Line Chart"};
        String[] interview = {interviewD + "pie chart", interviewD + "horizontal column chart", interviewD + "horizontal line chart"};

        for(int i = 0 ; i < name.length ; i++) {
            ChartItemData chartItemData = new ChartItemData(name[i], interview[i]);
            chartItemDatas.add(chartItemData);
        }

        return chartItemDatas;
    }
}
