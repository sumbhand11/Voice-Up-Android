package com.voiceup.sumantbhandari.social;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.provider.Settings;

/**
 * Created by sumantbhandari on 12/17/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "Recording.db";
    public static final String TABLE_NAME= "user_recording";
    public static final String COL_1= "ID";
    public static final String COL_2= "NAME";
    public static final String COL_3= "SURNAME";
    public static final String COL_4= "RECORDING";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, RECORDING TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String recording){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COL_2,name);
        contentValue.put(COL_3,surname);
        contentValue.put(COL_4,recording);
        Long result =  db.insert(TABLE_NAME, null, contentValue);
        System.out.println(result);
        if (result == -1)
                return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
