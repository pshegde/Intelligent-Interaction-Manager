package com.example.iim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.iim.utils.DBHelper;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnImpCallers  = (Button) findViewById(R.id.btnImpCallers);

		// Listening to login button
		btnImpCallers.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ImpCallerActivity.class);
				startActivity(i);
			}
		});

		Button btnNotType  = (Button) findViewById(R.id.btnNotType);

		// Listening to login button
		btnNotType.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), NotificationTypeActivity.class);
				startActivity(i);
			}
		});

		Button btnImportCal  = (Button) findViewById(R.id.btnImportCal);

		// Listening to login button
		btnImportCal.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

		// Loading the database .... 
		DBHelper dbhelper = new DBHelper(super.getApplicationContext());
		System.out.println("data created.");
		dbhelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
