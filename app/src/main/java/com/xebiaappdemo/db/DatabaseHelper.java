package com.xebiaappdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xebiaappdemo.model.City;
import com.xebiaappdemo.model.Temp;
import com.xebiaappdemo.model.Weather;
import com.xebiaappdemo.model.WeatherList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Todo db name for this application

    private static final String DATABASE_NAME = "xebia_db";

    //Todo version number of this db

    private static final int DATABASE_VERSION = 4;

    private static DatabaseHelper dbHelper = null;

    private static SQLiteDatabase db = null;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        dbHelper = this;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        DBTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        DBTable.onUpgrade(database, oldVersion, newVersion);
    }


    public Cursor executeQuery(String sql){
        Cursor mCursor =db.rawQuery(sql, null);
        return mCursor;
    }

    public boolean executeDMLQuery(String sql){
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }


    public static DatabaseHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper = new DatabaseHelper(context);
            openConnecion();
        }
        return dbHelper;
    }

    private static void openConnecion(){
        if ( db == null ){
            db = dbHelper.getWritableDatabase();
        }
    }
    // be sure to call this method by: DatabaseHelper.getInstance.closeConnecion() when application is closed by    somemeans most likely
    // onDestroy method of application

    public synchronized void closeConnecion() {
        if(dbHelper!=null){
            dbHelper.close();
            db.close();
            dbHelper = null;
            db = null;
        }
    }

    public void insertInfoToDB(String cityName, String countryName, String population, WeatherList model) {
        String insertQuery = "Insert into " + DBTable.TABLE_NAME + "(" + DBTable.KEY_CITY_NAME_COLUMN + "," + DBTable.KEY_COUNTRY_NAME_COLUMN
                + "," + DBTable.KEY_POPULATION_COLUMN +","+DBTable.KEY_WEATHER_NAME_COLUMN+","+DBTable.KEY_WEATHER_DESCRIPTION_COLUMN+","
                + DBTable.KEY_DAY_TEMP_COLUMN+","+DBTable.KEY_MIN_TEMP_COLUMN+","+DBTable.KEY_MAX_TEMP_COLUMN+","+DBTable.KEY_NIGHT_TEMP_COLUMN+","
                + DBTable.KEY_EVE_TEMP_COLUMN+","+DBTable.KEY_MOR_TEMP_COLUMN+","+DBTable.KEY_PRESSURE_COLUMN+","+DBTable.KEY_HUMIDITY_COLUMN+ ") "
                + "values('" + cityName + "','" + countryName + "','" + population + "','"
                + model.getWeather().getMain() + "','" + model.getWeather().getDescription() + "','"
                + model.getTemp().getDay() + "','" + model.getTemp().getMin() + "','" + model.getTemp().getMax() + "','"
                + model.getTemp().getNight() + "','" + model.getTemp().getEve() + "','" + model.getTemp().getMorn() + "','"
                + model.getPressure() + "','" + model.getHumidity() + "')";
        executeDMLQuery(insertQuery);
        Log.d("insertQuery", insertQuery);
    }

 // Todo deleting data from database table
 public void deleteInfoToDB() {
     String deleteQuery = "delete from " + DBTable.TABLE_NAME ;
     executeDMLQuery(deleteQuery);
     Log.d("deleteQuery", deleteQuery);
 }
 // Todo getting Citi Info from DB
    public City getCityInfoFromDB(){
        String selectSql = "Select "+DBTable.KEY_CITY_NAME_COLUMN+","+DBTable.KEY_COUNTRY_NAME_COLUMN +","
                           +DBTable.KEY_POPULATION_COLUMN+" from "+DBTable.TABLE_NAME;
      City city=null;
      Cursor myCursor = null;
      try{
      myCursor = executeQuery(selectSql);

          if (myCursor.moveToFirst()) {

           city=new City();
           city.setName(myCursor.getString(0));
           city.setCountry(myCursor.getString(1));
           city.setPopulation(myCursor.getString(2));

          }

      }catch(Exception e){
      e.printStackTrace();
      }finally{
      if(myCursor!=null && !myCursor.isClosed()){
        myCursor.close();
      }
      }

      return city;
    }

 // Todo get Weather List from DB
    public List<WeatherList> getWeatherListFromDB(){
        String selectSql = "Select "+DBTable.KEY_WEATHER_NAME_COLUMN+","+DBTable.KEY_WEATHER_DESCRIPTION_COLUMN +","
                +DBTable.KEY_DAY_TEMP_COLUMN+","+DBTable.KEY_MIN_TEMP_COLUMN +","
                +DBTable.KEY_MAX_TEMP_COLUMN +","+DBTable.KEY_NIGHT_TEMP_COLUMN +","+DBTable.KEY_EVE_TEMP_COLUMN +","
                +DBTable.KEY_MOR_TEMP_COLUMN +","+DBTable.KEY_PRESSURE_COLUMN +","+DBTable.KEY_HUMIDITY_COLUMN
                +" from "+DBTable.TABLE_NAME;

        List<WeatherList> list=new ArrayList<>();
        WeatherList weatherList=null;
        Weather weather=null;
        Temp temp=null;
        Cursor myCursor = null;

        try{
            myCursor = executeQuery(selectSql);

            if (myCursor.moveToFirst()) {
               do{
                   weatherList=new WeatherList();
                   weather=new Weather();
                   temp=new Temp();

                   weather.setMain(myCursor.getString(0));
                   weather.setDescription(myCursor.getString(1));
                   weatherList.setWeather(weather);

                   temp.setDay(myCursor.getString(2));
                   temp.setMin(myCursor.getString(3));
                   temp.setMax(myCursor.getString(4));
                   temp.setNight(myCursor.getString(5));
                   temp.setEve(myCursor.getString(6));
                   temp.setMorn(myCursor.getString(7));
                   weatherList.setTemp(temp);

                   weatherList.setPressure(myCursor.getString(8));
                   weatherList.setHumidity(myCursor.getString(9));

                   list.add(weatherList);
               }while (myCursor.moveToNext());

            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(myCursor!=null && !myCursor.isClosed()){
                myCursor.close();
            }
        }

        return list;
    }
}
