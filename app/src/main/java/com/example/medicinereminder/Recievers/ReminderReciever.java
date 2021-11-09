package com.example.medicinereminder.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.medicinereminder.helperClasses.Message;
import com.example.medicinereminder.helperClasses.NotificationHelper;

public class ReminderReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //recieve the values from Main Activity
        String title = intent.getStringExtra("name");
        String doses = intent.getStringExtra("doses");
        String days = intent.getStringExtra("days");
        String desc = intent.getStringExtra("desc");
        long time_of_alarm = intent.getLongExtra("time", 0);

        long current_time = System.currentTimeMillis();
        long diff = current_time - time_of_alarm;
        long graceP = 60000 * 1;

        Log.i("timeOfAlarm", String.valueOf(time_of_alarm));

        //random number
        int random = (int) (Math.random() * 10000);

       if(diff < graceP){
            //create a notification
            NotificationHelper.createNotification(context,title+"_channel",desc,title,random);
            Log.i("Alarm", "Time for Medicine Alarm executed");
       }
       else{
           Log.i("Alarm", "Time for Medicine has passed");
       }
    }
}