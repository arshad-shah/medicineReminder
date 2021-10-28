package com.example.medicinereminder.helperClasses;

import android.content.Context;
import android.widget.Toast;

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
}