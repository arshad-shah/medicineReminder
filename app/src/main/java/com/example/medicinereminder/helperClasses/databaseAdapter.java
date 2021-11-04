package com.example.medicinereminder.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
     * @return ArrayList<MedicineReminder> The ArrayList of the data from the database.
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
                MedicineReminder medicineReminder = new MedicineReminder(Integer.parseInt(reminderId),medicineName,dosesPerDay,numberOfDay);
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
            medicineReminder = new MedicineReminder(Integer.parseInt(reminderId),medicineName,dosesPerDay,numberOfDay);
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

        private final Context context;

        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
