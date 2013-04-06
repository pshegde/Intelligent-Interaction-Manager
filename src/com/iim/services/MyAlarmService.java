package com.iim.services;

import java.util.ArrayList;
import java.util.Calendar;

import com.iim.utils.DBHelper;
import com.iim.utils.MissedCallListener;
import com.iim.utils.MissedCallRow;

import android.app.AlarmManager;
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
		//GET from from missed call table the 1 contacts and send the email 
		//get the number and name from the table and send in the email
		//select * from missed_call where if_notified="1"; 
		//make if_notified for these "2"
		//compile all in 1 email and send to the callee using the sendnotification method in the missedcallhandler	

		DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
		ArrayList<MissedCallRow> missedCallRow = (ArrayList<MissedCallRow>) dbHelper.fetchAllMissedCallRows();

		for (MissedCallRow currentRow : missedCallRow) {
			if (currentRow.getIs_notified().equals("1")) {
				// update the row to set it to 1
				dbHelper.updateMissedCallRow(currentRow.get_Id(), currentRow.getCaller_name(), currentRow.getCaller_no(), currentRow.getCallee_free_time(), "2");

			}
		}
	}
}
