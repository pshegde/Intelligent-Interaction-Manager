package com.iim.services;

import com.iim.utils.MissedCallListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyAlarmService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		//send notification here to user
		System.out.println("Created an alarm...");
		//GET from from missed call table the 0 contacts and send the email 
		//get the number and name from the table and send in the email
		//select * from missed_call where if_notified="0"; 
		//make if_notified for these "1"
		//compile all in 1 email and send to the callee using the sendnotification method in the missedcallhandler
		
		
	}


}
