package com.iim.receivers;

import com.iim.services.MissedCallHandlerService;
import com.iim.utils.MissedCallListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MissedCallReceiver extends BroadcastReceiver{
	
	@Override
	  public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, MissedCallHandlerService.class);
	    context.startService(service);
	  }
	
}
