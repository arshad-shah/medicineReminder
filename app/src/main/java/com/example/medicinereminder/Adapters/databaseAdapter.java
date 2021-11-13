package com.example.medicinereminder.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.medicinereminder.ObjectClasses.Alarm;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;

import java.util.ArrayList;

/***
 * This class is used to create the database and to insert, update and delete data from the database.
 * @author  Arshad shah
 */
public class databaseAdapter {
    
    dbHelper helper;

    public databaseAdapter(Context context)
    {
        helper = new dbHelper(context);
    }

    //open the database
    public SQLiteDatabase open()
    {
        return helper.getWritableDatabase();
    }

    //close the database
    public void close()
    {
        helper.close();
    }

    /**
     * This method is used to insert data into the database.
     * @param MedicineName the name of the medicine.
     * @param DosesPerDay the number of doses per day.
     * @param NumberOfDay the number of days to take the medicine.
     * @return true if successful false if not.
     */
    public boolean insertData(String MedicineName, String DosesPerDay, String NumberOfDay)
    {
        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.MEDICINE_NAME, MedicineName);
        contentValues.put(dbHelper.DOSES_PER_DAY, DosesPerDay);
        contentValues.put(dbHelper.NUMBER_OF_DAY, NumberOfDay);

        long result = db.insert(dbHelper.TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /**
     * This method is used to read all the data from the database.
     * and return an ArrayList of type Medicine Reminder Objects.
     * @return An ArrayList of MedicineReminder objects
     */
    public ArrayList<MedicineReminder> readAllData() {

        ArrayList<MedicineReminder> medicineReminders = new ArrayList<>();

        SQLiteDatabase db = open();

        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME, null);

        if (cursor.moveToFirst()) {

            do {
                String reminderId = cursor.getString(cursor.getColumnIndex(dbHelper.UID));
                String medicineName = cursor.getString(cursor.getColumnIndex(dbHelper.MEDICINE_NAME));
                String dosesPerDay = cursor.getString(cursor.getColumnIndex(dbHelper.DOSES_PER_DAY));
                String numberOfDay = cursor.getString(cursor.getColumnIndex(dbHelper.NUMBER_OF_DAY));
                MedicineReminder medicineReminder = new MedicineReminder(Integer.parseInt(reminderId), medicineName,
                        dosesPerDay, numberOfDay);
                medicineReminders.add(medicineReminder);
            } while (cursor.moveToNext());

        }
        
        cursor.close();

        return medicineReminders;
    }

    /**
     * This Method reads data by the id of the reminder given to it.
     * returns a MedicineReminder Object.
     * @param id the id of the reminder.
     * @return MedicineReminder The object of the reminder.
     */
    public MedicineReminder readDataById(int id) {

        MedicineReminder medicineReminder = null;

        SQLiteDatabase db = open();

        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.UID + " = " + id,
                null);

        if (cursor.moveToFirst()) {

            String reminderId = cursor.getString(cursor.getColumnIndex(dbHelper.UID));
            String medicineName = cursor.getString(cursor.getColumnIndex(dbHelper.MEDICINE_NAME));
            String dosesPerDay = cursor.getString(cursor.getColumnIndex(dbHelper.DOSES_PER_DAY));
            String numberOfDay = cursor.getString(cursor.getColumnIndex(dbHelper.NUMBER_OF_DAY));
            medicineReminder = new MedicineReminder(Integer.parseInt(reminderId), medicineName, dosesPerDay,
                    numberOfDay);
        }

        cursor.close();

        return medicineReminder;
    }
    
    /**
     * This method reads data by name of medicine from the reminder table
     * @param medicineName the name of the medicine.
     * @return MedicineReminder The object of the reminder.
     */
    public MedicineReminder readDataByName(String medicineName) {

        MedicineReminder medicineReminder = null;

        SQLiteDatabase db = open();

        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.MEDICINE_NAME + " = '"
                + medicineName + "'", null);

        if (cursor.moveToFirst()) {
            String reminderId = cursor.getString(cursor.getColumnIndex(dbHelper.UID));
            String dosesPerDay = cursor.getString(cursor.getColumnIndex(dbHelper.DOSES_PER_DAY));
            String numberOfDay = cursor.getString(cursor.getColumnIndex(dbHelper.NUMBER_OF_DAY));
            medicineReminder = new MedicineReminder(Integer.parseInt(reminderId), medicineName, dosesPerDay,
                    numberOfDay);
        }
        cursor.close();

        return medicineReminder;
    }
    

    /**
     * This method is used to delete the data from the database.
     * based on the id given to it.
     * @param id the id of the reminder.
     * @return true if successful false if not.
     */
    public boolean deleteData(int id) {

        SQLiteDatabase db = open();

        long result = db.delete(dbHelper.TABLE_NAME, dbHelper.UID + " = " + id, null);

        return result != -1;
    }

    /**
     * This method is used to delete all the data from the database.
     * @return true if successful false if not.
     */
    public boolean deleteAllData() {

        SQLiteDatabase db = open();

        long result = db.delete(dbHelper.TABLE_NAME, null, null);

        return result != -1;
    }

    /**
     * This method is used to update the data in the database.
     * @param id the id of the reminder.
     * @param MedicineName the name of the medicine.
     * @param DosesPerDay the number of doses per day.
     * @param NumberOfDay the number of days to take the medicine.
     * @return true if successful false if not.
     */
    public boolean updateData(int id, String MedicineName, String DosesPerDay, String NumberOfDay) {

        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.MEDICINE_NAME, MedicineName);

        contentValues.put(dbHelper.DOSES_PER_DAY, DosesPerDay);

        contentValues.put(dbHelper.NUMBER_OF_DAY, NumberOfDay);

        long result = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.UID + " = " + id, null);

        return result != -1;
    }

    /**
     * This method is used to update the MedicineName in the database.
     * based on id given to it.
     * @param id the id of the reminder.
     * @param MedicineName the name of the medicine.
     * @return true if successful false if not.
     */
    public boolean updateMedicineName(int id, String MedicineName) {

        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.MEDICINE_NAME, MedicineName);

        long result = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.UID + " = " + id, null);

        return result != -1;
    }

    /**
     * This method is used to update the DosesPerDay in the database.
     * based on id given to it.
     * @param id the id of the reminder.
     * @param DosesPerDay the number of doses per day.
     * @return true if successful false if not.
     */
    public boolean updateDosesPerDay(int id, String DosesPerDay) {

        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.DOSES_PER_DAY, DosesPerDay);

        long result = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.UID + " = " + id, null);

        return result != -1;
    }

    /**
     * This method is used to update the NumberOfDay in the database.
     * based on id given to it.
     * @param id the id of the reminder.
     * @param NumberOfDay the number of days to take the medicine.
     * @return true if successful false if not.
     */
    public boolean updateNumberOfDay(int id, String NumberOfDay) {

        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.NUMBER_OF_DAY, NumberOfDay);

        long result = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.UID + " = " + id, null);

        return result != -1;
    }

    /**
     * This method is used to insert data into alarm table
     * @param alarmTime the time of the alarm.
     * @param medicineName the name of the medicine.
     * @param dosesPerDay the number of doses per day.
     * @param numberOfDay the number of days to take the medicine.
     * @param isAlarmOn the status of the alarm.
     * @param pendingIntentCode the pending intent of the alarm.
     * @return true if successful false if not.
     */
    public boolean insertAlarmData(String alarmTime, String medicineName, String dosesPerDay,
            String numberOfDay, String isAlarmOn, String pendingIntentCode) {

        SQLiteDatabase db = open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ALARM_TIME, alarmTime);
        contentValues.put(dbHelper.ALARM_MEDICINE_NAME, medicineName);
        contentValues.put(dbHelper.ALARM_DOSES_PER_DAY, dosesPerDay);
        contentValues.put(dbHelper.ALARM_NUMBER_OF_DAY, numberOfDay);
        contentValues.put(dbHelper.ALARM_IS_ACTIVE, isAlarmOn);
        contentValues.put(dbHelper.ALARM_PENDING_INTENT, pendingIntentCode);

        long result = db.insert(dbHelper.TABLE_NAME_ALARM, null, contentValues);

        return result != -1;
    }
    
    /**
     * This method is used to get the data from the alarm table.
     * using the name of medicine
     * @param medicineName the name of the medicine.
     * @return the alarm object.
     */
    public Alarm getAlarmData(String medicineName) {

        SQLiteDatabase db = open();

        Alarm alarm = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME_ALARM + " WHERE "
                + dbHelper.ALARM_MEDICINE_NAME + " = '" + medicineName + "'", null);

        if (cursor.moveToFirst()) {

            String alarmId = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_ID));
            String alarmTime = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_TIME));
            String medicineNameAlarm = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_MEDICINE_NAME));
            String dosesPerDay = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_DOSES_PER_DAY));
            String numberOfDay = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_NUMBER_OF_DAY));
            String isAlarmOn = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_IS_ACTIVE));
            String pendingIntentCode = cursor.getString(cursor.getColumnIndex(dbHelper.ALARM_PENDING_INTENT));

            alarm = new Alarm(Integer.parseInt(alarmId), alarmTime, medicineNameAlarm, dosesPerDay, numberOfDay,
                    isAlarmOn, pendingIntentCode);
        }

        cursor.close();

        return alarm;
    }
    
    /**
     * This method is used to delete data from the alarm table using the id.
     * @param id the id of the alarm.
     * @return true if successful false if not.
     */
    public boolean deleteAlarmData(int id) {

        SQLiteDatabase db = open();

        long result = db.delete(dbHelper.TABLE_NAME_ALARM, dbHelper.ALARM_ID + " = " + id, null);

        return result != -1;
    }

    //Database helper for Reminder Database
    static class dbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Reminder.db";
        
        private static final String TABLE_NAME = "Reminder";
        private static final String UID = "id";
        private static final String MEDICINE_NAME = "MedicineName";
        private static final String DOSES_PER_DAY = "DosesPerDay";
        private static final String NUMBER_OF_DAY = "NumberOfDay";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MEDICINE_NAME + " TEXT," + DOSES_PER_DAY + " TEXT," + NUMBER_OF_DAY + " TEXT)";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        
        //table for the alarm intents
        private static final String TABLE_NAME_ALARM = "Alarm";
        private static final String ALARM_ID = "AlarmId";
        private static final String ALARM_TIME = "AlarmTime";
        private static final String ALARM_MEDICINE_NAME = "AlarmMedicineName";
        private static final String ALARM_DOSES_PER_DAY = "AlarmDosesPerDay";
        private static final String ALARM_NUMBER_OF_DAY = "AlarmNumberOfDay";
        private static final String ALARM_IS_ACTIVE = "AlarmIsActive";
        private static final String ALARM_PENDING_INTENT = "AlarmPendingIntent";

        //create table for the alarm
        private static final String CREATE_TABLE_ALARM = "CREATE TABLE " + TABLE_NAME_ALARM + "(" + ALARM_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ALARM_TIME + " TEXT," + ALARM_MEDICINE_NAME + " TEXT,"
                + ALARM_DOSES_PER_DAY + " TEXT," + ALARM_NUMBER_OF_DAY + " TEXT," + ALARM_IS_ACTIVE + " BOOLEAN,"
                + ALARM_PENDING_INTENT + " TEXT)";
        
        
        private static final String DROP_TABLE_ALARM = "DROP TABLE IF EXISTS " + TABLE_NAME_ALARM;


        private final Context context;

        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_ALARM);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_TABLE_ALARM);
            onCreate(db);
        }
    }
}
