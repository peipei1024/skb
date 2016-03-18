package com.js.skb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PraiseDBHelp extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "praise.db";  
    private static final int DATABASE_VERSION = 1;  
	public PraiseDBHelp(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	
		db.execSQL("CREATE TABLE IF NOT EXISTS praise (_id integer PRIMARY KEY AUTOINCREMENT,"
				+ " userid text not null,dishobjectid not null)");  
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("ALTER TABLE foot ADD COLUMN other STRING"); 
	}
}

