package com.example.iim;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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

}
