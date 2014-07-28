package com.amitmerchant.notesapp;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class Add_note extends Activity implements OnClickListener {
	EditText et;
	Button add_bt, read_bt;
	SQLController dbcon;
	Boolean isReminder;
	CheckBox isReminderCheckbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);
		et = (EditText) findViewById(R.id.member_et_id);
		add_bt = (Button) findViewById(R.id.add_bt_id);

		dbcon = new SQLController(this);
		dbcon.open();
		add_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_bt_id:
			String note = et.getText().toString().trim();
			isReminderCheckbox = (CheckBox) findViewById(R.id.setReminder);
			DatePicker reminderDate = (DatePicker) findViewById(R.id.datePicker1);
			TimePicker reminderTime = (TimePicker)findViewById(R.id.timePicker1);

			int day = reminderDate.getDayOfMonth();
			int month = reminderDate.getMonth();
			int year = reminderDate.getYear();
			
			int hour = reminderTime.getCurrentHour();
		    int minute = reminderTime.getCurrentMinute();
			
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, year);
		    cal.set(Calendar.MONTH, month);
		    cal.set(Calendar.DATE, day);
		    cal.set(Calendar.HOUR_OF_DAY, hour);
		    cal.set(Calendar.MINUTE, minute);
		    cal.set(Calendar.SECOND, 0);
		    Date reminderDateTime = cal.getTime();
		    
			isReminder = isReminderCheckbox.isChecked();
			if (note.length() > 0 || !note.equals("")) {
				dbcon.addNote(new Notes(note, isReminder, reminderDateTime));
				
				//To set notification for the notes
				if(isReminder==true){
					Long systemCurrentTime = System.currentTimeMillis();
	            	Long reminderTimeNoti = reminderDateTime.getTime();
	            	
	        		Intent myIntent = new Intent(Add_note.this, MyReceiver.class);
	        		myIntent.putExtra("note_text_notification", note);
	                PendingIntent pendingIntent = PendingIntent.getBroadcast(Add_note.this, (int)(System.currentTimeMillis()/1000), myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	                
	                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	        		alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTimeNoti, pendingIntent);
				}
		            
				Intent main = new Intent(Add_note.this, MainActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(main);
			}
			break;

		default:
			break;
		}
	}

}
