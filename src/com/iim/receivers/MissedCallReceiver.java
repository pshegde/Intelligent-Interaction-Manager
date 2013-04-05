package com.iim.receivers;

import com.iim.services.MissedCallHandlerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MissedCallReceiver extends BroadcastReceiver{
	
	@Override
	  public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, MissedCallHandlerService.class);
	    context.startService(service);
	  }
	
}
