package com.amitmerchant.notesapp;

public class Notes {
	// private variables
	int _id;
	String _note;
	String _note_date;

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
	
	public int getId(){
		return this._id;
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public String getNote(){
		return this._note;
	}
	
	public void setNote(String note){
		this._note = note;
	}
}
