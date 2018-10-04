package com.backbase.weatherapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {FavoriteCity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase
{
    public abstract FavorityCityDao favorityCityDao();
}
