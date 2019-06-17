package com.example.medicine_reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class MedBag extends AppCompatActivity {
    ListView listView;
    EditText med_name_edt, med_count_edt;
    Button btn;
    Calendar calendar;

    String time = "";
    String fixtimehour = "", fixtimemin = "";
    String medname, medcount;
    static final String[] FROM = new String[] {"name", "med_count"};
    int get_got_it, go_insert_or_update;
    int name_id;
    int get_max_time;

    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_med_bag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("我的藥袋");

        mDBHelper = new DBHelper(this);
        timeDBHelper = new TimeDBHelper(this);

        findViews();

    }


    private void findViews() {
        listView = findViewById(R.id.time_set);
        med_name_edt = findViewById(R.id.set_med);
        med_count_edt = findViewById(R.id.set_count);
        btn = findViewById(R.id.med_btn);

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(MedBag.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        if (hourOfDay < 10) {
                            String gettime = Integer.toString(hourOfDay);
                            String fix = "0";
                            fixtimehour = fix + gettime;
                        }else {
                            fixtimehour = Integer.toString(hourOfDay);
                        }

                        if (minute < 10) {
                            String gettime = Integer.toString(minute);
                            String fix = "0";
                            fixtimemin = fix + gettime;
                        } else {
                            fixtimemin = Integer.toString(minute);
                        }

                        time = fixtimehour + " : " + fixtimemin;
                        String get_name = med_name_edt.getText().toString();
                        Cursor cursor = mDBHelper.checkName(get_name);
                        get_got_it = mDBHelper.getGot_it();
                        get_max_time = timeDBHelper.getMax_time();
                        checkGotit();
                    }
                }, hour, minute, false).show();
            }
        });
    }

    private void checkGotit() {
        if (get_max_time == 1){
            go_insert_or_update = 1;                 //已經有four data
            Toast.makeText(MedBag.this, "full time", Toast.LENGTH_SHORT).show();
        }
        else if (get_got_it == 1) {
            go_insert_or_update = 1;                 //已經有這筆資料
            Toast.makeText(MedBag.this, "已經有這筆資料了", Toast.LENGTH_SHORT).show();
            setData();
        }
        else {
            setData();
            go_insert_or_update = 0;
            Toast.makeText(MedBag.this, "setData", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        medname = med_name_edt.getText().toString();
        medcount = med_count_edt.getText().toString();

        addData(medname, medcount, time);
        setNotification(time);
    }

    private void addData(String med_name, String med_count, String datetime) {
        mDBHelper = new DBHelper(MedBag.this);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        SQLiteDatabase db_time = timeDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values_time = new ContentValues();

        if (go_insert_or_update == 1) {
            Toast.makeText(MedBag.this, "Have it", Toast.LENGTH_SHORT).show();
            name_id = mDBHelper.get_name_id(med_name_edt.getText().toString());
            values_time.put("name_id", name_id);
            values_time.put("datetime", datetime);
            values_time.put("eat", 0);
            db_time.insert("time_table", null, values_time);

        }else if (go_insert_or_update == 0) {
            values.put("name", med_name);
            values.put("med_count", med_count);
            db.insert("med_table", null, values);

            name_id = mDBHelper.get_name_id(med_name_edt.getText().toString());
            values_time.put("name_id", name_id);
            values_time.put("datetime", datetime);
            values_time.put("eat", 0);
            db_time.insert("time_table", null, values_time);
        }

        showList();

    }

    private void showList() {
        Cursor data = timeDBHelper.getData(name_id);
        ArrayList<String> listData = new ArrayList<>();
        data.moveToFirst();
        while(!data.isAfterLast()){
                listData.add(data.getString(0));
                System.out.println(data.getString(0));
            data.moveToNext();
        }

        ListAdapter adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);

        listView.setAdapter(adapter);
    }

    private void setNotification(String gettime) {
//        String[] settime = gettime.split(" : ");
//
//        String hour = settime[0];
//        String min = settime[1];

        //Calendar calendar = Calendar.getInstance();

//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(min));

//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        Intent intent = new Intent(this, Notification_reciever.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

}
