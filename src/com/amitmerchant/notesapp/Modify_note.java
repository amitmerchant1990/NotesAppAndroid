package com.amitmerchant.notesapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modify_note extends Activity implements OnClickListener {
	EditText et;
	Button edit_bt, delete_bt;

	int note_id;

	SQLController dbcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_note);

		dbcon = new SQLController(this);
		dbcon.open();

		et = (EditText) findViewById(R.id.edit_note_id);
		edit_bt = (Button) findViewById(R.id.update_bt_id);
		delete_bt = (Button) findViewById(R.id.delete_bt_id);

		Intent i = getIntent();
		String noteID = i.getStringExtra("noteID");
		String noteText = i.getStringExtra("noteText");

		note_id = Integer.parseInt(noteID);

		et.setText(noteText);
		et.setSelection(et.getText().length());
		edit_bt.setOnClickListener(this);
		delete_bt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.update_bt_id:
			String note_update = et.getText().toString().trim();
			if (note_update.length() > 0 || !note_update.equals("")) {
				dbcon.updateNote(new Notes(note_id, note_update));
				this.returnHome();
			}
			break;

		case R.id.delete_bt_id:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			 
	        // Setting Dialog Title
	        alertDialog.setTitle("Delete");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("Are you sure you want delete this note?");
	 
	        // Setting Icon to Dialog
	        //alertDialog.setIcon(R.drawable.delete);
	 
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	dbcon.deleteNote(new Notes(note_id));
	    			returnHome();	
		            // Write your code here to invoke YES event
		            Toast.makeText(getApplicationContext(), "Note deleted successfully.", Toast.LENGTH_SHORT).show();
	            }
	        });
	 
	        // Setting Negative "NO" Button
	        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            // Write your code here to invoke NO event
	            dialog.cancel();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
			break;
		}
	}

	public void returnHome() {

		Intent home_intent = new Intent(getApplicationContext(),
				MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(home_intent);
	}
}
