
package com.backbase.weatherapp.models.openweather.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherItemList {

    @SerializedName("city")
    @Expose
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
