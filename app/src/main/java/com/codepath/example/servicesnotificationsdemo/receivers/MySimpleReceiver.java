package com.codepath.example.servicesnotificationsdemo.receivers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.codepath.example.servicesnotificationsdemo.activities.MainActivity;

import static android.app.Activity.RESULT_OK;

//Defines a generic receiver used to pass data to Activity from a Service
public class MySimpleReceiver extends ResultReceiver {
	private Receiver receiver;

	// Constructor takes a handler
	public MySimpleReceiver(Handler handler) {
		super(handler);
	}

	// Setter for assigning the receiver
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	// Defines our event interface for communication
	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);
	}

	// Setup the callback for when data is received from the service
	public static MySimpleReceiver setupServiceReceiver(final Context context) {
		MySimpleReceiver receiverForSimple = new MySimpleReceiver(new Handler());
		// This is where we specify what happens when data is received from the
		// service
		receiverForSimple.setReceiver(new MySimpleReceiver.Receiver() {
			@Override
			public void onReceiveResult(int resultCode, Bundle resultData) {
				if (resultCode == RESULT_OK) {
					String resultValue = resultData.getString("resultValue");
					Toast.makeText(context, resultValue, Toast.LENGTH_SHORT).show();
				}
			}
		});
		return receiverForSimple;
	}

	// Delegate method which passes the result to the receiver if the receiver
	// has been assigned
	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (receiver != null) {
			receiver.onReceiveResult(resultCode, resultData);
		}
	}
}
