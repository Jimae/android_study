package com.example.master.clientcp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final String CP_AUTHORITY = "com.test.contentprovider";
    private final Uri CONTENT_URI = Uri.parse("content://" + CP_AUTHORITY + "/contactable");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button regbtn = (Button) this.findViewById(R.id.registerbutton);
        regbtn.setOnClickListener(this);
        Button selbtn = (Button) this.findViewById(R.id.selectbutton);
        selbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.registerbutton:
                insertContact();
                break;
            case R.id.selectbutton:
                selectContact();
                break;
            default:
                break;
        }
    }

    private void insertContact()
    {
        EditText name = (EditText) this.findViewById(R.id.name);
        EditText pnumber = (EditText) this.findViewById(R.id.pnumber);

        ContentValues values = new ContentValues();
        values.put("name", name.getText().toString());
        values.put("pnumber", pnumber.getText().toString());
        Uri uri = getContentResolver().insert(CONTENT_URI, values);
    }

    private void selectContact()
    {
        String seldata = "";
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {

                seldata = seldata + cursor.getInt(cursor.getColumnIndex("id")) + "," + cursor.getString(cursor.getColumnIndex("name")) + "," + cursor.getString(cursor.getColumnIndex("pnumber")) + "\n\n";

                }while (cursor.moveToNext());
        }

        TextView showtxt = (TextView) this.findViewById(R.id.showtxt);
        showtxt.setText(seldata);
    }
}
