package com.backbase.weatherapp;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.backbase.weatherapp.adapters.FiveDaysAdapter;
import com.backbase.weatherapp.models.DummyContent;
import com.backbase.weatherapp.models.openweather.WeatherItem;
import com.backbase.weatherapp.models.openweather.list.City;
import com.backbase.weatherapp.models.openweather.list.FiveDaysList;
import com.backbase.weatherapp.network.AsyncNetworkCall;
import com.backbase.weatherapp.network.NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String TAG = ItemDetailFragment.class.getCanonicalName();

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private ImageView imageView;
    private TextView txtDetails;
    private TextView txtFiveDays;
    private RecyclerView rvFiveDayWeather;
    private FiveDaysAdapter mFiveDaysAdapter;
    private List<FiveDaysList> mDataModelList;

    public ItemDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.content);
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

        mDataModelList = new ArrayList<>();
        mFiveDaysAdapter = new FiveDaysAdapter(getActivity(),mDataModelList);
        rvFiveDayWeather.setAdapter(mFiveDaysAdapter);

        txtDetails  = rootView.findViewById(R.id.item_detail);
        txtFiveDays = rootView.findViewById(R.id.txtfiveDays);

        imageView = rootView.findViewById(R.id.imgWeatherStatus);
        // Show the dummy content as text in a TextView.
        if (mItem != null)
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
                                .load("http://openweathermap.org/img/w/" + imageName + ".png")
                                .into(imageView);

                        Gson gson = new GsonBuilder().create();
                        WeatherItem dm = gson.fromJson(response, WeatherItem.class);
                        Log.d(TAG,dm.getName());
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onError(String errorMessage)
                {

                }
            }).execute("http://api.openweathermap.org/data/2.5/weather?q="+mItem.content + "&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric");

        }

        new AsyncNetworkCall(new NetworkResponse()
        {
            @Override
            public void onSuccess(String response)
            {
                //txtFiveDays.setText(response);
                Gson gson = new GsonBuilder().create();
                City dm = gson.fromJson(response, City.class);

                mFiveDaysAdapter.addAll(dm.getList());
                //Log.d(TAG,dm.getName());
            }

            @Override
            public void onError(String errorMessage)
            {

            }
        }).execute("http://api.openweathermap.org/data/2.5/forecast?q=" + mItem.content + "&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric");

        return rootView;
    }
}

//https://github.com/bumptech/glide
