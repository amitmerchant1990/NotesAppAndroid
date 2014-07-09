package com.amitmerchant.notesapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "notesManager";
	private static final String TABLE_NOTES = "notes";
	
	private static final String KEY_ID = "_id";
    private static final String KEY_NOTE = "note";
    private static final String KEY_DATE = "date_added";
    
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
    
 // Adding new contact
    public void addNote(Notes note) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getNote()); // Contact Name
           // Inserting Row
        db.insert(TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single contact
    public Notes getNote(int id) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                KEY_NOTE, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        Notes note = new Notes(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return note;
    }
     
    // Getting All Contacts
    public List<Notes> getAllNotes() {
    	List<Notes> noteList = new ArrayList<Notes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notes note = new Notes();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setNote(cursor.getString(1));
                // Adding contact to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return noteList;
    }
     
    // Getting contacts Count
    public int getNotesCount() {
    	
    	String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    // Updating single contact
    public int updateNote(Notes note) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getNote());
        
        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }
     
    // Deleting single contact
    public void deleteNote(Notes note) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }
}
