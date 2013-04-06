package com.iim.services;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class SendEmailTask extends AsyncTask<Void, Void, Void> {

	Exception error;
	Intent i;
	
	String user;
	String password;
	String hostname;
	String recipient;
	
	Map<String, String> missedCalls = new HashMap<String,String>();
	
	void setIntent(Intent intent){
		i=intent;
	}
	
	SendEmailTask(String user, String password, String hostname, String recipient, Map<String, String> missed){
		this.user = user;
		this.password = password;
		this.hostname = hostname;
		this.recipient = recipient;

		// clean missed calls map
		this.missedCalls.clear();
		
		for(String key: missed.keySet()) {
			  if(!this.missedCalls.containsKey(key)) {
				  this.missedCalls.put(key,missed.get(key));
			  }
		}
	}
	
	protected Void doInBackground(Void... params) {
		try {  
			SMTPSendEmail sftpClient = new SMTPSendEmail();
			sftpClient.sendEmailTo(user,password,hostname,recipient,missedCalls);
        } catch (Exception e) {   
            Log.e("SendMail", e.getMessage(), e);   
        } 
		return null;
	}

	protected void onPostExecute(Void result) {
		/*if(error != null){
			 
		} else{
			List<Ride> rideList = result;
			for(Ride r:rideList){
				System.out.println(r.getName());
			}
			Intent i = new Intent(getApplicationContext(), JoinActivity.class);
			i.putParcelableArrayListExtra("rideList",(ArrayList<? extends Parcelable>) rideList);
			startActivity(i);
		}*/
	}

}
