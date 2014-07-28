package com.amitmerchant.notesapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {
	private DatabaseHandler dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbhelper = new DatabaseHandler(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }
    
    public void close() {
        dbhelper.close();
    }

 // Adding new contact
    public void addNote(Notes note) {
    	
    	ContentValues values = new ContentValues();
        values.put(dbhelper.KEY_NOTE, note.getNote()); // Contact Name
        if(note.getReminderStatus()==true){
        	values.put(dbhelper.KEY_IS_REMINDER,1);
        }
        values.put(dbhelper.KEY_REMINDER_DATE, note.getReminderDate().getTime());
           // Inserting Row
        database.insert(dbhelper.TABLE_NOTES, null, values);
        database.close(); // Closing database connection
    }
     
    // Getting single contact
    public Cursor readNote() {
    	String[] allColumns = new String[]{dbhelper.KEY_ID, dbhelper.KEY_NOTE, dbhelper.KEY_DATE, dbhelper.KEY_IS_REMINDER};
    	
    	Cursor c = database.query(dbhelper.TABLE_NOTES, allColumns,null,null,null,null,dbhelper.KEY_DATE+" DESC");
    	
        if (c != null)
            c.moveToFirst();
     
        return c;
    }
     
    // Getting All Contacts
    /*public List<Notes> getAllNotes() {
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
    }*/
     
    // Getting contacts Count
    /*public int getNotesCount() {
    	
    	String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }*/
    // Updating single contact
    public int updateNote(Notes note) {
    	ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(dbhelper.KEY_NOTE, note.getNote());
        int i = database.update(dbhelper.TABLE_NOTES, cvUpdate,
                dbhelper.KEY_ID + " = " + note.getId(), null);
        return i;
    	
    }
     
    // Deleting single contact
    public void deleteNote(Notes note) {
    	database.delete(dbhelper.TABLE_NOTES, dbhelper.KEY_ID + "="
                + note.getId(), null);
    }
    
    public Cursor getAllNotesReminder(){
    	String[] allColumns = new String[]{dbhelper.KEY_ID, dbhelper.KEY_NOTE, dbhelper.KEY_DATE, dbhelper.KEY_IS_REMINDER, dbhelper.KEY_REMINDER_DATE};
    	return database.query(dbhelper.TABLE_NOTES, allColumns,null,null,null,null,null);
    }

}
