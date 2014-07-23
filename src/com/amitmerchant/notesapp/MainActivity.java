package com.amitmerchant.notesapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private ListView lvList;
	EditText ed;
	Button bt;
	final DatabaseHandler db = new DatabaseHandler(this);
	SQLController dbcon;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	int position = 0;
	String finalDateText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbcon = new SQLController(this);
		dbcon.open();
		
		bt = (Button) findViewById(R.id.btn);
		lvList = (ListView) findViewById(R.id.listView1);
		registerForContextMenu(lvList);
		
		bt.setOnClickListener(new OnClickListener() {
		       
		            @Override
		            public void onClick(View v) {
		                   Intent add_note = new Intent(MainActivity.this, Add_note.class);
		                   startActivity(add_note);
		           }
		 });

		cursor = dbcon.readNote();
        String[] from = new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NOTE, DatabaseHandler.KEY_DATE };
        int[] to = new int[] { R.id.note_id, R.id.note_text, R.id.note_date };

        adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.view_note_entry, cursor, from, to);

        adapter.setViewBinder(new ViewBinder(){
        	public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                if (aColumnIndex == aCursor.getColumnIndex(DatabaseHandler.KEY_DATE)) {
                        String createDate = (String)aCursor.getString(aCursor.getColumnIndex(DatabaseHandler.KEY_DATE));
                        TextView textView = (TextView) aView;
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        Date finalDate;
                        
                        try {
                        	finalDate = (Date)formatter.parse(createDate);
                    		finalDateText = (String)formatter.format(finalDate);
                    	} catch (ParseException e) {
                    		e.printStackTrace();
                    	}
                        textView.setText(finalDateText);
                        return true;
                 }

                 return false;
            }
        });
        
        adapter.notifyDataSetChanged();
        lvList.setEmptyView(findViewById(android.R.id.empty));
        lvList.setAdapter(adapter);
		
     // OnCLickListiner For List Items
        lvList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                TextView note_id = (TextView) view.findViewById(R.id.note_id);
                TextView note_text = (TextView) view.findViewById(R.id.note_text);

                String note_id_val = note_id.getText().toString();
                String note_text_val = note_text.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        Modify_note.class);
                modify_intent.putExtra("noteText", note_text_val);
                modify_intent.putExtra("noteID", note_id_val);
                startActivity(modify_intent);
            }
        });

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
		menu.setHeaderTitle("Choose Action");   // Context-menu title
        menu.add(0, 1, 0, "Delete");
        menu.add(0, 2, 1, "Copy Note");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
    public boolean onContextItemSelected(MenuItem item) {
    	
        super.onContextItemSelected(item);

        if(item.getTitle().equals("Delete")) {
        	final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            
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
	            	//Add code
	            	position = (int)info.id;  
	                //Notes note_id = (Notes)adapter.getNote(info.position);
	                dbcon.deleteNote(new Notes(position));
	                cursor.requery();	
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
            
            //this.adapter.notifyDataSetChanged();  
        }else if(item.getTitle().equals("Copy Note")){ // Added copy to clip board support
        	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            TextView textView = (TextView) (info.targetView).findViewById(R.id.note_text);
            String textTocopy = (String)textView.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
                ClipData clip = ClipData.newPlainText("simple text",textTocopy);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied.",
    					Toast.LENGTH_SHORT).show();
            }else{
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(textTocopy);
                Toast.makeText(getApplicationContext(), "Copied.",
    					Toast.LENGTH_SHORT).show();	
            }

        }
		return true;
	};
	
}
