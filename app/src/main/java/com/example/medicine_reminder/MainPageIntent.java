package com.example.medicine_reminder;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class MainPageIntent extends AppCompatActivity {

    Button eat, no_eat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_intent);
        setTitle("用藥資訊");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eat = findViewById(R.id.button);
        no_eat = findViewById(R.id.button2);



    }
}
