package com.codepath.example.servicesnotificationsdemo.services;

import com.codepath.example.servicesnotificationsdemo.DemoApplication;
import com.codepath.example.servicesnotificationsdemo.R;
import com.codepath.example.servicesnotificationsdemo.activities.ImagePreviewActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static com.codepath.example.servicesnotificationsdemo.services.DirectReplyIntent.KEY_NOTIFY_ID;
import static com.codepath.example.servicesnotificationsdemo.services.DirectReplyIntent.KEY_TEXT_REPLY;

public class ImageDownloadService extends JobIntentService {
    public static final int JOB_ID = 101;

    public static final int NOTIF_ID = 82;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ImageDownloadService.class, JOB_ID, work);
    }

    // Must create a default constructor
    public ImageDownloadService() {
        super();
    }

    // Download the image and create notification
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("DEBUG", "ImageDownloadService triggered");

        // Extract additional values from the bundle
        String imageUrl = intent.getStringExtra("url");
        // Download image
        Bitmap bitmap = downloadImage(imageUrl);
        // Sleep to waste time
        sleep(2000);
        // Create completion notification
        createNotification(bitmap);
    }

    protected Bitmap downloadImage(String address) {
        // Convert string to URL
        URL url = getUrlFromString(address);
        // Get input stream
        InputStream in = getInputStream(url);
        // Decode bitmap
        Bitmap bitmap = decodeBitmap(in);
        // Return bitmap result
        return bitmap;
    }

    // Construct compatible notification
    private void createNotification(Bitmap bmp) {
        // Resize bitmap
        Bitmap resizedBitmap = Bitmap
                .createScaledBitmap(bmp, bmp.getWidth() / 5, bmp.getHeight() / 5, false);
        // Construct pending intent to serve as action for notification item
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra("bitmap", resizedBitmap);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, DemoApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Image Download Complete!")
                .setContentText(
                        "Image download from IntentService has completed! Click to view!")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bmp))
                .setContentIntent(pIntent);

        if (android.os.Build.VERSION.SDK_INT >= 24) {

            Intent directReplyIntent = new Intent(this, DirectReplyIntent.class);
            directReplyIntent.putExtra(KEY_NOTIFY_ID, NOTIF_ID);

            int flags = FLAG_CANCEL_CURRENT;
            PendingIntent directReplyPendingIntent =
                    PendingIntent.getService(this, 0, directReplyIntent, flags);
            // http://android-developers.blogspot.com/2016/06/notifications-in-android-n.html
            RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                    .setLabel("Type Message").build();
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher, "Reply", directReplyPendingIntent)
                    .addRemoteInput(remoteInput).build();
            builder.addAction(action);

        }
        Notification noti = builder.build();

        // Hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIF_ID, noti);
    }

    private URL getUrlFromString(String address) {
        URL url;
        try {
            url = new URL(address);
        } catch (MalformedURLException e1) {
            url = null;
        }
        return url;
    }

    private InputStream getInputStream(URL url) {
        InputStream in;
        // Open connection
        URLConnection conn;
        try {
            conn = url.openConnection();
            conn.connect();
            in = conn.getInputStream();
        } catch (IOException e) {
            in = null;
        }
        return in;
    }

    private Bitmap decodeBitmap(InputStream in) {
        Bitmap bitmap;
        try {
            // Turn response into Bitmap
            bitmap = BitmapFactory.decodeStream(in);
            // Close the input stream
            in.close();
        } catch (IOException e) {
            in = null;
            bitmap = null;
        }
        return bitmap;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
