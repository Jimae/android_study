package com.example.master.presentation4;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    final int DRAWPERMISSION=0;
    MusicService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button startbutton = (Button) findViewById(R.id.MStart);
        startbutton.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        Intent startintent = new Intent(getApplicationContext(), MusicService.class);
        startService(startintent);
        bindService(startintent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart()
    {super.onStart();
        // Bind to LocalService
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(this))
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, DRAWPERMISSION);
                return;
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        unbindService(mConnection);

    }

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className,IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent imageData)
    {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode)
        {
            case DRAWPERMISSION:
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (!Settings.canDrawOverlays(this))
                    {
                        // SYSTEM_ALERT_WINDOW permission not granted...
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(this, MusicService.class);
                        startService(intent);
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    }
                }
            }
            break;
        }
    }
}
