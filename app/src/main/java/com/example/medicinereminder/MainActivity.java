package com.example.medicinereminder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.medicinereminder.helperClasses.AlarmManagerHelper;
import com.example.medicinereminder.helperClasses.CustomAdapter;
import com.example.medicinereminder.helperClasses.MedicineReminder;
import com.example.medicinereminder.helperClasses.Message;
import com.example.medicinereminder.helperClasses.NotificationHelper;
import com.example.medicinereminder.helperClasses.databaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //adapter for the database
    databaseAdapter helper;
    boolean areAllFieldsFilled = false;

    @Override
    protected void onResume() {
        super.onResume();
        //update the ui
        updateUI();
    }

    //onDestroy()
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //instantiate adapter
        helper = new databaseAdapter(this);

        //update the ui
        updateUI();

        //the fab button to create the reminders
        final FloatingActionButton fab = findViewById(R.id.addReminder);

        //when the fab button is clicked
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a dialog to add the reminder
                createDialog();
            }
        });
    }

    /***
     * update the ui with the data from the database
     */
    public void updateUI() {
        final ListView list = findViewById(R.id.ReminderList);
        final CardView nullStatusCard = findViewById(R.id.nullStatusCard);

        //get the data from the database
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.reminderlistitem, helper.readAllData());

        if (helper.readAllData().size() > 0) {
            nullStatusCard.setVisibility(GONE);
        } else {
            nullStatusCard.setVisibility(VISIBLE);
        }

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MedicineReminder clickedItem = (MedicineReminder) adapterView.getItemAtPosition(i);
                Intent navigateToReminderActivity = new Intent(MainActivity.this, ReminderDetails.class);
                navigateToReminderActivity.putExtra("reminderId", clickedItem.getReminderId());
                navigateToReminderActivity.putExtra("nameOfMedicine", clickedItem.getMedicineName());
                navigateToReminderActivity.putExtra("dosesPerDay", clickedItem.getDosesPerDay());
                navigateToReminderActivity.putExtra("numberOfDays", clickedItem.getNumberOfDay());
                startActivity(navigateToReminderActivity);
            }
        });
    }
    
    /***
     * create a dialog to add a reminder
     */
    public void createDialog() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        final View reminderDialog = inflater.inflate(R.layout.medicine_input, null);
        final Button submitBtn = reminderDialog.findViewById(R.id.dialogSubmit);
        final Button cancelBtn = reminderDialog.findViewById(R.id.dialogCancel);
        final EditText medicineName = reminderDialog.findViewById(R.id.medicineName);
        final EditText dosesInADay = reminderDialog.findViewById(R.id.dosesInADay);
        final EditText numberOfDays = reminderDialog.findViewById(R.id.numberOfDays);
        final TextView heading = reminderDialog.findViewById(R.id.heading);
        heading.setText(R.string.createReminder);
        builder.setView(reminderDialog);

        // Set Cancelable false
        builder.setCancelable(false);
        // Create the Alert dialog
        final AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialize variables
                String name = medicineName.getText().toString();
                String dosesPerDay = dosesInADay.getText().toString();
                String daysToUse = numberOfDays.getText().toString();

                //error check to ensure values are entered
                areAllFieldsFilled = CheckAllFields(medicineName, dosesInADay, numberOfDays);

                if (areAllFieldsFilled) {
                    boolean isInsertSuccessful = helper.insertData(name, dosesPerDay, daysToUse);
                    if (isInsertSuccessful) {
                        //update the ui
                        updateUI();
                        //create a channel for this reminder
                        NotificationHelper.createNotificationChannel(MainActivity.this,
                                NotificationManager.IMPORTANCE_MAX, true, name, name + "Reminder", name + "_channel");
                        //dismiss the dialog
                        alertDialog.dismiss();
                        //show Toast
                        Message.messageGreen(MainActivity.this, "Reminder Created");
                        medicineName.setText("");
                        dosesInADay.setText("");
                        numberOfDays.setText("");
                    } else {
                        Message.messageRed(MainActivity.this, "Reminder not created");
                    }


                    //amount of milliseconds in a day
                    long millisInADay = 86400000;
                    //millisInADay divide by dosesPerDay
                    long timeToAlarm = millisInADay / Integer.parseInt(dosesPerDay);

                    ArrayList<String> notificationData = new ArrayList<>();
                    notificationData.add(name);
                    notificationData.add(dosesPerDay);
                    notificationData.add(daysToUse);
                    notificationData.add("Time For your medicine " + name);

                    //create a repeating alarm that repeats using the interval of dosage
                    long firstAlarm = System.currentTimeMillis()+timeToAlarm;
                    //random number between 0 and timeToAlarm
                    int randomNumber = (int) (Math.random() * timeToAlarm);
                    AlarmManagerHelper.setRepeatingAlarm(MainActivity.this,randomNumber, firstAlarm,timeToAlarm,notificationData);
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                alertDialog.cancel();
                //show toast that remainder creation was cancelled
                Message.messageRed(MainActivity.this, "Reminder Cancelled");
            }
        });
    }

    /**
     * Form validation method to check if the field are filled in or not
     * can check any three EditText Views
     * This method only checks the length of the EditText to determine
     * if it is filled
     * The EditText View can be named anything
     * @param medicineName the first editText view
     * @param dosesInADay the second editText View
     * @param numberOfDays the third editText view
     * @return boolean true if all fields are filled false if not
     * */
    public boolean CheckAllFields(EditText medicineName, EditText dosesInADay, EditText numberOfDays) {
        if (medicineName.length() == 0) {
            medicineName.setError("Medicine name is required");
            medicineName.requestFocus();
            return false;
        }
        int doses = Integer.parseInt(dosesInADay.getText().toString());
        if (dosesInADay.length() == 0 && doses < 5) {
            dosesInADay.setError("Doses per day is required");
            dosesInADay.requestFocus();
            return false;
        }
        else if (doses > 4){
            dosesInADay.setError("Doses per day cannot exceed 4 in 24 hours");
            dosesInADay.requestFocus();
            return false;
        }

        if (numberOfDays.length() == 0) {
            numberOfDays.setError("Number Of Days is required");
            numberOfDays.requestFocus();
            return false;
        }
        // after all validation return true.
        return true;
    }
}