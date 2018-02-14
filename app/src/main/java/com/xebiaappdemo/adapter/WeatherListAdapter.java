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

        holder.weather_text.setText(model.getWeather().getMain());
        holder.weather_desc_text.setText(model.getWeather().getDescription());
        holder.day_temp.setText(model.getTemp().getDay());
        holder.min_temp.setText(model.getTemp().getMin());
        holder.max_temp.setText(model.getTemp().getMax());
        holder.night_temp.setText(model.getTemp().getNight());
        holder.evening_temp.setText(model.getTemp().getEve());
        holder.morning_temp.setText(model.getTemp().getMorn());
        holder.pressure_text.setText(model.getPressure());
        holder.humidity_text.setText(model.getHumidity());

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
        public TextView weather_text,weather_desc_text,
                        day_temp,min_temp,max_temp,night_temp,evening_temp,
                        morning_temp,pressure_text,humidity_text;

        public MyViewHolder(View itemView) {
            super(itemView);

            weather_text = (TextView) itemView.findViewById(R.id.weather_main_text);
            weather_desc_text= (TextView) itemView.findViewById(R.id.weather_description_text);
            day_temp= (TextView) itemView.findViewById(R.id.day_temp_value_text);
            min_temp= (TextView) itemView.findViewById(R.id.min_temp_value_text);
            max_temp= (TextView) itemView.findViewById(R.id.max_temp_value_text);
            night_temp= (TextView) itemView.findViewById(R.id.night_temp_value_text);
            evening_temp= (TextView) itemView.findViewById(R.id.eve_temp_value_text);
            morning_temp= (TextView) itemView.findViewById(R.id.mor_temp_value_text);
            pressure_text= (TextView) itemView.findViewById(R.id.pressure_value_text);
            humidity_text= (TextView) itemView.findViewById(R.id.humidity_value_text);
        }
    }
}