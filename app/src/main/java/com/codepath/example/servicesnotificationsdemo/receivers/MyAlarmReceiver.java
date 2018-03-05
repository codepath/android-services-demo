package com.codepath.example.servicesnotificationsdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.codepath.example.servicesnotificationsdemo.services.MySimpleService;

public class MyAlarmReceiver extends BroadcastReceiver {
	public static final int REQUEST_CODE = 12345;
	public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

	// Triggered by the Alarm periodically (starts the service to run task)
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("DEBUG", "MyAlarmReceiver triggered");

		Intent i = new Intent(context, MySimpleService.class);
		i.putExtra("foo", "alarm!!");
		i.putExtra("receiver", MySimpleReceiver.setupServiceReceiver(context));
		MySimpleService.enqueueWork(context, i);
	}
}
