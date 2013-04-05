package com.example.iim;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

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
	public  ImportWorkCalendar(Context ctx) {
		contentResolver = ctx.getContentResolver();
	}

	public boolean getUserStatus() {
		boolean b = false;
		try {
			b = new MyCalendar().execute().get();
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

	public class MyCalendar extends AsyncTask<Intent, String, Boolean> 
	{
		private final String[] COLS = new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY};

		@Override
		protected Boolean doInBackground(Intent... arg0) {
			System.out.println("**********in bckgrd");
			Cursor cursor = null;
			try
			{
				Calendar c = Calendar.getInstance();
				//c.set(2013,03,04,16,00);
				Date now = c.getTime();
				
				Date freeTime = null;
				//"("+CalendarContract.Events.DTSTART+"<="+now.getTimeInMillis()+" and " +CalendarContract.Events.DTEND+">"+now.getTimeInMillis()+")"
				cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, COLS, null, null, null);
				//endTimeAllDay.getTimeInMillis()+"))", null, null);

				boolean isBusy = false;
				while(cursor.moveToNext()) 
				{
					final String title = cursor.getString(0);
					final Date begin = new Date(cursor.getLong(1));
					final Date end = new Date(cursor.getLong(2));
					final Boolean allDay = !cursor.getString(3).equals("0");
					System.out.println("title:" + title);
					System.out.println("begin:" + begin);
					System.out.println("end:" + end);
					System.out.println("allDay:" + allDay);
					if((begin.getTime() <= now.getTime()) && (end.getTime() > now.getTime())){
						
						System.out.println("BUSY**********");
						isBusy = true;
						now = end;
						freeTime = end;
					} 
					
				}

				if(isBusy){
					///send to database
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