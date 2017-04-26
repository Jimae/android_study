package com.example.master.testaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.master.testaidl.MyCalc;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText abuffer, bbuffer;

    MyCalc mCalc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abuffer = (EditText) findViewById(R.id.adata);
        bbuffer = (EditText) findViewById(R.id.bdata);

        Button addbutton = (Button) findViewById(R.id.add);
        addbutton.setOnClickListener(this);

        Button subbutton = (Button) findViewById(R.id.sub);
        subbutton.setOnClickListener(this);

        Button mulbutton = (Button) findViewById(R.id.mul);
        mulbutton.setOnClickListener(this);

        Button divbutton = (Button) findViewById(R.id.div);
        divbutton.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Intent intent = new Intent();
        ComponentName name = new ComponentName("com.example.master.testaidl", "com.example.master.testaidl.MyCalcService");
        intent.setComponent(name);
        bindService(intent, mSrvConn, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.unbindService(mSrvConn);
    }

    public void onClick(View v)
    {
        if (mCalc == null) return;

        int ad = 0, bd = 0;

        ad = Integer.parseInt(abuffer.getText().toString().trim());
        bd = Integer.parseInt(bbuffer.getText().toString().trim());

        try
        {
            switch (v.getId())
            {
                case R.id.add:
                    Log.d("변수값", "" + mCalc.Add(ad, bd));
                    Toast.makeText(this, "계산값 = " + mCalc.Add(ad, bd), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.sub:
                    Log.d("변수값", "" + mCalc.Sub(ad, bd));
                    Toast.makeText(this, "계산값 = " + mCalc.Sub(ad, bd), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mul:
                    Log.d("변수값", "" + mCalc.Mul(ad, bd));
                    Toast.makeText(this, "계산값 = " + mCalc.Mul(ad, bd), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.div:
                    Log.d("변수값", "" + mCalc.Div(ad, bd));
                    Toast.makeText(this, "계산값 = " + mCalc.Div(ad, bd), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        catch (RemoteException e)
        {
            Log.d("maluchi","RemoteException : "+ e.getMessage());
        }
        catch (Exception e)
        {
            Log.d("maluchi","Exception : "+e.getMessage());
        }
    }

    ServiceConnection mSrvConn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mCalc = MyCalc.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mCalc = null;
        }
    };
}
