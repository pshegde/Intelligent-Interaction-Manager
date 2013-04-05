package com.iim.services;

import java.util.ArrayList;

import com.iim.utils.CallerGroupManager;
import com.iim.utils.DBHelper;
import com.iim.utils.MissedCallListener;

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
import android.util.Log;

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

		try {
			if (contactLookup != null && contactLookup.moveToNext()) {
				String name = contactLookup.getString(indexName);
				System.out.println(name);
				this.sendEmailNotification(name, incomingNumber);
				CallerGroupManager callerGroupManager = new CallerGroupManager(
						getApplicationContext());
				ArrayList<String> importantContacts = callerGroupManager
						.fetchImportantContacts();
				if (importantContacts.contains(name)) {
					// Fetch notification type for Important Caller

				} else {
					// Fetch notification type for unImportant Caller
				}

			}
		} finally {
			if (contactLookup != null) {
				contactLookup.close();
			}
		}
	}

	public void sendEmailNotification(String incomingName, String incomingNumber) {
        SendEmailTask sendEmailTask = new SendEmailTask("csc750iim@gmail.com","iimcsc750", "smtp.gmail.com", "pshegde@ncsu.edu", incomingNumber, incomingName);
        sendEmailTask.execute();
	}

}
