package com.backbase.weatherapp.ui.help;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.backbase.weatherapp.R;

public class HelpActivity extends AppCompatActivity
{

    public static void start(Context context)
    {
        Intent mIntent = new Intent(context, HelpActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView mWebViewInstruction = (WebView)findViewById(R.id.webViewInstruction);
        mWebViewInstruction.loadUrl("file:///android_asset/instruction.html");
    }
}
