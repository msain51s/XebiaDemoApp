package com.xebiaappdemo.mvp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xebiaappdemo.model.City;
import com.xebiaappdemo.model.Coord;
import com.xebiaappdemo.model.Temp;
import com.xebiaappdemo.model.Weather;
import com.xebiaappdemo.model.WeatherList;
import com.xebiaappdemo.model.WeatherResponse;
import com.xebiaappdemo.network_call.VolleySingleton;
import com.xebiaappdemo.utility.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2/14/2018.
 */

public class ModelImpl implements Contract.Model {
    private Context context;

    public ModelImpl(Context context){
        this.context=context;
    }
    @Override
    public void hitRequestToGetData(OnLoadListener listener) {
       getData(listener);
    }


 // Todo Volley Regquest call for getting Network data
   public void getData(final OnLoadListener listener){
            String url= Connection.URLWCF;

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            String serviceResponse=response.toString();
                            WeatherResponse responseModel=null;
                            City cityModel=null;
                            Coord coordModel=null;
                            Temp tempModel=null;
                            Weather weatherModel=null;
                            WeatherList weatherListModel=null;
                            if(serviceResponse!=null){

                                try {
                                    responseModel=new WeatherResponse();
                                    // Todo filling city data
                                    JSONObject cityJson=response.getJSONObject("city");
                                    cityModel=new City();
                                    coordModel=new Coord();
                                    cityModel.setId(cityJson.getString("id"));
                                    cityModel.setName(cityJson.getString("name"));

                                    JSONObject coordJson=cityJson.getJSONObject("coord");
                                    coordModel.setLat(coordJson.getString("lon"));
                                    coordModel.setLon(coordJson.getString("lat"));
                                    cityModel.setCoord(coordModel);

                                    cityModel.setCountry(cityJson.getString("country"));
                                    cityModel.setPopulation(cityJson.getString("population"));

                                    responseModel.setCity(cityModel);

                                    listener.onSuccess(responseModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onError("Service is down, please try again later");
                        }
                    }
            );
                VolleySingleton.getInstance(context).getRequestQueue().add(getRequest);
            }



}
