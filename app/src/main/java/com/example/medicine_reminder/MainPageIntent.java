package com.example.medicine_reminder;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainPageIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_intent);
        setTitle("用藥資訊");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
