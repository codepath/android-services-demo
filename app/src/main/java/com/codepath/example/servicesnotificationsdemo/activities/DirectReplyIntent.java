package com.codepath.example.servicesnotificationsdemo.activities;

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
import android.widget.Toast;

import static com.codepath.example.servicesnotificationsdemo.services.ImageDownloadService.NOTIF_ID;

public class DirectReplyIntent extends IntentService {

    public static String KEY_TEXT_REPLY = "key_text_reply";

    public DirectReplyIntent() {
        super("DirectReplyIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CharSequence directReply = getMessageText(intent);
        if (directReply != null) {
            Toast.makeText(this, directReply, Toast.LENGTH_LONG).show();
            Notification repliedNotification =
                    new NotificationCompat.Builder(DirectReplyIntent.this)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentText("Received: " + directReply)
                            .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);

            notificationManager.notify(NOTIF_ID, repliedNotification);
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
