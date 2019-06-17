package com.example.medicine_reminder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment {

    private View view;
    private FragmentActivity c = getActivity();
    private RecyclerView recyclerView;
    private MainPage_Adapter adapter;
    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<Boolean> mEat = new ArrayList<>();
    Notification_reciever notification_reciever;
    String getName[];
    String sendname = "";
    public static String name = "";
    int getclick = 0;
    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;
    int i = 0, m = 0;
    int countTure;

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

        mDBHelper = new DBHelper(getActivity());
        timeDBHelper = new TimeDBHelper(getActivity());
        adapter = new MainPage_Adapter(mTime, mName, mEat);
        adapter.count();
        getclick = adapter.sendclick();
        SQLiteDAO sqLiteDAO = new SQLiteDAO(getActivity());


        Cursor sort = timeDBHelper.sort();
        sort.moveToFirst();

        Calendar compare_time = Calendar.getInstance();
        int hour = compare_time.get(Calendar.HOUR_OF_DAY);
        int min = compare_time.get(Calendar.MINUTE);

        System.out.println("current hour =" + hour);
        System.out.println("current min =" + min);

        i = 0;
        int count =timeDBHelper.getGet_datacount();
        countTure = 0;

        for (int j = 0; j < count; j++){
            if (mEat.get(j) == false)
                countTure++;
        }

        while (!sort.isAfterLast()) {
            System.out.println("sort = " + sort.getString(0));
            String splited[] = sort.getString(0).split(" : ");
            System.out.println("check = " + Integer.parseInt(splited[0]));

//            if (hour < Integer.parseInt(splited[0])) {
//                mTime.add(sort.getString(0));
//                String getid = sort.getString(1);
//                Cursor data = mDBHelper.getname(getid);
//                data.moveToFirst();
//                mName.add(data.getString(0));
//                setNotification();
//
//            } else if (hour == Integer.parseInt(splited[0])) {
//                if (min <= Integer.parseInt(splited[1])) {
//                    mTime.add(sort.getString(0));
//                    String getid = sort.getString(1);
//                    Cursor data = mDBHelper.getname(getid);
//                    data.moveToFirst();
//                    mName.add(data.getString(0));
//                    setNotification();
//                }
//
//            } else if (hour > Integer.parseInt(splited[0])) {
//
//            }


            mTime.add(sort.getString(0));
            String getid = sort.getString(1);
            Cursor data = mDBHelper.getname(getid);
            data.moveToFirst();
            mName.add(data.getString(0));


            if (hour < Integer.parseInt(splited[0])) {
                setNotification(countTure);
            } else if (hour == Integer.parseInt(splited[0])) {
                if (min <= Integer.parseInt(splited[1])) {
                    setNotification(countTure);
                } else {
                    i++;
                }
            } else {
                i++;
            }


            sort.moveToNext();
        }
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_mainpage);
        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        // 設置格線
        //recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 將資料交給adapter
        adapter = new MainPage_Adapter(mTime, mName, mEat);
        //設定notification
        //setNotification();

        // 設置adapter給recycler_view
        recyclerView.setAdapter(adapter);

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 新增一個項目
//                adapter.addItem("10:30", "安安藥");
//            }
//        });
    }

    public void setNotification(int countTrue) {
        System.out.println("which first = " + 1);
        name = sendName();
        System.out.println("sed_name = " + name);

        getName = adapter.sendPosition(countTrue);
        String time = getName[1];
        String timeer[] = time.split(" : ");
        String hour = timeer[0];
        String min = timeer[1];

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));

        notification_reciever = new Notification_reciever();
        System.out.println("hour = " + hour);
        System.out.println("min = " + min);

        System.out.println("gettimeinmillis = " + calendar.getTimeInMillis());

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), Notification_reciever.class);

//        int getnameid = mDBHelper.get_name_id(name);
//        int gettimeid = timeDBHelper.get_time_id(getnameid);
//        intent.putExtra("channel", gettimeid);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public String sendName() {
        System.out.println("which first = " + 2);
        adapter = new MainPage_Adapter(mTime, mName, mEat);
        getName = adapter.sendPosition(countTure);

        sendname = getName[0];
        System.out.println("sendname = " + sendname);

        return sendname;
    }

    public String get_sed_name() {
        System.out.println("get_sed_name = " + name);

        return name;
    }

}
