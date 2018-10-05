package com.backbase.weatherapp.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class BackBaseUtils
{
    private static String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";
    private static String TEMP_UNITS = "metric";

    public static String getDateFromTimeStamp(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000L);
        String date = DateFormat.format("EEEE", cal).toString();
        date += "\n" + DateFormat.format("dd MMM yyyy", cal).toString();
        date += "\n" + DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public static String getImageUrl(String imageName)
    {
        return  String.format("http://openweathermap.org/img/w/%s.png", imageName);
    }

    public static String getCurrentCityUrl(String cityName)
    {
        return String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s",cityName, API_KEY,TEMP_UNITS);
    }

    public static String getFiveDayWeatherUrl(String cityName)
    {
        return String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=%s",cityName, API_KEY,TEMP_UNITS);
    }
}
