package com.example.medicinereminder.helperClasses;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.R;
import com.example.medicinereminder.ReminderDetails;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<MedicineReminder> {
    ArrayList<MedicineReminder> MedicineReminder;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MedicineReminder> objects) {
        super(context, resource, objects);
        MedicineReminder = objects;
    }

    @Override
    public View getView(int Position, View ConvertView, ViewGroup parent){

        View row = ConvertView;

        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if(row == null){
            row = inflater.inflate(R.layout.reminderlistitem,parent,false);
        }

        MedicineReminder medicineReminder = MedicineReminder.get(Position);
        final TextView nameOfMedicine = row.findViewById(R.id.nameOfMedicine);
        final TextView dosesLeft = row.findViewById(R.id.dosesLeft);

        dosesLeft.setText(medicineReminder.getDosesPerDay());
        nameOfMedicine.setText(medicineReminder.getMedicineName());
        return row;

    }
}

