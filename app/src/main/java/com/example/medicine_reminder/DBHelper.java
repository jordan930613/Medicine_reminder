package com.example.medicine_reminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    int Got_it = 0;

    private static final String DATABASE_NAME = "mydata.db";

    public static final String TABLE_NAME = "med_table";

    // 建構子，在一般的應用都不需要修改
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                                "(name_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "name VARCHAR(32), " +
                                "med_count VARCHAR(32))";

        db.execSQL(CREATE_TABLE);

        Log.i("sqlite ", "create table ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }


    //Return all the data from database
//    public Cursor getData(String get_name) {
//        SQLiteDatabase db  = this.getWritableDatabase();
//        String query = "SELECT datetime_1, datetime_2, datetime_3, datetime_4 FROM " + TABLE_NAME + " WHERE name = '" + get_name + "'";
//        Cursor data = db.rawQuery(query, null);
//
//        return data;
//    }

    public Cursor getData() {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor checkName(String get_name) {
        Got_it = 0;
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT name FROM " + TABLE_NAME + " WHERE name = '" + get_name + "'";
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() > 0) {
            Got_it = 1;
        }

        return data;
    }

    public int getGot_it() {

        return Got_it;
    }

    public int get_name_id(String get_name) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT name_id FROM " + TABLE_NAME + " WHERE name = '" + get_name + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        return Integer.valueOf(data.getString(0));
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(name_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(32), " +
                "med_count VARCHAR(32))";

        db.execSQL(CREATE_TABLE);
        super.onOpen(db);
    }
}