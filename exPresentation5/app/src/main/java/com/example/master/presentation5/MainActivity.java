package com.example.master.presentation5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar progressBar;
    TextView textView;
    int value = 0;

    BackgroundTask task;

    Boolean oneshowflag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.monitor);

        Button startbutton = (Button) findViewById(R.id.start);
        startbutton.setOnClickListener(this);

        Button stopbutton = (Button) findViewById(R.id.stop);
        stopbutton.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textmonitor);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start:
                if(oneshowflag != false) break;
                oneshowflag = true;
                task = new BackgroundTask();
                task.execute(100);
                break;
            case R.id.stop:
                oneshowflag = false;
                task.cancel(true);
                break;
            default:
                break;
        }
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            value = 0;
            progressBar.setProgress(value);
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);

            value = 0;
            textView.setText(" 중지됨 ");

            oneshowflag = false;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0].intValue());
            textView.setText(" 진행중 = "  + values[0]);
        }

        @Override
        protected Integer doInBackground(Integer... params)
        {
            while (!isCancelled())
            {
                value++;

                if (value >= 100) break;
                else publishProgress(value);

                try
                {
                    Thread.sleep(50);
                }
                catch (Exception e)
                {

                }
            }

            return value;
        }
    }
}
