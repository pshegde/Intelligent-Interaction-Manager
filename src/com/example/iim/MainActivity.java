package com.example.iim;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnImpCallers  = (Button) findViewById(R.id.btnImpCallers);

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
