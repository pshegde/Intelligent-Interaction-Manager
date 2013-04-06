package com.example.iim;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class NotificationTypeActivity extends TabActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_type);
		TabHost tabHost = getTabHost();

		// Tab for Photos
		TabSpec impCallerSpec = tabHost.newTabSpec("Important Caller");
		impCallerSpec.setIndicator("",getResources().getDrawable(R.drawable.impcaller));
		Intent impCallerIntent = new Intent(this, NotificationImpCaller.class);
		impCallerSpec.setContent(impCallerIntent);


		// Tab for Songs
		TabSpec notImpCallerSpec = tabHost.newTabSpec("Not Important Caller");
		// setting Title and Icon for the Tab
		notImpCallerSpec.setIndicator("",getResources().getDrawable(R.drawable.notimpcaller));
		Intent notImpCallerIntent = new Intent(this, NotificationNotImpCaller.class);
		notImpCallerSpec.setContent(notImpCallerIntent);
//		// Tab for Videos
//		TabSpec videospec = tabHost.newTabSpec("videos");
//		videospec.setIndicator("Accounts", getResources().getDrawable(R.drawable.icon_videos_tab));
//		Intent videosIntent = new Intent(this, VideosActivity.class);
//		videospec.setContent(videosIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(impCallerSpec); // Adding photos tab
		tabHost.addTab(notImpCallerSpec); // Adding songs tab
//		tabHost.addTab(videospec); // Adding videos tab
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification_type, menu);
		return true;
	}

}
