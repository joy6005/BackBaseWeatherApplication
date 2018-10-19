package com.backbase.weatherapp.ui.help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.backbase.weatherapp.R;
import com.backbase.weatherapp.ui.home.HomeActivity;

public class SplashActivity extends AppCompatActivity
{

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    public static void start(Context context)
    {
        Intent mIntent = new Intent(context, SplashActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
