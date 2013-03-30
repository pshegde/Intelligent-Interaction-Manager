package com.iim.utils;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class CallerGroupManager {
	
	Context context;
	
	public CallerGroupManager(Context ctx){
		context = ctx;
	}
	
	public void createGroup(){
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

	    ops.add(ContentProviderOperation
	            .newInsert(ContactsContract.Groups.CONTENT_URI)
	            .withValue(ContactsContract.Groups.TITLE, "Important").build());
	    try {

	        context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

	    } catch (Exception e) {
	        Log.e("Error", e.toString());
	    }
	}
	
	public ArrayList<String> fetchImportantContacts(){
		
		//Get group Id of the group we created - Important
		String gidOfImportantGroup = getGroupIdOfImportantGroup();
	    
	    Uri groupURI = ContactsContract.Data.CONTENT_URI;

	    String[] projection = new String[]{
	     ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID ,
	     ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID,
	     ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME};

	    Cursor c = this.context.getContentResolver().query(groupURI,
	    projection,
	    ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID+"="+gidOfImportantGroup,
	    null,null);
	    
	    ArrayList<String> importantContacts = new ArrayList<String>();
	    
	    String s = null;
	    
	    while(c.moveToNext()){
	    	s = c.getString(2);
	    	importantContacts.add(s);
	    	System.out.println(s);
	    }
	    
	    return importantContacts;
	}

	private String getGroupIdOfImportantGroup() {
		// TODO Auto-generated method stub
		final String[] GROUP_PROJECTION = new String[] {
	            ContactsContract.Groups._ID, ContactsContract.Groups.TITLE };
	    Cursor cursor = this.context.getContentResolver().query(
	    ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION, null,
	            null, ContactsContract.Groups.TITLE);
	  
	    while (cursor.moveToNext()) {

	        String id = cursor.getString(cursor
	                .getColumnIndex(ContactsContract.Groups._ID));

	        String gTitle = (cursor.getString(cursor
	                .getColumnIndex(ContactsContract.Groups.TITLE)));

	        System.out.println(gTitle);
	        
	        if (gTitle.equals("Important")) {
	           return id;
	        }
	    }
	    return null;
	}

	
	/*public Uri addContactToImportantGroup(long personId, long groupId){
		//ContactsContract.
		
		 //remove if exists
	    //this.removeFromGroup(personId, groupId);

	    ContentValues values = new ContentValues();
	    values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
	            personId);
	    values.put(
	            ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
	            groupId);
	    values
	            .put(
	                    ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
	                    ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);

	    return this.context.getContentResolver().insert(
	            ContactsContract.Data.CONTENT_URI, values);
	    
	}*/
	
	
}
