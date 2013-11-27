package com.codepath.example.servicesnotificationsdemo.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.codepath.example.servicesnotificationsdemo.R;
import com.codepath.example.servicesnotificationsdemo.activities.ImagePreviewActivity;

public class ImageDownloadService extends IntentService {
	public static final int NOTIF_ID = 82;
	long timestamp;

	// Must create a default constructor
	public ImageDownloadService() {
		super("image-service");
	}

	// Download the image and create notification
	@Override
	protected void onHandleIntent(Intent intent) {
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
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 5, bmp.getHeight() / 5, false);
		// Construct pending intent to serve as action for notification item
		Intent intent = new Intent(this, ImagePreviewActivity.class);
		intent.putExtra("bitmap", resizedBitmap);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		// Create notification
		Notification noti = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Image Download Complete!").setContentText("Image download from IntentService has completed! Click to view!")
				.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bmp))
				.setContentIntent(pIntent).build();

		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
