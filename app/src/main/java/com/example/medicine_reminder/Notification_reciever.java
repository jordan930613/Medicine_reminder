package com.example.medicine_reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

public class Notification_reciever extends BroadcastReceiver {

    MainPage mainPage;

    String name;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("which first = " + 3);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        mainPage = new MainPage();
        name =  mainPage.get_sed_name();
        System.out.println("name = " + name);
//        int channel = intent.getIntExtra("channel", 0);
//        System.out.println("channel = " + channel);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("該吃藥嘍！！！", "藥包 ： " + name);
        notificationHelper.getManager().notify(1, nb.build());

    }
}
