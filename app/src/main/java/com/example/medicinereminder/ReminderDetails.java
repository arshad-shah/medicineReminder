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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicinereminder.helperClasses.AlarmManagerHelper;
import com.example.medicinereminder.helperClasses.MedicineReminder;
import com.example.medicinereminder.helperClasses.Message;
import com.example.medicinereminder.helperClasses.NotificationHelper;
import com.example.medicinereminder.helperClasses.databaseAdapter;

import java.util.ArrayList;

public class ReminderDetails extends AppCompatActivity {
    //adapter for the database
    databaseAdapter helper;

    //onDestroy()
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);
        getSupportActionBar().hide();

        //instantiate adapter
        helper = new databaseAdapter(this);

        Intent intent = getIntent();
        int idReceived = intent.getIntExtra("reminderId",0);
        String medicineNameReceived = intent.getStringExtra("nameOfMedicine");
        String dosesPerDayReceived = intent.getStringExtra("dosesPerDay");
        String numberOfDaysReceived = intent.getStringExtra("numberOfDays");

        final TextView medicineName = findViewById(R.id.nameOfMedicineDisplay);
        final TextView dosesPerDayDisplay = findViewById(R.id.dosesPerDayDisplay);
        final TextView numberOfDaysDisplay = findViewById(R.id.numberOfDaysDisplay);
        final ImageButton deleteReminder = findViewById(R.id.deleteReminder);
        final ImageButton editReminderButton = findViewById(R.id.editReminderButton);

        medicineName.setText(medicineNameReceived);
        dosesPerDayDisplay.setText(dosesPerDayReceived);
        numberOfDaysDisplay.setText(numberOfDaysReceived);

        checkBoxGenerator(idReceived,dosesPerDayDisplay,numberOfDaysDisplay);
        
        //show confirmation dialog using custom layout when delete button is clicked
        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                deleteReminder.startAnimation(expandIn);
                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDetails.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.deleteconfirmation, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                //delete button
                Button delete = dialogView.findViewById(R.id.confirmDelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete the Notification channel using channel id and application context
                        NotificationHelper.deleteNotificationChannel(ReminderDetails.this,
                                medicineNameReceived + "_channel");
                        
                        //delete the reminder from the database
                        boolean isDeleteDataSuccessful = helper.deleteData(idReceived);
                        if(isDeleteDataSuccessful){
                            Message.messageGreen(ReminderDetails.this,"Reminder Deleted");
                        }
                        else{
                            Message.messageRed(ReminderDetails.this,"Reminder not deleted");
                        }
                        dialog.dismiss();
                        finish();
                    }
                });

                //cancel button
                Button cancel = dialogView.findViewById(R.id.dialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        /*
          show edit reminder dialog using custom layout when edit button is clicked
          the values are populated from the database
          the values are also updated in the database after submit is pressed if the values are changed
          the values are also updated in the form after submit is pressed if the values are changed
          the values are also updated in the display after submit is pressed if the values are changed
         */
        editReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //run Animation
                Animation expandIn = AnimationUtils.loadAnimation(ReminderDetails.this,R.anim.expand_in);
                editReminderButton.startAnimation(expandIn);

                //build the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDetails.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.editreminder, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                //get the values from the database
                MedicineReminder medicineReminder = helper.readDataById(idReceived);
                String nameOfMedicine = medicineReminder.getMedicineName();
                String dosesPerDay = medicineReminder.getDosesPerDay();
                String numberOfDays = medicineReminder.getNumberOfDay();

                //get the values of edit text from the form
                final EditText nameOfMedicineEdit = dialogView.findViewById(R.id.nameOfMedicineEdit);
                final EditText dosesPerDayEdit = dialogView.findViewById(R.id.dosesPerDayEdit);
                final EditText numberOfDaysEdit = dialogView.findViewById(R.id.numberOfDaysEdit);
                final TextView heading = dialogView.findViewById(R.id.heading);
                heading.setText(R.string.editReminder);

                //populate the form with the values from the database
                nameOfMedicineEdit.setText(nameOfMedicine);
                dosesPerDayEdit.setText(dosesPerDay);
                numberOfDaysEdit.setText(numberOfDays);

                //submit button
                Button submit = dialogView.findViewById(R.id.Edit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get the values from the form
                        String nameOfMedicineEditText = nameOfMedicineEdit.getText().toString();
                        String dosesPerDayEditText = dosesPerDayEdit.getText().toString();
                        String numberOfDaysEditText = numberOfDaysEdit.getText().toString();

                        //update the values in the database if the values are changed
                        if(!nameOfMedicineEditText.equals(nameOfMedicine) || !dosesPerDayEditText.equals(dosesPerDay) || !numberOfDaysEditText.equals(numberOfDays)){
                            boolean isUpdateDataSuccessful = helper.updateData(idReceived,nameOfMedicineEditText,dosesPerDayEditText,numberOfDaysEditText);
                            if(isUpdateDataSuccessful){
                                Message.messageGreen(ReminderDetails.this,"Reminder Updated");
                            }
                            else{
                                Message.messageRed(ReminderDetails.this,"Reminder not updated");
                            }
                        }

                        //update the values in the display
                        medicineName.setText(nameOfMedicineEditText);
                        dosesPerDayDisplay.setText(dosesPerDayEditText);
                        numberOfDaysDisplay.setText(numberOfDaysEditText);

                        //generate checkboxes for the amount of doses per day
                        checkBoxGenerator(idReceived,dosesPerDayDisplay,numberOfDaysDisplay);

                        dialog.dismiss();
                    }
                });
                
                //cancel button
                Button cancel = dialogView.findViewById(R.id.dialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * generate checkboxes for the amount of doses per day
     * based on the amount of doses per day
     * @param idReceived the id of the medicine reminder
     * @param dosesPerDayDisplay the doses per day of the medicine reminder
     */
    
    public void checkBoxGenerator(int idReceived,
                                  TextView dosesPerDayDisplay, TextView numberOfDaysDisplayString) {
        //get Reminder Object from database
        MedicineReminder medicineReminder = helper.readDataById(idReceived);
        String dosesPerDay = medicineReminder.getDosesPerDay();

        /*
          Dynamically generate checkboxes for the amount of doses per day
          based on the amount of doses per day
          when a checkbox is clicked remove it from layout
          and update the database as well as the display
          update the count of doses as well
         */
        int dosesPerDayInt = Integer.parseInt(dosesPerDay);

        //if the count is 0 and there are no checkboxes change dosesTitle to dosesCompleted else change it to dosesRemaining
        final TextView dosesCheckboxTitle = findViewById(R.id.dosesTitle);
        if(dosesPerDayInt == 0){
            dosesCheckboxTitle.setText(R.string.dosesComplete);
        }
        else{
            dosesCheckboxTitle.setText(R.string.dosesRemiaining);
        }

        //remove all checkboxes from layout
        LinearLayout linearLayout = findViewById(R.id.checkboxForDoses);
        linearLayout.removeAllViews();

        //generate checkboxes for the amount of doses per day
        for (int i = 0; i < dosesPerDayInt; i++) {
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText("Dose " + (i + 1));
            checkBox.setId(i);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the id of the checkbox
                    int id = checkBox.getId();

                    //get the count of the checkboxes
                    int count = linearLayout.getChildCount();

                    //remove the checkbox from layout
                    linearLayout.removeView(checkBox);

                    //update the count of doses
                    count--;
                    dosesPerDayDisplay.setText(String.valueOf(count));

                    //update the dosesPerDay in the database
                    boolean isUpdateDosesPerDaySuccessful = helper.updateDosesPerDay(idReceived, String.valueOf(count));
                    if (isUpdateDosesPerDaySuccessful) {
                        Message.messageGreen(ReminderDetails.this, "Doses Updated");
                    } else {
                        Message.messageRed(ReminderDetails.this, "Doses not updated");
                    }

                    //get Reminder Object from database
                    MedicineReminder medicineReminder = helper.readDataById(idReceived);
                    String days = medicineReminder.getNumberOfDay();
                    //if dosesPerDayInt is 0 then minus 1 from numberOfDaysInt
                    if (dosesPerDayInt == 0) {
                        int daysInt = Integer.parseInt(days);
                        daysInt--;
                        days = String.valueOf(daysInt);
                    }

                    //if the count is 0 and there are no checkboxes change dosesTitle to dosesCompleted else change it to dosesRemaining
                    final TextView dosesCheckboxTitle = findViewById(R.id.dosesTitle);
                    if(count == 0){
                        //update the number of days in the database
                        boolean isUpdateDaysSuccessful = helper.updateNumberOfDay(idReceived, days);
                        if (isUpdateDaysSuccessful) {
                            Message.messageGreen(ReminderDetails.this, "Days Updated");
                        } else {
                            Message.messageRed(ReminderDetails.this, "Days not updated");
                        }
                        dosesCheckboxTitle.setText(R.string.dosesComplete);
                    }
                    else{
                        dosesCheckboxTitle.setText(R.string.dosesRemiaining);
                    }
                }
            });
            linearLayout.addView(checkBox);
        }
    }
}