package com.example.master.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Master on 2017-03-02.
 */

public class DBHandler extends SQLiteOpenHelper
{
    public DBHandler(Context context)
    {
        super(context, ContentProviderTest.DB_NAME, null, ContentProviderTest.DB_VERSION);
    }

    // 데이터 베이스 생성
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createSql = "create table " + ContentProviderTest.TABLE_NAME + " (" + "id integer primary key autoincrement, " + "name text, " + "pnumber text)";
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
