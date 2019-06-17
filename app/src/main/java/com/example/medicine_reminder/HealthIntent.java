package com.example.medicine_reminder;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class HealthIntent extends AppCompatActivity {


    private long list_id;
    private EditText mSys, mDia, mPul, mSugar, mWeight;
    private FloatingActionButton fab_save;
    private SQLiteDAO sqLiteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_intent);
        Bundle bundle = getIntent().getExtras();
        list_id = bundle.getLong("ID");

        mSys = findViewById(R.id.set_sys);
        mDia = findViewById(R.id.set_dia);
        mPul = findViewById(R.id.set_pul);
        mSugar = findViewById(R.id.set_sugar);
        mWeight = findViewById(R.id.set_weight);
        fab_save = findViewById(R.id.health_save);
        sqLiteDAO = new SQLiteDAO(HealthIntent.this);

        if(list_id >0){
            setTitle("編輯數值");
            Item item_read = sqLiteDAO.get(list_id);
            mSys.setText(item_read.getSys()+"");
            mDia.setText(item_read.getDia()+"");
            mPul.setText(item_read.getPUL()+"");
            mSugar.setText(item_read.getBloodsugar()+"");
            mWeight.setText(item_read.getWeight()+"");

        }else {
            setTitle("新增數值");
        }

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSys.length()==0 && mDia.length()==0 && mPul.length()==0 && mSugar.length()==0
                && mWeight.length()==0){
                    Toast.makeText(HealthIntent.this, "至少需輸入一項", Toast.LENGTH_SHORT).show();
                }else {
                    mSys.setText((mSys.length()==0)?"0":mSys.getText());
                    mDia.setText((mDia.length()==0)?"0":mDia.getText());
                    mPul.setText((mPul.length()==0)?"0":mPul.getText());
                    mSugar.setText((mSugar.length()==0)?"0":mSugar.getText());
                    mWeight.setText((mWeight.length()==0)?"0":mWeight.getText());

                    int sys = Integer.parseInt(mSys.getText().toString());
                    int dia = Integer.parseInt(mDia.getText().toString());
                    int pul = Integer.parseInt(mPul.getText().toString());
                    int sugar = Integer.parseInt(mSugar.getText().toString());
                    int weight = Integer.parseInt(mWeight.getText().toString());

                    if(list_id>0){
                        Item item =new Item(list_id, sys, dia, pul, sugar, weight, new Date().getTime());
                        sqLiteDAO.update(item);
                    }else {
                        Item item =new Item(0, sys, dia, pul, sugar, weight, new Date().getTime());
                        sqLiteDAO.insert(item);
                    }
                    finish();

                }
            }
        });
    }
}
