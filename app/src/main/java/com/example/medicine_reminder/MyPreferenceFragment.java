package com.example.medicine_reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class MyPreferenceFragment extends PreferenceFragment
{
    private Preference notification, about_us;
    private Context mContext;
    NotificationHelper notificationHelper;
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mContext = getActivity();
        notification = findPreference("notification");
        about_us = findPreference("about");
        final SharedPreferences pref = mContext.getSharedPreferences("preference", MODE_PRIVATE);
        notificationHelper = new NotificationHelper(mContext);

        notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if(newValue.equals(true)){
                    pref.edit().putInt("notification",1).commit();
                }else {
                    pref.edit().putInt("notification",0).commit();
                    notificationHelper.CancelNotification();
                }
                String lloogg = Integer.toString(pref.getInt("notification", -1));
                return true;
            }
        });


        about_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(mContext, AboutUs.class);
                startActivity(intent);
                return false;
            }
        });


    }

}
