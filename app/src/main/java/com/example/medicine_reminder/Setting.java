package com.example.medicine_reminder;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Setting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("設定");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        private Preference notification, about_us;
        private Context mContext;
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            mContext = getActivity();
            notification = findPreference("notification");
            about_us = findPreference("about");

            notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Toast.makeText(mContext,String.format("Preference的值為%s", newValue), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });


            about_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(mContext, "Click", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AboutUs.class);
                    startActivity(intent);
                    return false;
                }
            });


        }

    }

}
