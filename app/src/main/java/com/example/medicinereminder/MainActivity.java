package com.example.medicinereminder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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

import com.example.medicinereminder.Alarms.AlarmHelper;
import com.example.medicinereminder.Adapters.CustomAdapter;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;
import com.example.medicinereminder.Notifications.Message;
import com.example.medicinereminder.Notifications.NotificationHelper;
import com.example.medicinereminder.Threads.AlarmThread;
import com.example.medicinereminder.helperClasses.Validation;
import com.example.medicinereminder.Adapters.databaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * This class is the main activity of the application. It contains the list of all the medicine reminders created
 * by the user.
 * @author Arshad Shah
 */
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
        Objects.requireNonNull(getSupportActionBar()).hide();

        //instantiate adapter
        helper = new databaseAdapter(this);

        //update the ui
        updateUI();

        //the fab button to create the reminders
        final FloatingActionButton fab = findViewById(R.id.addReminder);

        //when the fab button is clicked
        fab.setOnClickListener(view -> {
            //create a dialog to add the reminder
            createDialog();
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

        //if there are no reminders
        if (adapter.getCount() == 0) {
            //show the card
            nullStatusCard.setVisibility(VISIBLE);
            //hide the list
            list.setVisibility(GONE);
        } else {
            //hide the card
            nullStatusCard.setVisibility(GONE);
            //show the list
            list.setVisibility(VISIBLE);
        }

        list.setAdapter(adapter);

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            MedicineReminder clickedItem = (MedicineReminder) adapterView.getItemAtPosition(i);
            Intent navigateToReminderActivity = new Intent(MainActivity.this, ReminderDetails.class);
            navigateToReminderActivity.putExtra("reminderId", clickedItem.getReminderId());
            navigateToReminderActivity.putExtra("nameOfMedicine", clickedItem.getMedicineName());
            navigateToReminderActivity.putExtra("dosesPerDay", clickedItem.getDosesPerDay());
            navigateToReminderActivity.putExtra("numberOfDays", clickedItem.getNumberOfDay());
            startActivity(navigateToReminderActivity);
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

        submitBtn.setOnClickListener(view -> {

            //get the data from the dialog
            String name = medicineName.getText().toString();
            String dosesPerDay = dosesInADay.getText().toString();
            String daysToUse = numberOfDays.getText().toString();

            //error check to ensure values are entered
            areAllFieldsFilled = Validation.CheckAllFields(medicineName, dosesInADay, numberOfDays);

            //if all fields are filled
            if (areAllFieldsFilled) {
                boolean isInsertSuccessful = helper.insertData(name, dosesPerDay, daysToUse);
                //if the insert was successful
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

                // create alarm thread and start it
                AlarmThread newAlarmThread = new AlarmThread(MainActivity.this,name, dosesPerDay, daysToUse,true);
                newAlarmThread.start();
                alertDialog.cancel();
            }
        });
        cancelBtn.setOnClickListener(view -> {
            alertDialog.cancel();
            //show toast that remainder creation was cancelled
            Message.messageRed(MainActivity.this, "Reminder Cancelled");
        });
    }
}