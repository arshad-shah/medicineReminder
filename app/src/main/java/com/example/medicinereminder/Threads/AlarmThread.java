package com.example.medicinereminder.Threads;

import android.content.Context;
import android.util.Log;

import com.example.medicinereminder.Adapters.databaseAdapter;
import com.example.medicinereminder.Alarms.AlarmHelper;
import com.example.medicinereminder.ObjectClasses.Alarm;

/**
 *  This class is used to create a thread that will be used to set an alarm.
 *  It takes care of new or to renew an alarm
 */
public class AlarmThread extends Thread {
    Context context;
    String oldMedicineName;
    String name;
    String dosesPerDay;
    String daysToUse;
    boolean newAlarm;

    /**
     * Constructor for the class AlarmThread
     * @param context the context of the application
     * @param name the name of the medicine
     * @param dosesPerDay the number of doses per day
     * @param daysToUse the days to use the medicine
     * @param newAlarm true if the alarm is new, false if it is to be renewed
     * @see AlarmHelper
     */
    public AlarmThread(Context context, String name, String dosesPerDay, String daysToUse, boolean newAlarm) {
        this.context = context;
        this.name = name;
        this.dosesPerDay = dosesPerDay;
        this.daysToUse = daysToUse;
        this.newAlarm=newAlarm;
    }

    /**
     * Constructor for the class AlarmThread that is used when the reminder data is updated
     * @param context the context of the application
     * @param oldMedicineName the old name of medicine
     * @param name the name of the medicine
     * @param dosesPerDay the number of doses per day
     * @param daysToUse the days to use the medicine
     * @param newAlarm true if the alarm is new, false if it is to be renewed
     * @see AlarmHelper
     */
    public AlarmThread(Context context, String oldMedicineName, String name, String dosesPerDay, String daysToUse,
            boolean newAlarm) {
        this.context = context;
        this.name = name;
        this.dosesPerDay = dosesPerDay;
        this.daysToUse = daysToUse;
        this.newAlarm = newAlarm;
        this.oldMedicineName = oldMedicineName;
    }


    public void run() {
        if (!newAlarm) {
            AlarmHelper.cancelTheAlarm(context, oldMedicineName, dosesPerDay, daysToUse);
            //adapter for the database
            databaseAdapter helper = new databaseAdapter(context);
            //get alarm object from database by medicine name using getAlarmData
            Alarm alarmByMedicineName = helper.getAlarmData(oldMedicineName);
            //delete the alarm from the database
            boolean isDeleteAlarmDataSuccessful = helper.deleteAlarmData(alarmByMedicineName.getAlarmId());
            if(isDeleteAlarmDataSuccessful){
                Log.i("Alarm", "Alarm deleted in thread after cancel");
            }
            else{
                Log.i("Alarm", "Alarm not deleted in thread after cancel");
            }
        }
        AlarmHelper.createAlarm(context,name, dosesPerDay, daysToUse);
        name = "";
        dosesPerDay = "";
        daysToUse = "";
        newAlarm = true;
    }
}