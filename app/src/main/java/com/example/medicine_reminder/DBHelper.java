package com.example.medicine_reminder;

import android.content.ContentValues;
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
        super(context, DATABASE_NAME, null, 1);
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

    public int get_med_count(String get_name) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT med_count FROM " + TABLE_NAME + " WHERE name = '" + get_name + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        return Integer.valueOf(data.getString(0));
    }

    public Cursor decreaseCount(int get_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT med_count FROM " + TABLE_NAME + " WHERE name_id = " + get_id;
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        int count = Integer.valueOf(data.getString(0));
        count -= 1;

        ContentValues values = new ContentValues();
        values.put("med_count", count);

        //db.insert("med_table", null, values);
        db.update("med_table", values, "name_id = '" + get_id + "'", null);


        return data;
    }


    public Cursor getname(String getid) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT name FROM " + TABLE_NAME + " WHERE name_id = " + getid;
        Cursor data = db.rawQuery(query, null);

        return data;
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

    public void deletezerobag(int name_id) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE name_id = '" + name_id + "'";
        db.execSQL(query);
    }
}