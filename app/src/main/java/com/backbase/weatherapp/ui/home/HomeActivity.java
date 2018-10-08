package com.backbase.weatherapp.ui.home;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.adapters.FavoriteCitiesRecyclerViewAdapter;
import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.support.BackBaseApplication;
import com.backbase.weatherapp.ui.help.HelpActivity;
import com.backbase.weatherapp.ui.settings.SettingsActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    private boolean mTwoPane;
    private List<FavoriteCity>mFavoriteCityList;
    private FavoriteCitiesRecyclerViewAdapter mFavoriteCitiesRecyclerViewAdapter;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private int REQUEST_PLACE_PICKER = 101;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onPickButtonClick();
            }
        });

        if (findViewById(R.id.item_detail_container) != null)
        {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_help:
                HelpActivity.start(this);
                return true;

            case R.id.action_settings:
                SettingsActivity.start(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        BackBaseApplication.getGlobalApplicationInstance().loadDefaultFavoriteCities();
        mFavoriteCityList = BackBaseApplication.getGlobalApplicationInstance().getDB().favorityCityDao().getAll();
        Log.d("SIZE", "COUNT : " + mFavoriteCityList.size());
        mFavoriteCitiesRecyclerViewAdapter = new FavoriteCitiesRecyclerViewAdapter(this, mFavoriteCityList, mTwoPane, recyclerView);
        recyclerView.setAdapter(mFavoriteCitiesRecyclerViewAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mFavoriteCitiesRecyclerViewAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //mFavoriteCitiesRecyclerViewAdapter.getFilter().filter(newText);

        return true;
    }

    public void onPickButtonClick() {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            Log.d("PLACE", place.toString());

            final String name = place.getName().toString();
            double lat = place.getLatLng().latitude;
            double lng = place.getLatLng().longitude;

            FavoriteCity newFavoriteCity = new FavoriteCity();
            newFavoriteCity.setDefault(false);
            newFavoriteCity.setName(name);
            newFavoriteCity.setLat(String.valueOf(lat));
            newFavoriteCity.setLon(String.valueOf(lng));

            BackBaseApplication.getGlobalApplicationInstance()
                    .getDB()
                    .favorityCityDao()
                    .insert(newFavoriteCity);
                mFavoriteCityList.add(newFavoriteCity);
                mFavoriteCitiesRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
