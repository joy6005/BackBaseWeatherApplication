package com.backbase.weatherapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.backbase.weatherapp.utils.BackBaseUtils;
import com.bumptech.glide.Glide;
import com.backbase.weatherapp.R;
import com.backbase.weatherapp.models.openweather.Main;
import com.backbase.weatherapp.models.openweather.Rain;
import com.backbase.weatherapp.models.openweather.Weather;
import com.backbase.weatherapp.models.openweather.Wind;
import com.backbase.weatherapp.models.openweather.list.FiveDaysList;

import java.util.List;

public class FiveDaysAdapter extends RecyclerView.Adapter<FiveDaysAdapter.FiveDaysViewHolder>
{
    private List<FiveDaysList> fiveDaysLists;
    private Activity mActivity;

    public FiveDaysAdapter(Activity activity, List<FiveDaysList> dataModelList)
    {
        mActivity = activity;
        this.fiveDaysLists = dataModelList;
    }

    @Override
    public int getItemCount()
    {
        return fiveDaysLists.size();
    }

    // Add a list of items -- change to type used
    public void addAll(List<FiveDaysList> list) {

        fiveDaysLists.addAll(0,list);
        notifyDataSetChanged();
    }

    public void clear() {
        fiveDaysLists.clear();
        notifyDataSetChanged();
    }

    @Override
    public FiveDaysViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_five_days, viewGroup, false);

        return new FiveDaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FiveDaysViewHolder dataViewHolder, int i)
    {
        FiveDaysList mFiveDaysList = fiveDaysLists.get(i);
        Main mMain = fiveDaysLists.get(i).getMain();
        Wind mWind = fiveDaysLists.get(i).getWind();
        Rain mRain = fiveDaysLists.get(i).getRain();
        Weather mWeather = fiveDaysLists.get(i).getWeather().get(0);

        String date = BackBaseUtils.getDateFromTimeStamp(mFiveDaysList.getDt());
        dataViewHolder.txtTime.setText(date);
        //dataViewHolder.txtTime.setText(mFiveDaysList.getDtTxt());
        if(BackBaseUtils.TEMP_UNITS.equals("metric"))
        {
            dataViewHolder.txtTemperature.setText(String.valueOf(mMain.getTemp()) + " \u2103");
        }else
        {
            dataViewHolder.txtTemperature.setText(String.valueOf(mMain.getTemp()) + " \u2109");
        }
        dataViewHolder.txtHumidity.setText("Humi : " + String.valueOf(Math.round(mMain.getHumidity()))  + "%");

        if(mWind != null)
        {
            dataViewHolder.txtWind.setText("Wind : " + String.valueOf(Math.round(mWind.getSpeed())) + "m/s  " + BackBaseUtils.degreesToDirection(mWind.getDeg()));
        }

        if(mRain!=null)
        {
            dataViewHolder.txtRain.setText("Rain : " + String.valueOf(Math.round(mRain.get3h())) + "%");
        }else
        {
            dataViewHolder.txtRain.setText("No Rain Prediction");
        }


        Glide.with(mActivity).load("http://openweathermap.org/img/w/" + mWeather.getIcon() + ".png")
                .thumbnail(1)
                .into(dataViewHolder.imgWeatherStatus);
    }

    static public class FiveDaysViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTime;
        TextView txtTemperature;
        TextView txtHumidity;
        TextView txtWind;
        TextView txtRain;
        ImageView imgWeatherStatus;

        public FiveDaysViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtTemperature = itemView.findViewById(R.id.txtTemperature);
            txtHumidity = itemView.findViewById(R.id.txtHumidity);
            txtWind = itemView.findViewById(R.id.txtWind);
            txtRain = itemView.findViewById(R.id.txtRain);
            imgWeatherStatus = itemView.findViewById(R.id.imgWeatherStatus);
        }
    }
}
