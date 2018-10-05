package com.backbase.weatherapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.support.BackBaseApplication;
import com.backbase.weatherapp.ui.cities.CityDetailActivity;
import com.backbase.weatherapp.ui.cities.CityDetailFragment;
import com.backbase.weatherapp.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCitiesRecyclerViewAdapter
        extends RecyclerView.Adapter<FavoriteCitiesRecyclerViewAdapter.FavoriteCityViewHolder> implements Filterable
{

    private HomeActivity mParentActivity;
    private List<FavoriteCity> mFavoriteCityList;
    private boolean mTwoPane;
    private FavoriteCityFilter favoriteCityFilter;
    private RecyclerView rv;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            FavoriteCity city = (FavoriteCity) view.getTag();
            if (mTwoPane)
            {
                Bundle arguments = new Bundle();
                arguments.putString(CityDetailFragment.ARG_ITEM_ID, city.getName());
                CityDetailFragment fragment = new CityDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else
            {
                Context context = view.getContext();
                Intent intent = new Intent(context, CityDetailActivity.class);
                intent.putExtra(CityDetailFragment.ARG_ITEM_ID, city.getName());
                context.startActivity(intent);
            }
        }
    };

    private final View.OnLongClickListener mOnLongPressed = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View view)
        {
            final FavoriteCity city = (FavoriteCity) view.getTag();

            AlertDialog.Builder showPlace = new AlertDialog.Builder(
                    mParentActivity);
            showPlace.setMessage("Remove from list?");
            showPlace.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    BackBaseApplication.getGlobalApplicationInstance()
                            .getDB()
                            .favorityCityDao()
                            .delete(city);
                    mFavoriteCityList.remove(city);
                    notifyDataSetChanged();
                    Snackbar.make(rv, "City Removed Successfully from Favorite List", Snackbar.LENGTH_LONG).show();
                }

            });
            showPlace.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            showPlace.show();
            return true;
        }
    };

    public FavoriteCitiesRecyclerViewAdapter(HomeActivity parent,
                                      List<FavoriteCity> favoriteCityList,
                                      boolean twoPane,RecyclerView rv)
    {
        this.mFavoriteCityList = favoriteCityList;
        this.mParentActivity = parent;
        this.mTwoPane = twoPane;
        this.rv = rv;
    }

    @Override
    public FavoriteCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new FavoriteCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteCityViewHolder holder, int position)
    {
        holder.mContentView.setText(mFavoriteCityList.get(position).getName());

        holder.itemView.setTag(mFavoriteCityList.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.itemView.setOnLongClickListener(mOnLongPressed);
    }

    @Override
    public int getItemCount()
    {
        return mFavoriteCityList.size();
    }

    @Override
    public Filter getFilter()
    {
        if(favoriteCityFilter == null)
        {
            favoriteCityFilter = new FavoriteCityFilter();
        }

        return favoriteCityFilter;
    }

    class FavoriteCityViewHolder extends RecyclerView.ViewHolder
    {
        final TextView mContentView;

        FavoriteCityViewHolder(View view)
        {
            super(view);
            mContentView = view.findViewById(R.id.lblCityName);
        }
    }

    private class FavoriteCityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<FavoriteCity> tempList = new ArrayList<>();

                // search content in friend list
                for (FavoriteCity city : mFavoriteCityList) {
                    if (city.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(city);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mFavoriteCityList.size();
                filterResults.values = mFavoriteCityList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFavoriteCityList = (ArrayList<FavoriteCity>) results.values;
            notifyDataSetChanged();
        }
    }
}
