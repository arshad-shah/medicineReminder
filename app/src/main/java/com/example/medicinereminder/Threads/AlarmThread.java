package com.example.medicinereminder.Threads;

import android.content.Context;

import com.example.medicinereminder.Alarms.AlarmHelper;

/**
 *  This class is used to create a thread that will be used to set an alarm.
 *  It takes care of new or to renew an alarm
 */
public class AlarmThread extends Thread {
    Context context;
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

    public void run() {
        if (!newAlarm) {
            AlarmHelper.cancelTheAlarm(context, name, dosesPerDay, daysToUse);
        }
        AlarmHelper.createAlarm(context,name, dosesPerDay, daysToUse);
        name = "";
        dosesPerDay = "";
        daysToUse = "";
        newAlarm = true;
    }
}