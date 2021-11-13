package com.example.medicinereminder.Alarms;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getBroadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.medicinereminder.ObjectClasses.Alarm;
import com.example.medicinereminder.Recievers.ReminderReciever;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;
import com.example.medicinereminder.Adapters.databaseAdapter;

import java.util.ArrayList;

/**
 * This class is used to set and cancel alarms.
 * @author Arshad shah
 */
public class AlarmHelper {
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
            Log.i("Alarm", "Alarm created successfully");
        }
        else{
            Log.i("Alarm", "Creating Alarm failed!!");
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



    /**
     * create the alarm
     * @param nameOfMedicine the name of the medicine
     * @param dosesPerDay the doses per day
     * @param numberOfDays the number of days
     */
    public static void createAlarm(Context context, String nameOfMedicine, String dosesPerDay, String numberOfDays){
        //amount of milliseconds in a day
        long millisInADay = 86400000;
        //millisInADay divide by dosesPerDay
        long interval = millisInADay / Integer.parseInt(dosesPerDay);

        ArrayList<String> notificationData = new ArrayList<>();
        notificationData.add(nameOfMedicine);
        notificationData.add(dosesPerDay);
        notificationData.add(numberOfDays);
        notificationData.add("Time For your medicine " + nameOfMedicine);

        //create a repeating alarm that repeats using the interval of dosage
        long firstAlarm = System.currentTimeMillis()+interval;
        //random number between 0 and timeToAlarm
        int randomNumber = (int) (Math.random() * interval);
        AlarmHelper.setRepeatingAlarm(context,randomNumber, firstAlarm,interval,notificationData);
    }

    /**
     * cancel the alarm
     * @param medicineName the name of the medicine
     * @param dosesPerDay   the doses per day
     * @param numberOfDays  the number of days
     */
    public static void cancelTheAlarm(Context context, String medicineName, String dosesPerDay, String numberOfDays) {
        databaseAdapter helper = new databaseAdapter(context);
        //recreate the pending intent used to setup the alarm
        //this is needed to cancel the alarm when the reminder is deleted
        //create an intent to the alarm receiver
        Intent recreatedIntent = new Intent(context, ReminderReciever.class);
        recreatedIntent.putExtra("name", medicineName);
        recreatedIntent.putExtra("doses", dosesPerDay);
        recreatedIntent.putExtra("days", numberOfDays);
        recreatedIntent.putExtra("desc", "Time For your medicine " + medicineName);
        //get alarm object from database by medicine name using getAlarmData
        Alarm alarmByMedicineName = helper.getAlarmData(medicineName);
        //create a pending intent to send the intent
        PendingIntent recreatedPendingIntent = getBroadcast(context.getApplicationContext(), Integer.parseInt(alarmByMedicineName.getPendingIntentCode()), recreatedIntent,
                FLAG_UPDATE_CURRENT);
        //cancel alarm
        AlarmHelper.cancelAlarm(context, recreatedPendingIntent);
    }
}
