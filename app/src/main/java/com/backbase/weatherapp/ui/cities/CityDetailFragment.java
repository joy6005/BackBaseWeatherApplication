package com.backbase.weatherapp.ui.cities;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.utils.BackBaseUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.backbase.weatherapp.adapters.FiveDaysAdapter;
import com.backbase.weatherapp.models.openweather.WeatherItem;
import com.backbase.weatherapp.models.openweather.list.City;
import com.backbase.weatherapp.models.openweather.list.FiveDaysList;
import com.backbase.weatherapp.network.AsyncNetworkCall;
import com.backbase.weatherapp.network.NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityDetailFragment extends Fragment
{
    public static final String TAG = CityDetailFragment.class.getCanonicalName();
    public static final String ARG_CITY_NAME = "city_name";
    public static final String ARG_CITY_LAT = "city_lat";
    public static final String ARG_CITY_LNG = "city_lng";

    private FavoriteCity mFavoriteCity;
    private ImageView imageView;
    private TextView txtDetails;
    private TextView txtFiveDays;
    private RecyclerView rvFiveDayWeather;
    private FiveDaysAdapter mFiveDaysAdapter;
    private List<FiveDaysList> fiveDaysLists;
    private String cityName;
    private String cityLat;
    private String cityLng;

    public CityDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_CITY_NAME))
        {
            cityName =  getArguments().getString(ARG_CITY_NAME);
            cityLat =  getArguments().getString(ARG_CITY_LAT);
            cityLng =  getArguments().getString(ARG_CITY_LNG);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(cityName);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        rvFiveDayWeather = rootView.findViewById(R.id.rvFiveDayWeather);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvFiveDayWeather.setLayoutManager(mLinearLayoutManager);
        rvFiveDayWeather.setHasFixedSize(true);

        fiveDaysLists = new ArrayList<>();
        mFiveDaysAdapter = new FiveDaysAdapter(getActivity(), fiveDaysLists);
        rvFiveDayWeather.setAdapter(mFiveDaysAdapter);

        txtDetails  = rootView.findViewById(R.id.item_detail);
        txtFiveDays = rootView.findViewById(R.id.txtfiveDays);
        imageView = rootView.findViewById(R.id.imgWeatherStatus);

        loadCurrentCityWeatherInformations();
        loadFiveDaysCurrentCityWeatherInformations();

        return rootView;
    }

    public void loadFiveDaysCurrentCityWeatherInformations()
    {
        new AsyncNetworkCall(new NetworkResponse()
        {
            @Override
            public void onSuccess(String response)
            {
                fiveDaysLists.clear();
                Gson gson = new GsonBuilder().create();
                City cityList = gson.fromJson(response, City.class);

                fiveDaysLists.addAll(cityList.getList());
                mFiveDaysAdapter.notifyDataSetChanged();
                //mFiveDaysAdapter.clear();
                //mFiveDaysAdapter.addAll(fiveDaysLists);
            }

            @Override
            public void onError(String errorMessage)
            {

            }
        }).execute(BackBaseUtils.getFiveDayWeatherUrlByLatLng(cityLat, cityLng));
    }

    public void loadCurrentCityWeatherInformations()
    {
        if (cityName != null)
        {
            new AsyncNetworkCall(new NetworkResponse()
            {
                @Override
                public void onSuccess(String response)
                {
                    txtDetails.setText(response);
                    try
                    {
                        JSONObject mJsonObject = new JSONObject(response);
                        String imageName = mJsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        Glide.with(getActivity())
                                .load(BackBaseUtils.getImageUrl(imageName ))
                                .into(imageView);

                        Gson gson = new GsonBuilder().create();
                        WeatherItem currentCityWeather = gson.fromJson(response, WeatherItem.class);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage)
                {

                }
            }).execute(BackBaseUtils.getCurrentCityUrlByLatLng(cityLat,cityLng));
        }
    }
}

