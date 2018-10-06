package com.backbase.weatherapp.ui.cities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.ui.home.HomeActivity;

public class CityDetailActivity extends AppCompatActivity
{

    private CityDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(fragment != null)
                {
                    fragment.loadCurrentCityWeatherInformations();
                    fragment.loadFiveDaysCurrentCityWeatherInformations();
                }
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CityDetailFragment.ARG_CITY_NAME,
                    getIntent().getStringExtra(CityDetailFragment.ARG_CITY_NAME));
            arguments.putString(CityDetailFragment.ARG_CITY_LAT,getIntent().getStringExtra(CityDetailFragment.ARG_CITY_LAT));
            arguments.putString(CityDetailFragment.ARG_CITY_LNG,getIntent().getStringExtra(CityDetailFragment.ARG_CITY_LNG));

            fragment = new CityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            navigateUpTo(new Intent(this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
