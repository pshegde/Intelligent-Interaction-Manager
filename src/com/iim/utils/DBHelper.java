package com.iim.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {
	class Row extends Object {
		public long _Id;
		public String lookup_key;
		public String name;
	}
	
	private static final String IMP_CONTACT_CREATE =
			"create table if not exist important_contacts(_id integer primary key autoincrement, "
					+ "lookup_key text not null,"
					+ "name text not null"
					+");";

	private static final String NOTIFICAITON_CREATE =
			"create table if not exist notification(_id integer primary key autoincrement, "
					+ "notification_type character(2) not null,"
					+ "email_id text,"
					+ "caller_type text not null"
					+");";
	
	private static final String MISSED_CALL_CREATE =
			"create table if not exist missed_call(_id integer primary key autoincrement, "
					+ "caller_name text not null,"
					+ "caller_no text not null,"
					+ "callee_free_time long,"
					+ "is_notified character(1)"
					+");";

	private static final String DATABASE_NAME = "IIMDB";

	private static final String DATABASE_TABLE_CONTACTS = "important_contacts";

	private static final String DATABASE_TABLE_NOTIFICATION = "notification";

	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase db;
	
	private static DBHelper dbHelper = null;

	private DBHelper(Context ctx) {
		try {
			db = ctx.openOrCreateDatabase(DATABASE_NAME, DATABASE_VERSION, null);
			db.execSQL(IMP_CONTACT_CREATE);
			db.execSQL(NOTIFICAITON_CREATE);
			db.execSQL(MISSED_CALL_CREATE);
			createNotificationRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DBHelper getInstance(Context ctx) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(ctx);
		}
		return dbHelper;
	}
	
	private void createNotificationRecords() {
		Cursor c = db.query(DATABASE_TABLE_NOTIFICATION, new String[] {"_id", "notification_type", "email_id","caller_type"}, null, null, null, null, null);
		if (c.getCount() > 0) {
			return;
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put("notification_type", "V");
		initialValues.put("caller_type", "I");
		db.insert(DATABASE_TABLE_NOTIFICATION, null, initialValues);

		initialValues = new ContentValues();
		initialValues.put("notification_type", "V");
		initialValues.put("caller_type", "UI");
		db.insert(DATABASE_TABLE_NOTIFICATION, null, initialValues);
	}


	public void close() {
		db.close();
	}

	public void createRow(String lookup_key, String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("lookup_key", lookup_key);
		initialValues.put("name", name);
		db.insert(DATABASE_TABLE_CONTACTS, null, initialValues);
	}

	public void createMissedCallRow(String name,String number, long free_time, String is_notified) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("caller_name", name);
		initialValues.put("caller_no", number);
		initialValues.put("callee_free_time", free_time);
		initialValues.put("is_notified", is_notified);
		db.insert(MISSED_CALL_CREATE, null, initialValues);
	}
	
	public void deleteRow(long rowId) {
		db.delete(DATABASE_TABLE_CONTACTS, "_id=" + rowId, null);
	}

	public List<Row> fetchAllRows() {
		ArrayList<Row> ret = new ArrayList<Row>();
		try {
			Cursor c = db.query(DATABASE_TABLE_CONTACTS, new String[] {"_id", "lookup_key", "name"}, null, null, null, null, null);
			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				Row row = new Row();
				row._Id = c.getLong(0);
				row.lookup_key = c.getString(1);
				row.name = c.getString(2);
				ret.add(row);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("Exception on query", e.toString());
		}
		return ret;
	}

}
