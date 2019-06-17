package com.example.medicine_reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class modify_med extends AppCompatActivity {
    EditText edt_name, edt_cout;
    Button btn;
    ListView list;
    Calendar calendar;

    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;

    int get_selected_name_id;
    String get_selected_name;
    String get_selected_count;
    String get_time;
    String time = "";
    int get_got_it, go_insert_or_update;
    int get_max_time;
    String medname, medcount;
    int name_id;
    int time_id;
    String fixtimehour = "", fixtimemin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_modify_med);
        setTitle("修改藥袋");

        mDBHelper = new DBHelper(this);
        timeDBHelper = new TimeDBHelper(this);

        findViews();
        getSelected();
        showList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(getApplicationContext(),"yeeeee",Toast.LENGTH_SHORT).show();
            medcount = edt_cout.getText().toString();
            name_id = mDBHelper.get_name_id(edt_name.getText().toString());
            System.out.println("medcount = " +  medcount);
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("med_count", medcount);
            db.update("med_table", values, "name_id = '" + name_id + "'", null);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }

    private void findViews() {
        edt_name = findViewById(R.id.mod_set_med);
        edt_cout = findViewById(R.id.mod_set_count);
        btn = findViewById(R.id.mod_med_btn);
        list = findViewById(R.id.mod_time_set);

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(modify_med.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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
                        String get_name = edt_name.getText().toString();
                        System.out.println("get_name = " + get_name);
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
            Toast.makeText(modify_med.this, "full time", Toast.LENGTH_SHORT).show();
        }
        else if (get_got_it == 1) {
            go_insert_or_update = 1;                 //已經有這筆資料
            Toast.makeText(modify_med.this, "已經有這筆資料了", Toast.LENGTH_SHORT).show();
            setData();
        }
        else {
            setData();
            go_insert_or_update = 0;
            Toast.makeText(modify_med.this, "setData", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        medname = edt_name.getText().toString();
        medcount = edt_cout.getText().toString();

        addData(medname, medcount, time);
    }

    private void addData(String med_name, String med_count, String datetime) {
        mDBHelper = new DBHelper(modify_med.this);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        SQLiteDatabase db_time = timeDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values_time = new ContentValues();

        if (go_insert_or_update == 1) {
            Toast.makeText(modify_med.this, "Have it", Toast.LENGTH_SHORT).show();
            name_id = mDBHelper.get_name_id(edt_name.getText().toString());
            //time_id = timeDBHelper.get_time_id(Integer.toString(name_id));
            values_time.put("name_id", name_id);
            values_time.put("datetime", datetime);
            values_time.put("eat", 0);
            values_time.put("dead", 0);
            //db.update("time_table", values_time, "id_time = '" + time_id + "'", null);
            db_time.insert("time_table", null, values_time);
            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
            values.put("med_count", med_count);
            db.update("med_table", values, "name_id = '" + name_id + "'", null);

        }else if (go_insert_or_update == 0) {
            //values.put("name", med_name);
            values.put("med_count", med_count);
            db.insert("med_table", null, values);

            name_id = mDBHelper.get_name_id(edt_name.getText().toString());
            values_time.put("name_id", name_id);
            values_time.put("datetime", datetime);
            values_time.put("eat", 0);
            values_time.put("dead", 0);
            db_time.insert("time_table", null, values_time);
        }

        showList();

    }

    private void getSelected() {
        Intent intent = getIntent();

        get_selected_name_id = intent.getIntExtra("selected_name_id", 0);
        get_selected_name = intent.getStringExtra("selected_name");
        get_selected_count = intent.getStringExtra("selected_count");

        edt_name.setText(get_selected_name);
        edt_cout.setText(get_selected_count);
    }

    private void showList() {
        Cursor data = timeDBHelper.getData(get_selected_name_id);
        ArrayList<String> listData = new ArrayList<>();
        data.moveToFirst();
        while(!data.isAfterLast()){
            listData.add(data.getString(0));
            System.out.println(data.getString(0));
            data.moveToNext();
        }

        final ListAdapter adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);

        list.setAdapter(adapter);

        Toast.makeText(this, "左右滑動可移除時間", Toast.LENGTH_SHORT).show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                get_time = parent.getItemAtPosition(position).toString();
                openOptionDialog(parent.getItemAtPosition(position).toString());
            }
        });
    }

    private void openOptionDialog(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("確定要刪除嗎？");
        dialog.setMessage("確定要刪除時間為 " + message + " 的提醒嗎？");

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timeDBHelper.deleteTime(get_time, get_selected_name_id);
                onResume();
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }




}
