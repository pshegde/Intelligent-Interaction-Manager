package com.iim.utils;

public class MissedCallRow {
	public long _Id;

	public String caller_name;

	public String caller_no;

	public String is_notified;

	long callee_free_time;

	public long get_Id() {
		return _Id;
	}

	public String getCaller_name() {
		return caller_name;
	}

	public String getCaller_no() {
		return caller_no;
	}

	public String getIs_notified() {
		return is_notified;
	}

	public long getCallee_free_time() {
		return callee_free_time;
	}

}
