package com.iim.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

public class CallerGroupManager {
	
	Context context;
	
	public CallerGroupManager(Context ctx){
		context = ctx;
	}
	
	public ArrayList<String> fetchImportantContacts(){
	    String[] projection = new String[]{
	    		ContactsContract.Contacts.DISPLAY_NAME};
	    
	    Cursor c = this.context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, "starred=?",
	    	    new String[] {"1"}, null);
	    
	    ArrayList<String> importantContacts = new ArrayList<String>();
	    
	    String s = null;
	    
	    while(c.moveToNext()){
	    	s = c.getString(0);
	    	importantContacts.add(s);
	    	System.out.println(s);
	    }
	    System.out.println(importantContacts);
	    return importantContacts;
	}
	
	public String getGoogleAccount(){
	    AccountManager manager = AccountManager.get(this.context); 
	    Account[] accounts = manager.getAccountsByType("com.google"); 
	    List<String> possibleEmails = new LinkedList<String>();

	    for (Account account : accounts) {
	      System.out.println(account.name);
	      return account.name;
	    }
	    return null;
	}
	
}
