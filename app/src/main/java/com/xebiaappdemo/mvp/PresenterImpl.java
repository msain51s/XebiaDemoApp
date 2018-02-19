package com.xebiaappdemo.mvp;

import com.xebiaappdemo.db.DatabaseHelper;
import com.xebiaappdemo.model.WeatherResponse;
import com.xebiaappdemo.mvp.Contract.Presenter;

/**
 * Created by Administrator on 2/14/2018.
 */

public class PresenterImpl implements Presenter,Contract.Model.OnLoadListener {
    private Contract.View mView;
    private Contract.Model mModel;

    public PresenterImpl(Contract.View view, Contract.Model model){
        this.mView=view;
        this.mModel=model;
    }
    @Override
    public void getData() {
       if(mView!=null)
           mView.showLoader();
       if(mModel!=null)
           mModel.hitRequestToGetData(this);
    }

    @Override
    public void onDestroy() {
       mView=null;
    }

    @Override
    public void onSuccess(WeatherResponse response) {
       if(mView!=null) {
           mView.dismissLoader();
           mView.updateCityInfoUI(response.getCity());
           mView.updateListUI(response.getList());
       }
    }

    @Override
    public void onError(String errorMessage) {
        if(mView!=null) {
            mView.dismissLoader();
            mView.showToastMessage(errorMessage);
        }
    }
}
