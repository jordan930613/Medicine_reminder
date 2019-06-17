package com.example.medicine_reminder;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Healthy extends Fragment {

    private View view;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private HealthAdapter adapter;

    public Healthy() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_healthy, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = view.findViewById(R.id.healthadd);
        recyclerView = view.findViewById(R.id.recycler_view_health);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthIntent.class);
                Bundle bundle = new Bundle();
                bundle.putLong("ID", 0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
//         ArrayList<Integer> mSys = new ArrayList<>();
//         ArrayList<Integer> mDia = new ArrayList<>();
//         ArrayList<Integer> mPul = new ArrayList<>();
//         ArrayList<Integer> mSugar = new ArrayList<>();
//         ArrayList<Integer> mWeight = new ArrayList<>();
//         ArrayList<String> mDate = new ArrayList<>();
//         ArrayList<Long> mID = new ArrayList<>();
         List<Item> mItem = new ArrayList<>();
         SQLiteDAO sqLiteDAO = new SQLiteDAO(getActivity());
         mItem = sqLiteDAO.getAll();

        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 設置格線
        //recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 將資料交給adapter
        adapter = new HealthAdapter(mItem);
        // 設置adapter給recycler_view
        recyclerView.setAdapter(adapter);
    }

}
