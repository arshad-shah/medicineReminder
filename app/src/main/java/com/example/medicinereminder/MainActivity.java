package com.example.medicinereminder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
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

import com.example.medicinereminder.helperClasses.CustomAdapter;
import com.example.medicinereminder.helperClasses.MedicineReminder;
import com.example.medicinereminder.helperClasses.Message;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        String dosesPerDayString = dosesInADay.getText().toString();
                        String daysToUseString = numberOfDays.getText().toString();

                        //error check to ensure values are entered
                        areAllFieldsFilled = CheckAllFields(medicineName,dosesInADay,numberOfDays);

                        if(areAllFieldsFilled){
                            int dosesPerDay = Integer.parseInt(dosesPerDayString);
                            int daysToUse = Integer.parseInt(daysToUseString);

                            long id = helper.insertData(name, dosesPerDay, daysToUse);
                            if(id <= 0){
                                Message.message(MainActivity.this,"Creating the reminder failed");

                            }
                            else{
                                Message.message(MainActivity.this,"Creating the reminder was successfull");
                            }
                            medicineName.setText("");
                            dosesInADay.setText("");
                            numberOfDays.setText("");
                            alertDialog.cancel();
                            updateUI();
                        }
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

    public void updateUI(){
        final ListView list = findViewById(R.id.ReminderList);
        final TextView nullStatusText = findViewById(R.id.nullStatusText);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this,R.layout.reminderlistitem, helper.readData());

        if(helper.readData().size() > 0){
            nullStatusText.setVisibility(GONE);
        }
        else{
            nullStatusText.setVisibility(VISIBLE);
        }

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MedicineReminder clickedItem = (MedicineReminder) adapterView.getItemAtPosition(i);
                Intent navigateToReminderActivity = new Intent(MainActivity.this, ReminderDetails.class);
                navigateToReminderActivity.putExtra("id", clickedItem.getId());
                navigateToReminderActivity.putExtra("nameOfMedicine", clickedItem.getMedicineName());
                navigateToReminderActivity.putExtra("dosesPerDay", clickedItem.getDosesPerDay());
                navigateToReminderActivity.putExtra("numberOfDays", clickedItem.getNumberOfDays());
                startActivity(navigateToReminderActivity);
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
     * @return boolean if all fields are filled returns true else false
     * */
    public boolean CheckAllFields(EditText medicineName, EditText dosesInADay, EditText numberOfDays) {
        if (medicineName.length() == 0) {
            medicineName.setError("Medicine name is required");
            medicineName.requestFocus();
            return false;
        }

        if (dosesInADay.length() == 0) {
            dosesInADay.setError("Doses per day is required");
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