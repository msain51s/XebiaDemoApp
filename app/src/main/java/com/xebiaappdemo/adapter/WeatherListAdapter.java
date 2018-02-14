package com.xebiaappdemo.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xebiaappdemo.R;
import com.xebiaappdemo.model.WeatherList;

import java.util.List;

/**
 * Created by Administrator on 2/14/2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.MyViewHolder> {
    private List<WeatherList> list;
    private Activity activity;

    public WeatherListAdapter(Activity activity, List<WeatherList> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public WeatherListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.weather_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeatherList model = list.get(position);

        holder.text.setText(model.getText());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<WeatherList> getList() {
        return list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.list_chkbox);
            text = (TextView) itemView.findViewById(R.id.chkbox_text);

        }
    }
}