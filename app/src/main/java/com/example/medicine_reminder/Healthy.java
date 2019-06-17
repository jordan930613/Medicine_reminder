package com.example.medicine_reminder;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Healthy extends Fragment {

    private View view;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private HealthAdapter adapter;
    private SQLiteDAO sqLiteDAO;
    private Item item;

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
        sqLiteDAO = new SQLiteDAO(getActivity());


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
         ArrayList<Integer> mSys = new ArrayList<>();
         ArrayList<Integer> mDia = new ArrayList<>();
         ArrayList<Integer> mPul = new ArrayList<>();
         ArrayList<Integer> mSugar = new ArrayList<>();
         ArrayList<Integer> mWeight = new ArrayList<>();
         ArrayList<String> mDate = new ArrayList<>();
         ArrayList<Long> mID = new ArrayList<>();
        for (int i = 1; i <= sqLiteDAO.getCount() ; i++) {
            item = sqLiteDAO.get(i);
            mSys.add(item.getSys());
            mDia.add(item.getDia());
            mPul.add(item.getPUL());
            mSugar.add(item.getBloodsugar());
            mWeight.add(item.getWeight());
            mDate.add(item.getLocaleDatetime());
            mID.add(item.getId());
        }
        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 設置格線
        //recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 將資料交給adapter
        adapter = new HealthAdapter(mSys, mDia, mPul, mSugar,mWeight,mDate,mID);
        // 設置adapter給recycler_view
        recyclerView.setAdapter(adapter);
    }

}
