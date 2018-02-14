package com.xebiaappdemo.mvp;

import com.xebiaappdemo.model.City;
import com.xebiaappdemo.model.WeatherList;
import com.xebiaappdemo.model.WeatherResponse;

import java.util.List;

/**
 * Created by Administrator on 2/14/2018.
 */

public class Contract {
    public interface View{
        void showLoader();
        void dismissLoader();
        void updateListUI(List<WeatherList> list);
        void updateCityInfoUI(City city);
        void showToastMessage(String message);
    }

    public interface Presenter{
        void getData();
        void onDestroy();
    }

    interface Model{
        void hitRequestToGetData(OnLoadListener listener);

        interface OnLoadListener{
            void onSuccess(WeatherResponse response);
            void onError(String errorMessage);
        }
    }
}
