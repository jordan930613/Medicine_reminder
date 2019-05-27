package com.example.medicine_reminder;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Medicine_package extends Fragment {
    private View v;
    private FloatingActionButton mainPage_btn;


    public Medicine_package() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_medicine_package, container, false);

        mainPage_btn = (FloatingActionButton)v.findViewById(R.id.mainPage_add);
        mainPage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedBag.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
