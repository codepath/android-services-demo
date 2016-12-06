package com.codepath.example.servicesnotificationsdemo.services;

import com.codepath.example.servicesnotificationsdemo.R;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class DirectReplyIntent extends IntentService {

    public static String KEY_TEXT_REPLY = "key_text_reply";
    public static String KEY_NOTIFY_ID = "key_notify_id";

    public DirectReplyIntent() {
        super("DirectReplyIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CharSequence directReply = getMessageText(intent);
        if (directReply != null) {
            Notification repliedNotification =
                    new NotificationCompat.Builder(DirectReplyIntent.this)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentText("Received: " + directReply)
                            .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);

            int notifyId = intent.getIntExtra(KEY_NOTIFY_ID, -1);
            notificationManager.notify(notifyId, repliedNotification);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }
}
