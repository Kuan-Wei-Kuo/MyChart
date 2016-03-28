package com.kuo.mychart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuo.mychart.R;
import com.kuo.mychart.model.ChartItemData;

import java.util.ArrayList;

/**
 * Created by Kuo on 2016/3/28.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<ChartItemData> chartItemDatas = new ArrayList<>();
    private Context context;

    private OnItemClickListener onItemClickListener;

    public interface  OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(Context context, ArrayList<ChartItemData> chartItemDatas) {
        this.context = context;
        this.chartItemDatas = chartItemDatas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_chart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.chart_name.setText(chartItemDatas.get(position).name);
        holder.chart_interview.setText(chartItemDatas.get(position).interview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                    onItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chartItemDatas.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        protected TextView chart_name, chart_interview;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            chart_name = (TextView) itemView.findViewById(R.id.chart_name);
            chart_interview = (TextView) itemView.findViewById(R.id.chart_interview);
        }
    }
}
