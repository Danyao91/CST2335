package com.example.androidlabs;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MyDatabaseFiles";
    public static final int VERSION_NUM =1;
    public static final String TABLE_NAME = "Contacts";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE = "message";
    public static final String COL_SEND = "isSent";


    public MyDatabaseOpenHelper(Activity ctx){
        super(ctx, DATABASE_NAME,null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COL_MESSAGE + " TEXT, "+ COL_SEND + " TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.i("Database upgrade", "old version: " + oldVersion + "  newVersion" + newVersion);

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

}
