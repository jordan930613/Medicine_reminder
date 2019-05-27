package com.example.medicine_reminder;

import android.app.TimePickerDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MedBag extends AppCompatActivity {
    ListView listView;
    TimePickerDialog.OnTimeSetListener picker;
    TimePicker timePickerChange;
    Calendar calendar;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_med_bag);

        setTitle("我的藥袋");

        listView = findViewById(R.id.time_set);

        final ArrayList<String> timeSet = new ArrayList<String>();
        timeSet.add("新增時間");

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeSet);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        picker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeSet.add(count + ")  " + hourOfDay + " : " + minute);
                listView.setAdapter(adapter);
            }
        };

        calendar = Calendar.getInstance();

    }

    private AdapterView.OnItemClickListener onItemClickListener = new
            AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0){
                        TimePickerDialog dialog = new TimePickerDialog(MedBag.this, picker,
                                calendar.get(Calendar.HOUR),
                                calendar.get(Calendar.MINUTE),
                                false);
                        dialog.show();

                        count++;
                    }
                }
            };


}
