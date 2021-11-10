package com.example.medicinereminder.helperClasses;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.Recievers.ReminderReciever;

import java.util.ArrayList;

/**
 * This class is used to set and cancel alarms.
 * @author Arshad shah
 */
public class AlarmManagerHelper {
    /**
     * This method is used to set an alarm.
     * @param context the context of the application
     * @param requestCode the alarm request code
     * @param timeToAlarm the time to alarm
     * @param interval the repeat interval
     * @param notificationData the notification data to fill the notification
    */
    public static void setRepeatingAlarm(Context context, int requestCode, long timeToAlarm, long interval, ArrayList<String> notificationData) {

        //instantiate adapter
        databaseAdapter helper = new databaseAdapter(context);
        //get the alarm service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //create an intent to the alarm receiver
        Intent intent = new Intent(context, ReminderReciever.class);
        intent.putExtra("name", notificationData.get(0));
        intent.putExtra("doses", notificationData.get(1));
        intent.putExtra("days", notificationData.get(2));
        intent.putExtra("desc", notificationData.get(3));

        //read data from reminder table using name
        MedicineReminder reminderData = helper.readDataByName(notificationData.get(0));
        //get the id of the reminder
        int reminderId = reminderData.getReminderId();
        String alarmTime = String.valueOf(interval);
        String requestCodeOfAlarmIntent = String.valueOf(requestCode);

        //create a pending intent to send the intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent,
                FLAG_UPDATE_CURRENT);


        //insert data into alarm table using insertAlarmData
        boolean isInsertSuccessful =  helper.insertAlarmData(alarmTime, notificationData.get(0), notificationData.get(1),
                notificationData.get(2), "true", requestCodeOfAlarmIntent);
        
        //if the alarm is successfully inserted into the alarm table
        if (isInsertSuccessful) {
            //set the alarm
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeToAlarm, interval, pendingIntent);
            Message.messageGreen(context, "Alarm for Notification Created");
        }
        else{
            Message.messageRed(context, "Something went wrong, Alarm failed!!");
        }
    }
    
    /**
     * cancel the alarm
     * @param pendingIntent the pending intent to cancel
     */
    public static void cancelAlarm(Context context, PendingIntent pendingIntent) {
        //get the alarm service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //cancel the alarm
        alarmManager.cancel(pendingIntent);
    }


}
