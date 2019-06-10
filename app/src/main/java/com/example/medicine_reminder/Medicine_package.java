package com.example.medicine_reminder;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Medicine_package extends Fragment {
    private View v;
    private FloatingActionButton mainPage_btn;
    private ListView med_list_list;

    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;


    public Medicine_package() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        showListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_medicine_package, container, false);

        mainPage_btn = (FloatingActionButton) v.findViewById(R.id.mainPage_add);
        mainPage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedBag.class);
                startActivity(intent);
            }
        });

        mDBHelper = new DBHelper(getActivity());
        timeDBHelper = new TimeDBHelper(getActivity());

        med_list_list = (ListView) v.findViewById(R.id.med_list);
        med_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.med_name);
                int get_name_id = mDBHelper.get_name_id(textView.getText().toString());
                Intent intent = new Intent(getActivity(), modify_med.class);
                Toast.makeText(getActivity(), "intent", Toast.LENGTH_SHORT).show();

                intent.putExtra("selected_name_id", get_name_id);
                intent.putExtra("selected_name", textView.getText().toString());

                startActivity(intent);
//                Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select name from med_table where name = " + get_name_id + ";", null);
//                cursor.moveToFirst();
//
//                ArrayList<Cursor>myList = new ArrayList<>();
//                myList.add(cursor);
//
//                Cursor cursor_time = timeDBHelper.getReadableDatabase().rawQuery("select datetime from time_table where name = " + get_name_id + ";", null);
//                cursor_time.moveToFirst();
//                myList.add(cursor_time);
//                intent.putExtra("cursor",myList);
            }
        });

        showListView();

        return v;
    }


    private void showListView() {
        //get the data and append to a list
        Cursor data = mDBHelper.getData();

//        ArrayList<String> listData = new ArrayList<>();
//        while(data.moveToNext()){
//            listData.add(data.getString(1));
//        }

        ArrayList<Map<String, String>> listData = new ArrayList<>();
        data.moveToFirst();
        while (!data.isAfterLast()) {
            Map<String, String> map = new HashMap<>();
            map.put("med_name", data.getString(1));
            map.put("med_count", data.getString(2));
            Cursor data_time = timeDBHelper.getData(Integer.valueOf(data.getString(0)));

            String datetime = "";
            data_time.moveToFirst();
            while (!data_time.isAfterLast()) {
                //int id=result.getInt(0);
                datetime = datetime + data_time.getString(0) + "    ";
                // do something useful with these
                data_time.moveToNext();
            }

            map.put("med_date1", datetime);

            listData.add(map);
            data.moveToNext();
        }

        //create the list adapter and set the adapter
        String[] from = {"med_name", "med_count", "med_date1"};
        int[] to = {R.id.med_name, R.id.med_count, R.id.date1};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listData, R.layout.med_item, from, to);

//        ListAdapter adapter =
//                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData);
        med_list_list.setAdapter(adapter);
    }


}
