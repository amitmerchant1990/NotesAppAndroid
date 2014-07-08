package com.amitmerchant.notesapp;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	ListView lvList;
	EditText ed;
	Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final DatabaseHandler db = new DatabaseHandler(this);
		
		ed = (EditText) findViewById(R.id.edt);
		bt = (Button) findViewById(R.id.btn);
		lvList = (ListView) findViewById(R.id.listView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1);

		List<Notes> note = db.getAllNotes(); 
		
		for (Notes cn : note) {
            String note_text = cn.getNote();
                // Writing Contacts to log
            adapter.insert(note_text,0);
            adapter.notifyDataSetChanged();
        }
		
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = ed.getText().toString();
				if (str != null){
					db.addNote(new Notes(str));
					adapter.insert(str,0);
					ed.setText("");
					Toast.makeText(getApplicationContext(), "successfully added",
					Toast.LENGTH_SHORT).show();
					adapter.notifyDataSetChanged();
				}
			}
		});
		lvList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	/*
	 * public void addNote(View v){
	 * 
	 * /*AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this)
	 * .setTitle("SHOW FIELDS") .setMessage(note_text)
	 * .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { // do nothing
	 * closeContextMenu(); } }); alert.show();
	 * 
	 * }
	 */
}