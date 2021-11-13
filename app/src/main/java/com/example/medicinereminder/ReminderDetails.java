package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.medicinereminder.ObjectClasses.Alarm;
import com.example.medicinereminder.Alarms.AlarmHelper;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;
import com.example.medicinereminder.Notifications.Message;
import com.example.medicinereminder.Notifications.NotificationHelper;
import com.example.medicinereminder.Threads.AlarmThread;
import com.example.medicinereminder.helperClasses.Validation;
import com.example.medicinereminder.Adapters.databaseAdapter;

import java.util.Objects;

/**
 * This class is responsible for the details of the reminder.
 * It is also responsible for the editing of the reminder.
 * @author Arshad Shah
 */
public class ReminderDetails extends AppCompatActivity {
    //adapter for the database
    databaseAdapter helper;
    boolean areAllFieldsFilledCorrectly = false;

    //onDestroy()
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //instantiate adapter
        helper = new databaseAdapter(this);

        //get the values from the intent
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

        //set default values
        medicineName.setText(medicineNameReceived);
        dosesPerDayDisplay.setText(dosesPerDayReceived);
        numberOfDaysDisplay.setText(numberOfDaysReceived);

        //custom dynamic checkbox generator
        checkBoxGenerator(idReceived, dosesPerDayDisplay, numberOfDaysDisplay);


        
        //show confirmation dialog using custom layout when delete button is clicked
        deleteReminder.setOnClickListener(v -> {
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
            delete.setOnClickListener(v1 -> {
                //get alarm object from database by medicine name using getAlarmData
                Alarm alarmByMedicineName = helper.getAlarmData(medicineNameReceived);

                //delete the Notification channel using channel id and application context
                NotificationHelper.deleteNotificationChannel(ReminderDetails.this,
                        medicineNameReceived + "_channel");

                AlarmHelper.cancelTheAlarm(ReminderDetails.this,medicineNameReceived, dosesPerDayReceived, numberOfDaysReceived);

                //delete the alarm from the database
                boolean isDeleteAlarmDataSuccessful = helper.deleteAlarmData(alarmByMedicineName.getAlarmId());

                //delete the reminder from the database
                boolean isDeleteDataSuccessful = helper.deleteData(idReceived);

                if(isDeleteAlarmDataSuccessful && isDeleteDataSuccessful){
                    Message.messageGreen(ReminderDetails.this,"Alarm and Reminder Deleted");
                }
                else{
                    Message.messageRed(ReminderDetails.this,"Alarm and Reminder not Deleted");
                }
                dialog.dismiss();
                finish();
            });

            //cancel button
            Button cancel = dialogView.findViewById(R.id.dialogCancel);
            cancel.setOnClickListener(v12 -> dialog.dismiss());
        });

        /*
          show edit reminder dialog using custom layout when edit button is clicked
          the values are populated from the database
          the values are also updated in the database after submit is pressed if the values are changed
          the values are also updated in the form after submit is pressed if the values are changed
          the values are also updated in the display after submit is pressed if the values are changed
         */
        editReminderButton.setOnClickListener(v -> {
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
            submit.setOnClickListener(v13 -> {
                //error check to ensure values are entered
                areAllFieldsFilledCorrectly = Validation.CheckAllFields(nameOfMedicineEdit, dosesPerDayEdit, numberOfDaysEdit);
                if(areAllFieldsFilledCorrectly) {
                    //get the values from the form
                    String nameOfMedicineEditText = nameOfMedicineEdit.getText().toString();
                    String dosesPerDayEditText = dosesPerDayEdit.getText().toString();
                    String numberOfDaysEditText = numberOfDaysEdit.getText().toString();

                    //update the values in the database if the values are changed
                    if (!nameOfMedicineEditText.equals(nameOfMedicine) || !dosesPerDayEditText.equals(dosesPerDay) || !numberOfDaysEditText.equals(numberOfDays)) {
                        boolean isUpdateDataSuccessful = helper.updateData(idReceived, nameOfMedicineEditText, dosesPerDayEditText, numberOfDaysEditText);
                        if (isUpdateDataSuccessful) {
                            Message.messageGreen(ReminderDetails.this, "Reminder Updated");
                        } else {
                            Message.messageRed(ReminderDetails.this, "Reminder not updated");
                        }
                    }

                    //update the values in the display
                    medicineName.setText(nameOfMedicineEditText);
                    dosesPerDayDisplay.setText(dosesPerDayEditText);
                    numberOfDaysDisplay.setText(numberOfDaysEditText);

                    //generate checkboxes for the amount of doses per day
                    checkBoxGenerator(idReceived, dosesPerDayDisplay, numberOfDaysDisplay);

                    //get alarm object from database by medicine name using getAlarmData
                    Alarm alarmByMedicineName = helper.getAlarmData(medicineNameReceived);

                    //create an alarm with the updated values
                    AlarmThread renewAlarmThread = new AlarmThread(ReminderDetails.this,alarmByMedicineName.getMedicineNameAlarm(),nameOfMedicineEditText, dosesPerDayEditText, numberOfDaysEditText,false);
                    renewAlarmThread.start();
                    dialog.dismiss();
                }
            });

            //cancel button
            Button cancel = dialogView.findViewById(R.id.dialogCancel);
            cancel.setOnClickListener(v14 -> dialog.dismiss());
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
        String medicineName = medicineReminder.getMedicineName();
        /*
          Dynamically generate checkboxes for the amount of doses per day
          based on the amount of doses per day
          when a checkbox is clicked remove it from layout
          and update the database as well as the display
          update the count of doses as well
         */
        int dosesPerDayInt = Integer.parseInt(dosesPerDay);

        //remove all checkboxes from layout
        LinearLayout linearLayout = findViewById(R.id.checkboxForDoses);
        linearLayout.removeAllViews();

        //generate checkboxes for the amount of doses per day
        for (int i = 0; i < dosesPerDayInt; i++) {
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText(getString(R.string.doseCheckBox,String.valueOf(i + 1)));
            checkBox.setId(i);
            checkBox.setOnClickListener(v -> {
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
                MedicineReminder medicineReminder1 = helper.readDataById(idReceived);
                String numberOfDays = medicineReminder1.getNumberOfDay();

                //if the count is 0 and there are no checkboxes change dosesTitle to dosesCompleted else change it to dosesRemaining
                final TextView dosesCheckboxTitle1 = findViewById(R.id.dosesTitle);
                if(count == 0){
                    int daysInt = Integer.parseInt(numberOfDays);
                    numberOfDays = String.valueOf(daysInt-1);
                    numberOfDaysDisplayString.setText(numberOfDays);
                    dosesPerDayDisplay.setText(String.valueOf(dosesPerDayInt));
                    dosesCheckboxTitle1.setText(R.string.dosesComplete);
                    //update the number of days in the database
                    boolean isUpdateDosesSuccessful = helper.updateDosesPerDay(idReceived, String.valueOf(dosesPerDayInt));
                    boolean isUpdateDaysSuccessful = helper.updateNumberOfDay(idReceived, numberOfDays);
                    if (isUpdateDaysSuccessful && isUpdateDosesSuccessful) {
                        Message.messageGreen(ReminderDetails.this, "Days Updated");
                    } else {
                        Message.messageRed(ReminderDetails.this, "Days not updated");
                    }
                    checkBoxGenerator(idReceived, dosesPerDayDisplay, numberOfDaysDisplayString);
                }
                else{
                    dosesCheckboxTitle1.setText(R.string.dosesRemiaining);
                }

                if(Integer.parseInt(numberOfDays) == 0){
                    //cleanup everything
                    //random number
                    int random = (int) (Math.random() * 10000);
                    //create a notification
                    NotificationHelper.createNotification(ReminderDetails.this,medicineName+"_channel","Doses of "+medicineName+" Finished the alarm has been deleted.",medicineName,random);
                    AlarmHelper.cancelTheAlarm(ReminderDetails.this, medicineName,dosesPerDay,numberOfDays);
                    //delete the Notification channel using channel id and application context
                    NotificationHelper.deleteNotificationChannel(ReminderDetails.this,
                            medicineName + "_channel");

                    //get alarm object from database by medicine name using getAlarmData
                    Alarm alarmByMedicineName = helper.getAlarmData(medicineName);
                    //delete the alarm from the database
                    boolean isDeleteAlarmDataSuccessful = helper.deleteAlarmData(alarmByMedicineName.getAlarmId());

                    //delete the reminder from the database
                    boolean isDeleteDataSuccessful = helper.deleteData(idReceived);

                    if(isDeleteAlarmDataSuccessful && isDeleteDataSuccessful){
                        Message.messageGreen(ReminderDetails.this,"Alarm and Reminder Deleted");
                        finish();
                    }
                    else{
                        Message.messageRed(ReminderDetails.this,"Alarm and Reminder not Deleted");
                    }
                }
            });
            linearLayout.addView(checkBox);
        }
    }
}