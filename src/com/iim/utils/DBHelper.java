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

	private static final String MISSED_CALL_CREATE =
			"create table if not exist missed_call(_id integer primary key autoincrement, "
					+ "caller_name text not null,"
					+ "caller_no text not null,"
					+ "callee_free_time long,"
					+ "is_notified character(1)"
					+");";

	private static final String DATABASE_NAME = "IIMDB";

	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_MISSED_CALL = "missed_call";

	private SQLiteDatabase db;

	private static DBHelper dbHelper = null;

	private DBHelper(Context ctx) {
		try {
			db = ctx.openOrCreateDatabase(DATABASE_NAME, DATABASE_VERSION, null);
			db.execSQL(MISSED_CALL_CREATE);
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

	public void close() {
		db.close();
	}

	public void createMissedCallRow(String name,String number, long free_time, String is_notified) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("caller_name", name);
		initialValues.put("caller_no", number);
		initialValues.put("callee_free_time", free_time);
		initialValues.put("is_notified", is_notified);
		db.insert(TABLE_MISSED_CALL, null, initialValues);
	}

	public void updateMissedCallRow(long id,String name, String number, long free_time, String is_notified) {
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("caller_name", name);
		updatedValues.put("caller_no", number);
		updatedValues.put("callee_free_time", free_time);
		updatedValues.put("is_notified", is_notified);
		db.update(TABLE_MISSED_CALL, updatedValues, "_id = " + id, null);
	}

	public List<MissedCallRow> fetchAllMissedCallRows() {
		ArrayList<MissedCallRow> missedCallRow = new ArrayList<MissedCallRow>();
		try {
			Cursor c = db.query(TABLE_MISSED_CALL, new String[] {"_id", "caller_name", "caller_no","caller_free_time","is_notified"}, null, null, null, null, null);
			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				MissedCallRow row = new MissedCallRow();
				row._Id = c.getLong(0);
				row.caller_name = c.getString(1);
				row.caller_no = c.getString(2);
				row.callee_free_time = c.getLong(3);
				row.is_notified = c.getString(4);
				missedCallRow.add(row);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("Exception on query", e.toString());
		}
		return missedCallRow;
	}
}
