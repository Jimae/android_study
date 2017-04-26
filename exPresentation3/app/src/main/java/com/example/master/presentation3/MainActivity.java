package com.example.master.presentation3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    BroadcastReceiver screenOnOffReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter screenOnOffFilter = new IntentFilter();
        screenOnOffFilter.addAction("android.intent.action.SCREEN_ON");
        screenOnOffFilter.addAction("android.intent.action.SCREEN_OFF");

        screenOnOffReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if (intent.getAction().equals("android.intent.action.SCREEN_ON"))
                {
                    Toast.makeText(context, "스크린 ON", Toast.LENGTH_SHORT).show();
                }
                else if (intent.getAction().equals("android.intent.action.SCREEN_OFF"))
                {
                    Toast.makeText(context, "스크린 OFF", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "등록되어 있지 않는 Action", Toast.LENGTH_SHORT).show();
                }
            }
        };

        registerReceiver(screenOnOffReceiver, screenOnOffFilter);
    }

    @Override
    public void onDestroy()
    {
        if(screenOnOffReceiver != null) unregisterReceiver(screenOnOffReceiver);

        super.onDestroy();
    }
}
