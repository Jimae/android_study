package com.example.master.presentation4;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Master on 2017-04-22.
 */

public class MusicService extends Service
{
    private MediaPlayer mplayer;

    private WindowManager winMgr;
    private View view=null;

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder
    {
        MusicService getService()
        {
            // Return this instance of LocalService so clients can call public methods
            return MusicService.this;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i("Service","onCreate()");

        mplayer = new MediaPlayer();

        try
        {
            AssetFileDescriptor afd = getAssets().openFd("kwill.mp3");
            mplayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

            mplayer.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        displayButton();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.i("Service","onBind()");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent , int flags, int startId)
    {
        mplayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        Log.i("Service","onDestroy()");

        mplayer.stop();
        stopSelf();

        if (view != null)
        {
            winMgr.removeView(view);
            view=null;
        }
        super.onDestroy();
    }

    private void displayButton()
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (View) inflater.inflate(R.layout.service, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,       //터치 인식
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        ImageView cancel_b=(ImageView)view.findViewById(R.id.cancelButton);
        cancel_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mplayer.stop();
                stopSelf();
                if (view != null) {
                    winMgr.removeView(view);
                    view=null;

                }
            }
        });

        try {
            winMgr = (WindowManager) getSystemService(WINDOW_SERVICE);
            winMgr.addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
