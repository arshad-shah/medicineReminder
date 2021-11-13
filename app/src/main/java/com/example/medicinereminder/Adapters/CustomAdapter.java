package com.example.medicinereminder.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medicinereminder.R;
import com.example.medicinereminder.ObjectClasses.MedicineReminder;

import java.util.ArrayList;

/**
 * Custom adapter for the list view in the main activity
 * @author Arshad Shah
 */
public class CustomAdapter extends ArrayAdapter<MedicineReminder> {
    
    //ArrayList of MedicineReminder
    ArrayList<MedicineReminder> MedicineReminder;

    
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MedicineReminder> objects) {
        super(context, resource, objects);
        MedicineReminder = objects;
    }

    @Override
    public View getView(int Position, View ConvertView, ViewGroup parent){

        View row = ConvertView;

        Context context = getContext();

        //check to see if there is a row there
        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(context);
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

