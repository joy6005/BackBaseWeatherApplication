package com.backbase.weatherapp.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class BackBaseUtils
{
    public static String getDateFromTimeStamp(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
