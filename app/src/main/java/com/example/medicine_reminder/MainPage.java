package com.example.medicine_reminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment {

    private View view;

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
            NAME[i]= "性愛藥";
            Ic[i]=R.drawable.ic_check;


        }

        ListView ls = (ListView)view.findViewById(R.id.lv_mainpage);
        CustomizeAdapter cs = new CustomizeAdapter(TIME, NAME, Ic);
        ls.setAdapter(cs);
    }
}
