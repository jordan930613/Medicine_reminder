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

        String[] TITLE ={
                "Bell", "Doraemon", "Doramin", "Doraemon", "Nobita", "Doraemon", "Teacher", "Doraemon", "Shizuka", "Doraemon"
        };
        String[] SUBTITLE = {
                "content", "content", "content", "content", "content", "content", "content", "content", "content", "content"
        };
        int[] Ic = {
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
                R.drawable.ic_menu_home,
        };
        ListView ls = (ListView)view.findViewById(R.id.lv_mainpage);
        CustomizeAdapter cs = new CustomizeAdapter(TITLE, SUBTITLE, Ic);
        ls.setAdapter(cs);
    }
}
