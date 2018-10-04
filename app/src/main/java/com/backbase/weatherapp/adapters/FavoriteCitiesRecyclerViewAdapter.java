package com.backbase.weatherapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.db.FavoriteCity;
import com.backbase.weatherapp.ui.cities.CityDetailActivity;
import com.backbase.weatherapp.ui.cities.CityDetailFragment;
import com.backbase.weatherapp.ui.home.HomeActivity;

import java.util.List;

public class FavoriteCitiesRecyclerViewAdapter
        extends RecyclerView.Adapter<FavoriteCitiesRecyclerViewAdapter.FavoriteCityViewHolder>
{

    private HomeActivity mParentActivity;
    private List<FavoriteCity> mFavoriteCityList;
    private boolean mTwoPane;

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

    public FavoriteCitiesRecyclerViewAdapter(HomeActivity parent,
                                      List<FavoriteCity> favoriteCityList,
                                      boolean twoPane)
    {
        this.mFavoriteCityList = favoriteCityList;
        this.mParentActivity = parent;
        this.mTwoPane = twoPane;
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
    }

    @Override
    public int getItemCount()
    {
        return mFavoriteCityList.size();
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
}
