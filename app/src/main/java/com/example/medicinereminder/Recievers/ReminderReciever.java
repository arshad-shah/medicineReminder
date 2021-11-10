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

        //random number
        int random = (int) (Math.random() * 10000);
        //create a notification
        NotificationHelper.createNotification(context,title+"_channel",desc,title,random);
        Log.i("Alarm", "Time for Medicine Alarm executed");
    }
}