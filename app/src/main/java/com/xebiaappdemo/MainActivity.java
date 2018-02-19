package com.xebiaappdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xebiaappdemo.adapter.WeatherListAdapter;
import com.xebiaappdemo.db.DatabaseHelper;
import com.xebiaappdemo.model.City;
import com.xebiaappdemo.model.WeatherList;
import com.xebiaappdemo.mvp.Contract;
import com.xebiaappdemo.mvp.ModelImpl;
import com.xebiaappdemo.mvp.PresenterImpl;
import com.xebiaappdemo.utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Contract.View{
    private ProgressBar loader;
    private RecyclerView recyclerView;
    private TextView cityName,countryName,population;
    private Contract.Presenter mPresenter;
    private WeatherListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<WeatherList> weatherList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter=new PresenterImpl(this,new ModelImpl(this));

   // Todo initialize view
        loader= (ProgressBar) findViewById(R.id.loader);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        cityName= (TextView) findViewById(R.id.cityNameText);
        countryName= (TextView) findViewById(R.id.cityCountryText);
        population= (TextView) findViewById(R.id.cityPopulationText);

        setupRecyclerView();

  // Todo hit request on page load at first time
        hitRequest();
    }

    @Override
    public void showLoader() {
      loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoader() {
       loader.setVisibility(View.GONE);
    }

    @Override
    public void updateListUI(List<WeatherList> list) {
       weatherList.clear();
       weatherList.addAll(list);
       adapter.notifyDataSetChanged();
    }

    @Override
    public void updateCityInfoUI(City city) {
        if(city!=null) {
            cityName.setText("City : " + city.getName());
            countryName.setText("Country : " + city.getCountry());
            population.setText("Population : " + city.getPopulation());
        }
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseHelper.getInstance(this).closeConnecion();
    }

  // Todo setup RecyclerView
    private void setupRecyclerView(){
        weatherList=new ArrayList<>();
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new WeatherListAdapter(this,weatherList);
        recyclerView.setAdapter(adapter);
    }

    public void hitRequest(){
        if(Utils.isInternetConnected(this))
            mPresenter.getData();
        else
            showToastMessage("Internet is not connected, please check your connection.");
    }
    public void performRefresh(View view) {
        hitRequest();
    }

}
