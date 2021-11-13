package com.example.medicinereminder.Recievers;

import static com.example.medicinereminder.Alarms.AlarmHelper.createAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.medicinereminder.Adapters.databaseAdapter;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;

import java.util.ArrayList;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED) || intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)){
            //adapter for the database
            databaseAdapter helper= new databaseAdapter(context);

            ArrayList<MedicineReminder> reminders = helper.readAllData();
            //for each reminder in helper.readAllData() call create alarm
            for(int i=0;i<reminders.size();i++){
               createAlarm(context, reminders.get(i).getMedicineName(),reminders.get(i).getDosesPerDay(), reminders.get(i).getNumberOfDay());
            }
        }
    }
}