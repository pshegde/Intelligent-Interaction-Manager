package com.example.iim;

import java.util.List;

import com.iim.utils.CallerGroupManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnImpCallers  = (Button) findViewById(R.id.btnImpCallers);

		CallerGroupManager callerGroupManager = new CallerGroupManager(getApplicationContext());
		String googleAccount = callerGroupManager.getGoogleAccount();
		if(googleAccount==null){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Google Account");
			alertDialog.setMessage("There is no Google account configured. Please configure your Google account from Settings");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				// add function here
				}
			});
			alertDialog.show();
		}
		else{
			TextView text = (TextView) findViewById(R.id.textView2);
	        text.setText("Google account is: " + googleAccount);
		}
		
		// Listening to login button
		btnImpCallers.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Loading the database .... 
				//DBHelper dbhelper = new DBHelper(super.getApplicationContext());
				//System.out.println("data created.");
				//dbhelper.close();
				
				
				Intent i = new Intent(getApplicationContext(), ImpCallerActivity.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
