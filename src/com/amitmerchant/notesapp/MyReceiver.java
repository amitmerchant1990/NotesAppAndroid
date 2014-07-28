package com.amitmerchant.notesapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver
{
      
    @Override
    public void onReceive(Context context, Intent intent)
    {
       Intent service1 = new Intent(context, MyAlarmService.class);
       String notificationdata = intent.getStringExtra("note_text_notification");
       service1.putExtra("noti_data_alarm", notificationdata);
       context.startService(service1);
        
    }   
}
