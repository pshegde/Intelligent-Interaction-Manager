package com.example.iim;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class NotificationNotImpCaller extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_not_imp_caller);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification_not_imp_caller, menu);
		return true;
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rbtnAlarm:
	            if (checked){
	            	  Toast.makeText(NotificationNotImpCaller.this, "Alarm is Selected", Toast.LENGTH_SHORT).show();
	            }
	            break;
	        case R.id.rbtnEmail:
	            if (checked){
	            	  Toast.makeText(NotificationNotImpCaller.this, "Email is Selected", Toast.LENGTH_SHORT).show();
	            }
	            break;
	        case R.id.rbtnVibrate:
	            if (checked){
	            	  Toast.makeText(NotificationNotImpCaller.this, "Vibrate is Selected", Toast.LENGTH_SHORT).show();
	            }
	            break;
	    }
	}

}
