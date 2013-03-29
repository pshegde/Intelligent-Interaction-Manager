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

	private static final String DATABASE_NAME = "IIMDB";

	private static final String DATABASE_TABLE = "important_contacts";

	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase db;

	public DBHelper(Context ctx) {
		try {
			System.out.println("Creating databse from dbhelper");
			db = ctx.openOrCreateDatabase(DATABASE_NAME, DATABASE_VERSION, null);
			db.execSQL(IMP_CONTACT_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		db.close();
	}

	public void createRow(String lookup_key, String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("lookup_key", lookup_key);
		initialValues.put("name", name);
		db.insert(DATABASE_TABLE, null, initialValues);
	}

	public void deleteRow(long rowId) {
		db.delete(DATABASE_TABLE, "_id=" + rowId, null);
	}

	public List<Row> fetchAllRows() {
		ArrayList<Row> ret = new ArrayList<Row>();
		try {
			Cursor c = db.query(DATABASE_TABLE, new String[] {"_id", "lookup_key", "name"}, null, null, null, null, null);
			int numRows = c.getColumnCount();
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

//	public Row fetchRow(long rowId) {
//		Row row = new Row();
//		Cursor c = db.query(true, DATABASE_TABLE, new String[] {
//						"_id", "lookup_key", "name"}, "_id=" + rowId, null, null,null, null, null);
//		if (c.getColumnCount() > 0) {
//			c.moveToFirst();
//			row._Id = c.getLong(0);
//			row.lookup_key = c.getString(1);
//			row.name = c.getString(2);
//			return row;
//		} else {
//			row. = -1;
//			row.code = row.name= null;
//		}
//		return row;
//	}

}
