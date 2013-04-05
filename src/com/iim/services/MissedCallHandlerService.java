package com.iim.services;

import com.iim.utils.MissedCallListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

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
		MissedCallListener phoneListener=new MissedCallListener();
	    TelephonyManager telephony = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
	    telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}

}
