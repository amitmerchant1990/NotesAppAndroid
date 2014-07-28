package com.amitmerchant.notesapp;

import java.util.Date;

public class Notes {
	// private variables
	int _id;
	String _note;
	String _note_date;
	Boolean _is_note_reminder;
	Date _date_reminder;

	// Empty constructor
	public Notes() {

	}

	public Notes(int id, String _note){
		this._id = id;
		this._note = _note;
	}
	
	public Notes(String _note){
		this._note = _note;
	}
	
	public Notes(String _note, Boolean _isReminder, Date _date_reminder){
		this._note = _note;
		this._is_note_reminder = _isReminder;
		this._date_reminder = _date_reminder;
	}
	
	public Notes(int id){
		this._id = id;
	}
	
	public int getId(){
		return this._id;
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public String getNote(){
		return this._note;
	}
	
	public Boolean getReminderStatus(){
		return this._is_note_reminder;
	}
	
	public Date getReminderDate(){
		return this._date_reminder;
	}
	
	public void setNote(String note){
		this._note = note;
	}
}
