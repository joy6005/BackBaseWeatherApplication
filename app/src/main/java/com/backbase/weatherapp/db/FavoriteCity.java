package com.backbase.weatherapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FavoriteCity
{
    /*{
        "id": 707860,
            "name": "Hurzuf",
            "country": "UA",
            "coord": {
        "lon": 34.283333,
                "lat": 44.549999
    }*/
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "city_id")
    private long cityId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "lat")
    private String lat;

    @ColumnInfo(name = "lon")
    private String lon;

    public FavoriteCity(long cityId, String name, String country, String lat, String lon)
    {
        this.cityId = cityId;
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public long getCityId()
    {
        return cityId;
    }

    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLon()
    {
        return lon;
    }

    public void setLon(String lon)
    {
        this.lon = lon;
    }
}
