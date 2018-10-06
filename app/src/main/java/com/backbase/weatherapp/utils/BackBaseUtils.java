package com.backbase.weatherapp.utils;

import android.text.format.DateFormat;
import android.util.Log;

import com.backbase.weatherapp.support.BackBaseApplication;

import java.util.Calendar;
import java.util.Locale;

public class BackBaseUtils
{
    private static String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";
    public static String TEMP_UNITS = "metric";

    public static String getDateFromTimeStamp(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000L);
        String date = DateFormat.format("EEEE", cal).toString();
        date += "\n" + DateFormat.format("dd MMM yyyy", cal).toString();
        date += "\n" + DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public static String degreesToDirection(double degrees)
    {
        degrees *= 10;

        String[] caridnals = { "N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW", "N" };
        return caridnals[(int)Math.round(((double)degrees % 3600) / 225)];
    }

    public static String getStringFromMilli(long millis) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        Log.d("HOURS", String.valueOf(hours));

        if(hours >= 1 && hours <= 12){
            return "\uD83C\uDF05 Morning";
        }else if(hours >= 12 && hours <= 16){
            return "Afternoon";
        }else if(hours >= 16 && hours <= 21){
            return "\uD83C\uDF07 Evening";
        }else if(hours >= 21 && hours <= 24){
            return "\uD83C\uDF03 Night";
        }
        return null;
    }

    public static String getImageUrl(String imageName)
    {
        return  String.format("http://openweathermap.org/img/w/%s.png", imageName);
    }

    public static String getCurrentCityUrl(String cityName)
    {
        TEMP_UNITS = BackBaseApplication.getGlobalApplicationInstance().getDefaultUnit();
        return String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s",cityName, API_KEY,TEMP_UNITS);
    }

    public static String getCurrentCityUrlByLatLng(String lat, String lng)
    {
        TEMP_UNITS = BackBaseApplication.getGlobalApplicationInstance().getDefaultUnit();
        return String.format("http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=%s",lat, lng, API_KEY,TEMP_UNITS);
    }

    public static String getFiveDayWeatherUrl(String cityName)
    {
        TEMP_UNITS = BackBaseApplication.getGlobalApplicationInstance().getDefaultUnit();
        return String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=%s",cityName, API_KEY,TEMP_UNITS);
    }

    public static String getFiveDayWeatherUrlByLatLng(String lat, String lng)
    {
        TEMP_UNITS = BackBaseApplication.getGlobalApplicationInstance().getDefaultUnit();
        return String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s&units=%s",lat,lng, API_KEY,TEMP_UNITS);
    }
}
