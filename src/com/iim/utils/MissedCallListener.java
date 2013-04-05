package com.iim.utils;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MissedCallListener extends PhoneStateListener {
		
		final static String TAG = "STATELISTENER";
		boolean ringing = false;
		boolean offhook = false;
		/*MissedCallDetectorService missedCallDetectorService;
		
		public MissedCallListener(MissedCallDetectorService missedCallDetectorService){
			this.missedCallDetectorService = missedCallDetectorService;
		}*/
		
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				Log.d(TAG,"IDLE");		
				Log.d(TAG,"ringing "+ringing+" offhook "+offhook);
				if(ringing&&(!offhook))
				{
					Log.d(TAG, "You've got a missed call from " + incomingNumber);
					 ringing = false;
					 offhook = false;
					 //missedCallDetectorService.setFlag(true);
					 System.out.println("You've got a missed call from " + incomingNumber);
				}
				break;

			case TelephonyManager.CALL_STATE_RINGING:
				Log.d(TAG, "RINGING");
				ringing = true;
				offhook = false;
				
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.d(TAG, "OFFHOOK");
				offhook = true;
				ringing = false;
				break;

			default:
				break;
			}
		}

}
