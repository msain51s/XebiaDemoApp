package com.xebiaappdemo.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2/19/2018.
 */

public class DBTable {
    //Todo Database creation SQL statement

    public static final String TABLE_NAME="mytable";
    public static final String KEY_CITY_NAME_COLUMN="city_name";
    public static final String KEY_COUNTRY_NAME_COLUMN="country_name";
    public static final String KEY_POPULATION_COLUMN="population";
    public static final String KEY_WEATHER_NAME_COLUMN="weather_name";
    public static final String KEY_WEATHER_DESCRIPTION_COLUMN="weather_description";
    public static final String KEY_DAY_TEMP_COLUMN="day_temp";
    public static final String KEY_MIN_TEMP_COLUMN="min_temp";
    public static final String KEY_MAX_TEMP_COLUMN="max_temp";
    public static final String KEY_NIGHT_TEMP_COLUMN="night_temp";
    public static final String KEY_EVE_TEMP_COLUMN="eve_temp";
    public static final String KEY_MOR_TEMP_COLUMN="mor_temp";
    public static final String KEY_PRESSURE_COLUMN="pressure";
    public static final String KEY_HUMIDITY_COLUMN="humidity";
    private static final String CREATE_WEATHER_TABLE = "create table "+TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_CITY_NAME_COLUMN+" VARCHAR(100),"
            +KEY_COUNTRY_NAME_COLUMN+" VARCHAR(100),"
            +KEY_POPULATION_COLUMN+" VARCHAR(100),"
            +KEY_WEATHER_NAME_COLUMN+" VARCHAR(100),"
            +KEY_WEATHER_DESCRIPTION_COLUMN+" VARCHAR(100),"
            +KEY_DAY_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_MIN_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_MAX_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_NIGHT_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_EVE_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_MOR_TEMP_COLUMN+" VARCHAR(100),"
            +KEY_PRESSURE_COLUMN+" VARCHAR(100),"
            +KEY_HUMIDITY_COLUMN+" VARCHAR(100))";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_WEATHER_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);
    }
}
