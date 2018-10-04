package com.backbase.weatherapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.db.WeatherDatabase;

import java.util.ArrayList;
import java.util.List;

public class BackBaseApplication extends Application
{
    private static BackBaseApplication appInstance;
    private static final String DATABASE_NAME = "BackBaseDatabase";
    private WeatherDatabase database;
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";


    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        // create database
        database = Room.databaseBuilder(getApplicationContext(), WeatherDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        loadDefaultFavoriteCities();
    }


    public static BackBaseApplication getGlobalApplicationInstance() {
        return appInstance;
    }

    public WeatherDatabase getDB() {
        return database;
    }

    public boolean isForceUpdate() {
        return getSP().getBoolean(KEY_FORCE_UPDATE, true);
    }

    public void setForceUpdate(boolean force) {
        SharedPreferences.Editor edit = getSP().edit();
        edit.putBoolean(KEY_FORCE_UPDATE, force);
        edit.apply();
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }

    public void loadDefaultFavoriteCities()
    {

        List<FavoriteCity>mFavoriteCityList = database.favorityCityDao().getAll();
        if((mFavoriteCityList == null || mFavoriteCityList.isEmpty()))
        {
            populateFavoriteCities();
        }
    }

    private void populateFavoriteCities()
    {
        List<FavoriteCity> list = new ArrayList<>();
        list.add(new FavoriteCity(6167865,"Toronto","CA","43.700111","-79.416298"));
        list.add(new FavoriteCity(6183235,"Winnipeg","CA","49.884399","-97.147041"));
        list.add(new FavoriteCity(6173331,"Vancouver","CA","49.24966","-123.119339"));
        list.add(new FavoriteCity(5128638,"New York","US","43.000351","-75.499901"));
        list.add(new FavoriteCity(1275339,"Mumbai","IN","19.01441","72.847939"));
        list.add(new FavoriteCity(2643743,"London","GB","51.50853","-0.12574"));

          // insert product list into database
        database.favorityCityDao().insertAll(list);
    }
}
