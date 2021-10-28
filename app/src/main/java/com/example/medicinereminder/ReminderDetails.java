package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.medicinereminder.helperClasses.MedicineReminder;
import com.example.medicinereminder.helperClasses.Message;
import com.example.medicinereminder.helperClasses.databaseAdapter;

import java.util.ArrayList;

public class ReminderDetails extends AppCompatActivity {
    //adapter for the database
    databaseAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);
        getSupportActionBar().hide();

        //instantiate adapter
        helper = new databaseAdapter(this);

        Intent intent = getIntent();
        int idReceived = intent.getIntExtra("id",0);
        String medicineNameReceived = intent.getStringExtra("nameOfMedicine");
        String dosesPerDayReceived = intent.getStringExtra("dosesPerDay");
        String numberOfDaysReceived = intent.getStringExtra("numberOfDays");


        final TextView medicineName = findViewById(R.id.nameOfMedicineDisplay);
        final TextView dosesPerDayDisplay = findViewById(R.id.dosesPerDayDisplay);
        final TextView numbertOfDaysDisplay = findViewById(R.id.numbertOfDaysDisplay);
        final ImageButton deleteReminder = findViewById(R.id.deleteReminder);
        final ImageButton editReminder = findViewById(R.id.editReminder);

        medicineName.setText(medicineNameReceived);
        dosesPerDayDisplay.setText(dosesPerDayReceived);
        numbertOfDaysDisplay.setText(numberOfDaysReceived);

        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                deleteReminder.startAnimation(expandIn);

                //instantiate adapter
                helper = new databaseAdapter(ReminderDetails.this);

                int a = helper.deleteData(String.valueOf(idReceived));
                if(a<=0)
                {
                    Message.message(ReminderDetails.this,"Unsuccessful");
                }
                else
                {
                    Message.message(ReminderDetails.this, "DELETED");
                }

                finish();
            }
        });

        editReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                editReminder.startAnimation(expandIn);



                // Create the object of
                // AlertDialog Builder class

                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDetails.this);
                LayoutInflater inflater = ReminderDetails.this.getLayoutInflater();

                final View reminderDialog = inflater.inflate(R.layout.medicine_input, null);
                final Button submitBtn = reminderDialog.findViewById(R.id.dialogSubmit);
                final Button cancelBtn = reminderDialog.findViewById(R.id.dialogCancel);
                final EditText medicineNameDialog = reminderDialog.findViewById(R.id.medicineName);
                final EditText dosesInADayDialog = reminderDialog.findViewById(R.id.dosesInADay);
                final EditText numberOfDaysDialog = reminderDialog.findViewById(R.id.numberOfDays);

                builder.setView(reminderDialog);

                // Set Cancelable false
                builder.setCancelable(false);
                // Create the Alert dialog
                final AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();

                //get the row with the id supplied
                MedicineReminder reminder = helper.readDataById(String.valueOf(idReceived));
                medicineNameDialog.setText(reminder.getMedicineName());
                dosesInADayDialog.setText(reminder.getDosesPerDay());
                numberOfDaysDialog.setText(reminder.getNumberOfDays());

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //initialize variables
                        String name = medicineNameDialog.getText().toString();
                        String dosesPerDayString = dosesInADayDialog.getText().toString();
                        String daysToUseString = numberOfDaysDialog.getText().toString();

                        if(dosesPerDayString != null || daysToUseString != null){

                            //instantiate adapter
                            helper = new databaseAdapter(ReminderDetails.this);

                            if(!name.equals(medicineNameReceived)){
                                int a= helper.updateMedicineName( String.valueOf(idReceived), name);
                                if(a<=0)
                                {
                                    Message.message(getApplicationContext(),"Unsuccessful");
                                } else {
                                    Message.message(getApplicationContext(),"Update to medicine name successful");
                                }
                            }
                            if(!dosesPerDayString.equals(dosesPerDayReceived)){
                                int a= helper.updateDoses( String.valueOf(idReceived), dosesPerDayString);
                                if(a<=0)
                                {
                                    Message.message(getApplicationContext(),"Unsuccessful");
                                } else {
                                    Message.message(getApplicationContext(),"Update to doses successful");
                                }
                            }
                            if (!numberOfDaysReceived.equals(daysToUseString)){
                                int a= helper.updateDays( String.valueOf(idReceived), daysToUseString);
                                if(a<=0)
                                {
                                    Message.message(getApplicationContext(),"Unsuccessful");
                                } else {
                                    Message.message(getApplicationContext(),"Update to days to use successful");
                                }
                            }
                        }
                        medicineNameDialog.setText("");
                        dosesInADayDialog.setText("");
                        numberOfDaysDialog.setText("");
                        alertDialog.cancel();

                        //get the row with the id supplied
                        MedicineReminder reminderRefresh = helper.readDataById(String.valueOf(idReceived));
                        medicineName.setText(reminderRefresh.getMedicineName());
                        dosesPerDayDisplay.setText(reminderRefresh.getDosesPerDay());
                        numbertOfDaysDisplay.setText(reminderRefresh.getNumberOfDays());
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });


    }
}