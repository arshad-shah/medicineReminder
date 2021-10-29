package com.example.medicinereminder.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class databaseAdapter {
    dbHelper helper;
    public databaseAdapter(Context context)
    {
        helper = new dbHelper(context);
    }

    public long insertData(String MedicineName,int DosesPerDay, int NumberOfDay)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.MedicineName, MedicineName);
        contentValues.put(dbHelper.DosesPerDay, DosesPerDay);
        contentValues.put(dbHelper.NumberOfDay, NumberOfDay);
        return db.insert(dbHelper.TABLE_NAME, null , contentValues);
    }

    public ArrayList<MedicineReminder> readData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {dbHelper.UID,dbHelper.MedicineName,dbHelper.DosesPerDay,dbHelper.NumberOfDay};
        Cursor cursor =db.query(dbHelper.TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<MedicineReminder> medicineReminder = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int mid =cursor.getInt(cursor.getColumnIndex(dbHelper.UID));
            String MedicineName =cursor.getString(cursor.getColumnIndex(dbHelper.MedicineName));
            String  DosesPerDay =cursor.getString(cursor.getColumnIndex(dbHelper.DosesPerDay));
            String  NumberOfDay =cursor.getString(cursor.getColumnIndex(dbHelper.NumberOfDay));

            medicineReminder.add(new MedicineReminder(mid,MedicineName,DosesPerDay,NumberOfDay));
        }
        return medicineReminder;
    }

    public MedicineReminder readDataById(String mid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {dbHelper.UID,dbHelper.MedicineName,dbHelper.DosesPerDay,dbHelper.NumberOfDay};
        String[] selectionArgs ={mid};
        Cursor cursor =db.query(dbHelper.TABLE_NAME,columns,dbHelper.UID+" = ?",selectionArgs,null,null,null);
        MedicineReminder medicineReminder = null;
        while (cursor.moveToNext())
        {
            int uid =cursor.getInt(cursor.getColumnIndex(dbHelper.UID));
            String MedicineName =cursor.getString(cursor.getColumnIndex(dbHelper.MedicineName));
            String  DosesPerDay =cursor.getString(cursor.getColumnIndex(dbHelper.DosesPerDay));
            String  NumberOfDay =cursor.getString(cursor.getColumnIndex(dbHelper.NumberOfDay));

            medicineReminder = new MedicineReminder(uid,MedicineName,DosesPerDay,NumberOfDay);
        }
        return medicineReminder;
    }

    public int deleteData(String mid)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs ={mid};

        return db.delete(dbHelper.TABLE_NAME ,dbHelper.UID+" = ?",whereArgs);
    }

    public int updateMedicineName(String mid, String newName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.MedicineName,newName);
        String[] whereArgs ={mid};
        return db.update(dbHelper.TABLE_NAME,contentValues, dbHelper.UID+" = ?",whereArgs );
    }
    public int updateDoses(String mid, String newDose)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.DosesPerDay,newDose);
        String[] whereArgs ={mid};
        return db.update(dbHelper.TABLE_NAME,contentValues, dbHelper.UID+" = ?",whereArgs );
    }
    public int updateDays(String mid, String newDays)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NumberOfDay,newDays);
        String[] whereArgs ={mid};
        return db.update(dbHelper.TABLE_NAME,contentValues, dbHelper.UID+" = ?",whereArgs );
    }

    static class dbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "reminderDatabase";    // Database Name
        private static final String TABLE_NAME = "reminders";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String MedicineName = "MedicineName";    //Column II
        private static final String DosesPerDay= "DosesPerDay";    // Column III
        private static final String NumberOfDay= "NumberOfDay";    // Column IV
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MedicineName+" VARCHAR(255) ,"+ DosesPerDay+" NUMBER(2),"+ NumberOfDay+" NUMBER(4));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private final Context context;

        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
