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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicinereminder.helperClasses.MedicineReminder;
import com.example.medicinereminder.helperClasses.Message;
import com.example.medicinereminder.helperClasses.databaseAdapter;

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
        final TextView numberOfDaysDisplay = findViewById(R.id.numberOfDaysDisplay);
        final ImageButton deleteReminder = findViewById(R.id.deleteReminder);
        final ImageButton editReminder = findViewById(R.id.editReminder);


        //get the values to populate the form to ensure correct values are changed
        //it also facilitates that the values not changed are not altered
        medicineName.setText(medicineNameReceived);
        dosesPerDayDisplay.setText(dosesPerDayReceived);
        numberOfDaysDisplay.setText(numberOfDaysReceived);

        checkBoxGenerator(dosesPerDayReceived,idReceived,medicineName,dosesPerDayDisplay,numberOfDaysDisplay);

        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                deleteReminder.startAnimation(expandIn);


                // Create the object of
                // AlertDialog Builder class

                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDetails.this);
                LayoutInflater inflater = ReminderDetails.this.getLayoutInflater();

                final View deleteDialog = inflater.inflate(R.layout.deleteconfirmation, null);
                final Button confirmDelete = deleteDialog.findViewById(R.id.confirmDelete);
                final Button cancelBtn = deleteDialog.findViewById(R.id.dialogCancel);

                builder.setView(deleteDialog);

                // Set Cancelable false
                builder.setCancelable(false);
                // Create the Alert dialog
                final AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();

                confirmDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

                        alertDialog.cancel();
                        finish();
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

        editReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                editReminder.startAnimation(expandIn);



                // Create the object of
                // AlertDialog Builder class

                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDetails.this);
                LayoutInflater inflater = ReminderDetails.this.getLayoutInflater();

                final View reminderEditDialog = inflater.inflate(R.layout.medicine_input, null);
                final Button submitBtn = reminderEditDialog.findViewById(R.id.dialogSubmit);
                final Button cancelBtn = reminderEditDialog.findViewById(R.id.dialogCancel);
                final EditText medicineNameDialog = reminderEditDialog.findViewById(R.id.medicineName);
                final EditText dosesInADayDialog = reminderEditDialog.findViewById(R.id.dosesInADay);
                final EditText numberOfDaysDialog = reminderEditDialog.findViewById(R.id.numberOfDays);
                final TextView heading = reminderEditDialog.findViewById(R.id.heading);
                heading.setText(R.string.editReminder);

                builder.setView(reminderEditDialog);

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
                        numberOfDaysDisplay.setText(reminderRefresh.getNumberOfDays());

                        checkBoxGenerator(dosesPerDayString,idReceived,medicineName,dosesPerDayDisplay,numberOfDaysDisplay);
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

    public void checkBoxGenerator(String dosesPerDayReceived,
                                  int idReceived,
                                  TextView medicineName,
                                  TextView dosesPerDayDisplay,
                                  TextView numberOfDaysDisplay){

        MedicineReminder reminderRefresh = helper.readDataById(String.valueOf(idReceived));
        String dosesPerDayString = reminderRefresh.getDosesPerDay();

        //dynamically add checkboxes based on doses in a day
        final LinearLayout checkboxCard = findViewById(R.id.checkboxForDoses);
        //add the number of checkboxes for doses per the doses number
        for (int i = 0; i < Integer.parseInt(dosesPerDayReceived); i++) {
            CheckBox numberOfDaysCheckBox = new CheckBox(this);
            numberOfDaysCheckBox.setPadding(8, 8, 8, 8);
            numberOfDaysCheckBox.setTextSize(18);
            numberOfDaysCheckBox.setText("Dose " + (i+1));

            numberOfDaysCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        //get the row with the id supplied
                        int doses = Integer.parseInt(dosesPerDayString);
                        doses = doses - 1;

                        int a= helper.updateDoses( String.valueOf(idReceived), String.valueOf(doses));
                        if(a<=0)
                        {
                            Message.message(getApplicationContext(),"Unsuccessful");
                        } else {
                            Message.message(getApplicationContext(),"Update to doses successful");
                            checkboxCard.removeView(numberOfDaysCheckBox);

                            medicineName.setText(reminderRefresh.getMedicineName());
                            dosesPerDayDisplay.setText(String.valueOf(doses));
                            numberOfDaysDisplay.setText(reminderRefresh.getNumberOfDays());
                        }
                    }
                }
            });
            checkboxCard.addView(numberOfDaysCheckBox);
        }

        //if doses are 0 change title
        int doses = Integer.parseInt(dosesPerDayString);
        final TextView dosesCheckboxTitle = findViewById(R.id.dosesTitle);
        if(doses == 0){
            dosesCheckboxTitle.setText(R.string.dosesComplete);
        }
        else{
            dosesCheckboxTitle.setText(R.string.dosesleft);
        }
    }
}