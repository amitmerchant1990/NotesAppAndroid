package com.amitmerchant.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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

		edit_bt.setOnClickListener(this);
		delete_bt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.update_bt_id:
			String note_update = et.getText().toString();
			dbcon.updateNote(new Notes(note_id, note_update));
			this.returnHome();
			break;

		case R.id.delete_bt_id:
			dbcon.deleteNote(new Notes(note_id));
			this.returnHome();
			break;
		}
	}

	public void returnHome() {

		Intent home_intent = new Intent(getApplicationContext(),
				MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(home_intent);
	}
}
