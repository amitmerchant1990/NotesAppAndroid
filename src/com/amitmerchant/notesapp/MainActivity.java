package com.amitmerchant.notesapp;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private ListView lvList;
	EditText ed;
	Button bt;
	ArrayAdapter<String> adapter;
	final ArrayList<String> list = new ArrayList<String>();
	final DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ed = (EditText) findViewById(R.id.edt);
		bt = (Button) findViewById(R.id.btn);
		lvList = (ListView) findViewById(R.id.listView1);
		registerForContextMenu(lvList);
		adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,list);

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
				String str = ed.getText().toString().trim();
				if (str.length() > 0|| !str.equals("")){
					db.addNote(new Notes(str));
					adapter.insert(str,0);
					ed.setText("");
					Toast.makeText(getApplicationContext(), "Note added successfully.",
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
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, 
                    View v, ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, "Delete");
        menu.add(0, 2, 1, "Copy Note");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
    public boolean onContextItemSelected(MenuItem item) {
    	int position;
        super.onContextItemSelected(item);

        if(item.getTitle().equals("Delete")) {
            //Add code
        	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();  
            position = (int)info.id;  
            //Notes note_id = (Notes)adapter.getNote(info.position);
            db.deleteNote(new Notes(position));
            
            list.remove(position);  
            this.adapter.notifyDataSetChanged();  
        }else if(item.getTitle().equals("Copy Note")){ // Added copy to clip board support
        	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            String textTocopy = ((TextView) info.targetView).getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
                ClipData clip = ClipData.newPlainText("simple text",textTocopy);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied.",
    					Toast.LENGTH_SHORT).show();
            }else{
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(textTocopy);

            }

        }
        return true;
    };

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
