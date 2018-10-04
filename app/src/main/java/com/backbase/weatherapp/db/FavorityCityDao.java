package com.backbase.weatherapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavorityCityDao
{
    @Query("SELECT * FROM FavoriteCity")
    List<FavoriteCity> getAll();

    @Query("SELECT * FROM FavoriteCity WHERE name LIKE :name LIMIT 1")
    FavoriteCity findByName(String name);

    @Query("SELECT * FROM FavoriteCity WHERE city_id LIKE :cityId LIMIT 1")
    FavoriteCity findByCityID(long cityId);

    @Insert
    void insertAll(List<FavoriteCity> city);

    @Delete
    void delete(FavoriteCity city);

    @Query("DELETE FROM FavoriteCity")
    void deleteAll();
}
