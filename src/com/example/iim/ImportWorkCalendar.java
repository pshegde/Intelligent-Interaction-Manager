package com.example.iim;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import com.iim.utils.DBHelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.text.format.DateUtils;

public class ImportWorkCalendar {
	//static boolean isBusy ;
	ContentResolver contentResolver;
	Context context;
	public  ImportWorkCalendar(Context ctx) {
		contentResolver = ctx.getContentResolver();
		context = ctx;
	}

	public boolean getUserStatus(String name,String number) {
		boolean b = false;
		try {
			b = new MyCalendar().execute(new String[]{name,number}).get();
			System.out.println("b is: " + b);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}

	public class MyCalendar extends AsyncTask<String, String, Boolean> 
	{
		private final String[] COLS = new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY,CalendarContract.Events.RRULE,CalendarContract.Events.RDATE};

		private final String[] COLS_2 = new String[] {CalendarContract.Instances.BEGIN, CalendarContract.Instances.END};//, CalendarContract.Events.ALL_DAY,CalendarContract.Events.RRULE,CalendarContract.Events.RDATE};
		
		@Override
		protected Boolean doInBackground(String... namenumber) {
			System.out.println("**********in bckgrd");
			Cursor cursor = null;
			try
			{
				Calendar c = Calendar.getInstance();
				//c.set(2013,03,04,16,00);
				Date now = c.getTime();
				
				Date freeTime = null;
				Calendar cal = Calendar.getInstance();
				Date now1 = cal.getTime();
				long begin_param = now1.getTime();
				cal.add(Calendar.DATE, 1);
				now1 = cal.getTime();
				long end_param = now1.getTime();
				cursor =  CalendarContract.Instances.query(contentResolver, COLS_2,begin_param,end_param);
				
				boolean isBusy = false;
				while(cursor.moveToNext()) 
				{
					Date begin = new Date(cursor.getLong(0));
					Date end = new Date(cursor.getLong(1));
					System.out.println("begin:" + begin);
					System.out.println("end:" + end);
					if((begin.getTime() <= now.getTime()) && (end.getTime() > now.getTime())){
						System.out.println("BUSY**********");
						isBusy = true;
						now = end;
						freeTime = end;
					} 
					
				}

				if(isBusy){
					///send to database
					System.out.println("Free time: " + freeTime.getDate() + " " + freeTime.getHours() +" "+ freeTime.getMinutes());
					DBHelper helper = DBHelper.getInstance(context);
					helper.createMissedCallRow(namenumber[0],namenumber[1],freeTime.getTime(),"0");
					System.out.println("BUSY");
					Calendar c1 = Calendar.getInstance();
					c1.setTime(freeTime);
					System.out.println(c1);
					
					
				} else{
					System.out.println("NOT BUSY");
				}
				return isBusy;

				
			}
			catch(Exception e)
			{
				System.out.println("DEBUG"+ ""+e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) 
		{
			System.out.println("**********in post");
			super.onPostExecute(result);
		}

	
	}
}