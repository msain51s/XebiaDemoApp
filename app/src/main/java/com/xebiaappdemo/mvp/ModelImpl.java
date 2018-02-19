package com.xebiaappdemo.mvp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xebiaappdemo.db.DatabaseHelper;
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
import java.util.List;
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

                               // Todo filling list data
                                    List<WeatherList> weatherList=new ArrayList<>();
                                    JSONArray listJsonArray=response.getJSONArray("list");
                                    int size=listJsonArray.length();
                                    JSONObject listJsonObj=null,tempJsonObj;
                                    for(int i=0;i<size;i++){
                                        listJsonObj=listJsonArray.getJSONObject(i);
                                        weatherListModel=new WeatherList();

                                        tempModel=new Temp();
                                        tempJsonObj=listJsonObj.getJSONObject("temp");
                                        tempModel.setDay(tempJsonObj.getString("day"));
                                        tempModel.setMin(tempJsonObj.getString("min"));
                                        tempModel.setMax(tempJsonObj.getString("max"));
                                        tempModel.setNight(tempJsonObj.getString("night"));
                                        tempModel.setEve(tempJsonObj.getString("eve"));
                                        tempModel.setMorn(tempJsonObj.getString("morn"));
                                        weatherListModel.setTemp(tempModel);

                                        weatherListModel.setPressure(listJsonObj.getString("pressure"));
                                        weatherListModel.setHumidity(listJsonObj.getString("humidity"));

                                        JSONArray weatherJsonArr=listJsonObj.getJSONArray("weather");
                                        JSONObject weatherJsonObj=weatherJsonArr.getJSONObject(0);
                                        weatherModel=new Weather();
                                        weatherModel.setMain(weatherJsonObj.getString("main"));
                                        weatherModel.setDescription(weatherJsonObj.getString("description"));
                                        weatherListModel.setWeather(weatherModel);

                                       weatherList.add(weatherListModel);

                                    }
                                    responseModel.setList(weatherList);

                            //Todo deleting table data before inserting new Data
                                    if(responseModel.getList().size()>0)
                                        DatabaseHelper.getInstance(context).deleteInfoToDB();

                           // Todo Inserting data to Database
                                    for(WeatherList mo:responseModel.getList()) {
                                        DatabaseHelper.getInstance(context).insertInfoToDB(responseModel.getCity().getName(),
                                                responseModel.getCity().getCountry(),responseModel.getCity().getPopulation(),mo);
                                    }

                          //  Todo Getting data from Database and updating the UI
                                      responseModel=new WeatherResponse();
                                      responseModel.setCity(DatabaseHelper.getInstance(context).getCityInfoFromDB());
                                      responseModel.setList(DatabaseHelper.getInstance(context).getWeatherListFromDB());
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
