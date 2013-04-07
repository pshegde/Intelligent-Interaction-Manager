package com.iim.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.iim.R;
import com.iim.utils.CallerGroupManager;
import com.iim.utils.DBHelper;
import com.iim.utils.MissedCallListener;
import com.iim.utils.MissedCallRow;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
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

		Map<String,String> missedCalls = new HashMap<String,String>();
		for (MissedCallRow currentRow : missedCallRow) {
			if (currentRow.getIs_notified().equals("1")) {
				// update the row to set it to 1
				dbHelper.updateMissedCallRow(currentRow.get_Id(), currentRow.getCaller_name(), currentRow.getCaller_no(), currentRow.getCallee_free_time(), "2");
				missedCalls.put(currentRow.getCaller_name(),currentRow.getCaller_no());
			}
		}
	
		String message = "You've got a missed call from ";
		int count = missedCalls.keySet().size();
		int countCheck = 0;
		for(String incomingName:missedCalls.keySet()){
			if(incomingName==null){
				message = message + missedCalls.get(incomingName);
			}
			else{
				message = message + incomingName;
			}
			countCheck++;
			if(countCheck!=count){
				message = message +",";
			}
		}
		
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Context context = getApplicationContext();
		NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context,MyAlarmService.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
        
        
    	CallerGroupManager callerGroupManager = new CallerGroupManager(getApplicationContext());
		String googleAccount = callerGroupManager.getGoogleAccount();
		SendEmailTask sendEmailTask = new SendEmailTask("csc750iim@gmail.com","iimcsc750", "smtp.gmail.com", googleAccount, missedCalls);
		sendEmailTask.execute();
	}
}
