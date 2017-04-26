package com.example.master.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Master on 2017-04-23.
 */

public class MyCalcService extends Service
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mCalc;
    }

    MyCalc.Stub mCalc = new MyCalc.Stub()
    {
        @Override
        public int Add(int a, int b) throws RemoteException {
            Log.d("maluchi", "Add was called!!");
            return a+b;
        }

        @Override
        public int Mul(int a, int b) throws RemoteException {
            Log.d("maluchi", "Mul was called!!");
            return a*b;
        }

        @Override
        public int Sub(int a, int b) throws RemoteException {
            Log.d("maluchi", "Sub was called!!");
            return a-b;
        }

        @Override
        public int Div(int a, int b) throws RemoteException {
            Log.d("maluchi", "Div was called!!");
            return a/b;
        }
    };

}
