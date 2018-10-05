package com.backbase.weatherapp.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backbase.weatherapp.adapters.FavoriteCitiesRecyclerViewAdapter;
import com.backbase.weatherapp.support.BackBaseApplication;
import com.backbase.weatherapp.R;
import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.ui.help.HelpActivity;
import com.backbase.weatherapp.ui.settings.SettingsActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    private boolean mTwoPane;
    private List<FavoriteCity>mFavoriteCityList;
    private FavoriteCitiesRecyclerViewAdapter mFavoriteCitiesRecyclerViewAdapter;
    private SearchView searchView;
    private MenuItem searchMenuItem;

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

            }
        });

        if (findViewById(R.id.item_detail_container) != null)
        {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
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
        List<FavoriteCity>mFavoriteCityList = BackBaseApplication.getGlobalApplicationInstance().getDB().favorityCityDao().getAll();
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

}
