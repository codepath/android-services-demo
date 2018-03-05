package com.codepath.example.servicesnotificationsdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class DemoApplication extends Application {

    public static String CHANNEL_ID = "ChannelID";

    @Override
    public void onCreate() {
        super.onCreate();

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", importance);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
