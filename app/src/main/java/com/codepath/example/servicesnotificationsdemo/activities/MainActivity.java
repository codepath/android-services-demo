package com.codepath.example.servicesnotificationsdemo.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.example.servicesnotificationsdemo.R;
import com.codepath.example.servicesnotificationsdemo.receivers.MyAlarmReceiver;
import com.codepath.example.servicesnotificationsdemo.receivers.MySimpleReceiver;
import com.codepath.example.servicesnotificationsdemo.services.ImageDownloadService;
import com.codepath.example.servicesnotificationsdemo.services.MySimpleService;

public class MainActivity extends Activity {
	public MySimpleReceiver receiverForSimple;
	private PendingIntent alarmPendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkForMessage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onSimpleService(View v) {
		// Construct our Intent specifying the Service
		Intent i = new Intent(this, MySimpleService.class);
		// Add extras to the bundle
		i.putExtra("foo", "bar");
		i.putExtra("receiver", MySimpleReceiver.setupServiceReceiver(MainActivity.this));
		// Start the service
		MySimpleService.enqueueWork(this, i);
	}
	
	public void onImageDownloadService(View v) {
		// Construct our Intent specifying the Service
		Intent i = new Intent(this, ImageDownloadService.class);
		// Add extras to bundle
		i.putExtra("url", "http://www.zastavki.com/pictures/1920x1200/2010/World_Australia_River_in_Australia_022164_.jpg");
		// Start the service
		ImageDownloadService.enqueueWork(this, i);
	}
	
	public void onStartAlarm(View v) {
		// Construct an intent that will execute the AlarmReceiver
	    Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
	    // Create a PendingIntent to be triggered when the alarm goes off
	    alarmPendingIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
	        intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    // Setup periodic alarm every 5 seconds
	    long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
	    int intervalMillis = 60000; // as of API 19, alarm manager will be forced up to 60000 to save battery
	    AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
	    // See https://developer.android.com/training/scheduling/alarms.html
		alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, alarmPendingIntent);
	}
	
	public void onStopAlarm(View v) {
		AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		if (alarmPendingIntent != null) {
		  alarm.cancel(alarmPendingIntent);
		}
	}
	
	// Checks to see if service passed in a message
	private void checkForMessage() {
		String message = getIntent().getStringExtra("message");
		if (message != null) {
			Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
		}
	}

}
