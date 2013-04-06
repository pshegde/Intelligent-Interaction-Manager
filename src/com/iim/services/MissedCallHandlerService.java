package com.iim.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.iim.ImportWorkCalendar;
import com.iim.utils.CallerGroupManager;
import com.iim.utils.DBHelper;
import com.iim.utils.MissedCallListener;
import com.iim.utils.MissedCallRow;

public class MissedCallHandlerService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		MissedCallListener phoneListener = new MissedCallListener(this);
		TelephonyManager telephony = (TelephonyManager) this
				.getApplicationContext().getSystemService(
						Context.TELEPHONY_SERVICE);
		telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void handleMissedCall(String incomingNumber) {
		Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(incomingNumber));
		Cursor contactLookup = getApplicationContext().getContentResolver()
				.query(lookupUri,
						new String[] { ContactsContract.PhoneLookup._ID,
								ContactsContract.PhoneLookup.DISPLAY_NAME },
						null, null, null);

		int indexName = contactLookup
				.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);

		String name = null;
		try {
			if (contactLookup != null && contactLookup.moveToNext()) {
				name = contactLookup.getString(indexName);
				System.out.println(name);
			}
			else{
				name = null;
			}
			CallerGroupManager callerGroupManager = new CallerGroupManager(
					getApplicationContext());
			ArrayList<String> importantContacts = callerGroupManager
					.fetchImportantContacts();
			
			Map<String,String> missedCalls = new HashMap<String,String>();
			missedCalls.put(name,incomingNumber);
			
			if (importantContacts.contains(name)) {
				// Fetch notification type for Important Caller
				this.sendEmailNotification(missedCalls);
			} else {
				ImportWorkCalendar importWorkCalendar = new ImportWorkCalendar(getApplicationContext());
				boolean isBusy = importWorkCalendar.getUserStatus(name, incomingNumber);
				if(!isBusy){
					// Fetch notification type for unImportant Caller	
					this.sendEmailNotification(missedCalls);
				}
				else{
					System.out.println("He is busy");
					//start an alarm to send a notification later
					startAlarm();
				}
			}
		} finally {
			if (contactLookup != null) {
				contactLookup.close();
			}
		}
	}

	public void sendEmailNotification(Map<String,String> missed) {
        SendEmailTask sendEmailTask = new SendEmailTask("csc750iim@gmail.com","iimcsc750", "smtp.gmail.com", "psdeshp2@ncsu.edu", missed);
        sendEmailTask.execute();
	}

	public void sendNotification(){
		
	}
	
	public void startAlarm(){
		System.out.println("**Start an alarm..");
		//GET from from missed call table free time in calendar for the 0 - if_notify=0 contacts and set the alarm....
		//select free_time from missed_call where if_notified="0"; 
		//set free time in calendar
		
		Intent myIntent = new Intent(MissedCallHandlerService.this, MyAlarmService.class);
		PendingIntent pendingIntent = PendingIntent.getService(MissedCallHandlerService.this, 0, myIntent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		
		DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
		ArrayList<MissedCallRow> missedCallRow = (ArrayList<MissedCallRow>) dbHelper.fetchAllMissedCallRows();

		for (MissedCallRow currentRow : missedCallRow) {
			if (currentRow.getIs_notified().equals("0")) {
				// update the row to set it to 1
				dbHelper.updateMissedCallRow(currentRow.get_Id(), currentRow.getCaller_name(), currentRow.getCaller_no(), currentRow.getCallee_free_time(), "1");
				 // free_time TODO - Confirm with prajakta
				 alarmManager.set(AlarmManager.RTC_WAKEUP, currentRow.getCallee_free_time(), pendingIntent);
			}
		}
	}
}
