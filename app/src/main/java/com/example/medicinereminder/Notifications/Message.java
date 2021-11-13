package com.example.medicinereminder.Notifications;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.R;

/**
 * Message class created to serve messages using a simple function call
 * rather than repeated boilerplate code
 * @author Arshad shah
 * */
public class Message {
    /**
     * Toast creater
     * @param context the context of the application
     * @param message the string message to show
     * */
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Red Toast Creater
     * @param context the context of the application
     * @param message the string message to show
     */
    public static void messageRed(Context context, String message) {
        //layout inflater for toast
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate the layout over toast
        View layout = inflater.inflate(R.layout.messagered, null);

        //creating the toast
        Toast toast = new Toast(context);

        //setting the view of the toast
        toast.setView(layout);

        //setting the Message of the toast
        TextView text = layout.findViewById(R.id.message);
        text.setText(message);

        //setting the gravity of the toast
        toast.setGravity(Gravity.BOTTOM,0, 100);

        //setting the duration of the toast
        toast.setDuration(Toast.LENGTH_LONG);

        //showing toast
        toast.show();
    }

    /**
     * Green Toast Creater
     * @param context the context of the application
     * @param message the string message to show
     */
    public static void messageGreen(Context context, String message) {
        //layout inflater for toast
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate the layout over toast
        View layout = inflater.inflate(R.layout.messagegreen, null);

        //creating the toast
        Toast toast = new Toast(context);

        //setting the view of the toast
        toast.setView(layout);

        //setting the Message of the toast
        TextView text = layout.findViewById(R.id.message);
        text.setText(message);

        //setting the gravity of the toast
        toast.setGravity(Gravity.BOTTOM, 0, 100);

        //setting the duration of the toast
        toast.setDuration(Toast.LENGTH_LONG);

        //showing toast
        toast.show();
    }

}