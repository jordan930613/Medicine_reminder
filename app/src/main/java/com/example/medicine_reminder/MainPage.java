package com.example.medicine_reminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment {

    private View view;
    private FragmentActivity c = getActivity();
    private RecyclerView recyclerView;
    private MainPage_Adapter adapter;
    private Button btnAdd;
    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mName = new ArrayList<>();

    public MainPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_main_page, container, false);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] TIME ={
                "07:30", "11:30", "15:30", "19:30", "23:30"};
        String[] NAME = new String[5] ;
        int[] Ic = new int[5] ; ;
        for(int i = 0 ; i<5;i++){
            mName.add("性愛藥");
        }

        for(int i =0;i<5;i++){
            mTime.add(TIME[i]);
        }


        btnAdd = (Button)view.findViewById(R.id.btnadd);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_mainpage);
        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        // 設置格線
        //recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 將資料交給adapter
        adapter = new MainPage_Adapter(mTime, mName);
        // 設置adapter給recycler_view
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 新增一個項目
                adapter.addItem("10:30", "安安藥");
            }
        });
    }


}
