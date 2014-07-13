package com.amitmerchant.notesapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "notesManager";
	public static final String TABLE_NOTES = "notes";
	
	public static final String KEY_ID = "_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_DATE = "date_added";
    
    public DatabaseHandler(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db){
    	String CREATE_NOTES_TABLE = "CREATE TABLE "+TABLE_NOTES+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NOTE+" TEXT,"+KEY_DATE+" DATE"+")";
    	db.execSQL(CREATE_NOTES_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
 
        // Create tables again
        onCreate(db);
    }
    
 
}
