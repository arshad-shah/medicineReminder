package com.example.medicinereminder.ObjectClasses;

public class Alarm {
    /**
     * The id of the alarm.
     */
    private int alarmId;

    /**
     * The time of the alarm.
     */
    private String alarmTime;

    /**
     * The medicine name of the alarm.
     */
    private String medicineNameAlarm;

    /**
     * The doses per day of the alarm.
     */
    private String dosesPerDay;

    /**
     * The number of day of the alarm.
     */
    private String numberOfDay;

    /**
     * The boolean value of the alarm status.
     */
    private String isAlarmOn;

    /**
     * The pending intent code of the alarm.
     */
    private String pendingIntentCode;

    /**
     * The constructor of the alarm.
     * @param alarmId The id of the alarm.
     * @param alarmTime The time of the alarm.
     * @param medicineNameAlarm The medicine name of the alarm.
     * @param dosesPerDay The doses per day of the alarm.
     * @param numberOfDay The number of day of the alarm.
     * @param isAlarmOn The boolean value of the alarm status.
     * @param pendingIntentCode The pending intent code of the alarm.
     */
    public Alarm(int alarmId, String alarmTime, String medicineNameAlarm, String dosesPerDay, String numberOfDay, String isAlarmOn, String pendingIntentCode) {
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.medicineNameAlarm = medicineNameAlarm;
        this.dosesPerDay = dosesPerDay;
        this.numberOfDay = numberOfDay;
        this.isAlarmOn = isAlarmOn;
        this.pendingIntentCode = pendingIntentCode;
    }


    /**
     * The getter of the id of the alarm.
     * @return The id of the alarm.
     */
    public int getAlarmId() {
        return alarmId;
    }

    /**
     * The setter of the id of the alarm.
     * @param alarmId The id of the alarm.
     */
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    /**
     * The getter of the time of the alarm.
     * @return The time of the alarm.
     */
    public String getAlarmTime() {
        return alarmTime;
    }

    /**
     * The setter of the time of the alarm.
     * @param alarmTime The time of the alarm.
     */
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * The getter of the medicine name of the alarm.
     * @return The medicine name of the alarm.
     */
    public String getMedicineNameAlarm() {
        return medicineNameAlarm;
    }

    /**
     * The setter of the medicine name of the alarm.
     * @param medicineNameAlarm The medicine name of the alarm.
     */
    public void setMedicineNameAlarm(String medicineNameAlarm) {
        this.medicineNameAlarm = medicineNameAlarm;
    }

    /**
     * The getter of the doses per day of the alarm.
     * @return The doses per day of the alarm.
     */
    public String getDosesPerDay() {
        return dosesPerDay;
    }

    /**
     * The setter of the doses per day of the alarm.
     * @param dosesPerDay The doses per day of the alarm.
     */
    public void setDosesPerDay(String dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }

    /**
     * The getter of the number of day of the alarm.
     * @return The number of day of the alarm.
     */
    public String getNumberOfDay() {
        return numberOfDay;
    }

    /**
     * The setter of the number of day of the alarm.
     * @param numberOfDay The number of day of the alarm.
     */
    public void setNumberOfDay(String numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    /**
     * The getter of the boolean value of the alarm status.
     * @return The boolean value of the alarm status.
     */
    public String isAlarmOn() {
        return isAlarmOn;
    }

    /**
     * The setter of the boolean value of the alarm status.
     * @param isAlarmOn The String value of the alarm status.
     */
    public void setAlarmOn(String isAlarmOn) {
        this.isAlarmOn = isAlarmOn;
    }

    /**
     * The getter of the pending intent code of the alarm.
     * @return The pending intent code of the alarm.
     */
    public String getPendingIntentCode() {
        return pendingIntentCode;
    }

    /**
     * The setter of the pending intent code of the alarm.
     * @param pendingIntentCode The pending intent code of the alarm.
     */
    public void setPendingIntentCode(String pendingIntentCode) {
        this.pendingIntentCode = pendingIntentCode;
    }
}
