package com.js.skb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDBHelp extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "list.db";  
    private static final int DATABASE_VERSION = 1;  
	public ListDBHelp(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	
		db.execSQL("CREATE TABLE IF NOT EXISTS list (_id integer PRIMARY KEY AUTOINCREMENT,"
				+ " name text not null,num not null)");  
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("ALTER TABLE foot ADD COLUMN other STRING"); 
	}
}

