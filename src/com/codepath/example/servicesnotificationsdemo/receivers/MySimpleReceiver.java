package com.codepath.example.servicesnotificationsdemo.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

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

	// Delegate method which passes the result to the receiver if the receiver
	// has been assigned
	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (receiver != null) {
			receiver.onReceiveResult(resultCode, resultData);
		}
	}
}
