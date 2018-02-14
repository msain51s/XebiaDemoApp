package com.xebiaappdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xebiaappdemo.model.City;
import com.xebiaappdemo.mvp.Contract;
import com.xebiaappdemo.mvp.ModelImpl;
import com.xebiaappdemo.mvp.PresenterImpl;
import com.xebiaappdemo.utility.Utils;

public class MainActivity extends AppCompatActivity implements Contract.View{
    private ProgressBar loader;
    private RecyclerView recyclerView;
    private TextView cityName,countryName,population;
    private Contract.Presenter mPresenter;
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

  // Todo hit request on page load at first time
        if(Utils.isInternetConnected(this))
            mPresenter.getData();
        else
            showToastMessage("Internet is not connected, please check your connection.");
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
    public void updateListUI() {

    }

    @Override
    public void updateCityInfoUI(City city) {
        cityName.setText("City : "+city.getName());
        countryName.setText("Country : "+city.getCountry());
        population.setText("Population : "+city.getPopulation());
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
