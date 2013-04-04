package com.example.iim;

import java.util.List;

import com.iim.utils.CallerGroupManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ImpCallerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imp_caller);
		CallerGroupManager callerGroupManager = new CallerGroupManager(getApplicationContext());
		List<String> contacts = callerGroupManager.fetchImportantContacts();
		contacts.add("ImportantContact");
		contacts.add("RtoCary");
		contacts.add("RtoGreensboro");
		contacts.add("RtoLakeRaleigh");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		contacts.add("RtoRTP");
		//create an ArrayAdaptar from the String Array
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.caller_list,contacts);
		ListView listView = (ListView) findViewById(R.id.listView1);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//  listView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imp_caller, menu);
		return true;
	}

}
