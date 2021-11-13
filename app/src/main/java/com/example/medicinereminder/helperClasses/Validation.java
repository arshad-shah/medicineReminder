package com.example.medicinereminder.helperClasses;

import android.widget.EditText;

public class Validation {
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
    public static boolean CheckAllFields(EditText medicineName, EditText dosesInADay, EditText numberOfDays) {
        if (medicineName.length() == 0 ) {
            medicineName.setError("Medicine name is required");
            medicineName.requestFocus();
            return false;
        }
        if (dosesInADay.length() == 0  ||  dosesInADay.getText().toString().equals("0") ||  dosesInADay.getText().toString().equals("")) {
            dosesInADay.setError("Doses per day is required");
            dosesInADay.requestFocus();
            return false;
        }

        if (numberOfDays.length() == 0 || numberOfDays.getText().toString().equals("0") ||  numberOfDays.getText().toString().equals("")) {
            numberOfDays.setError("Number Of Days is required");
            numberOfDays.requestFocus();
            return false;
        }
        // after all validation return true.
        return true;
    }
}
