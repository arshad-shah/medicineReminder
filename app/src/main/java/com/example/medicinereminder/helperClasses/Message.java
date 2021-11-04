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

    /**
     * Toast creater
     * @param context the context of the application
     * @param message the string message to show
     * @param length the length of the toast
     * */
    public static void message(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    /**
     * Toast creater
     * @param context the context of the application
     * @param message the string message to show
     * @param length the length of the toast
     * @param gravity the gravity of the toast
     * */
    public static void message(Context context, String message, int length, int gravity) {
        Toast toast = Toast.makeText(context, message, length);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * Toast creater
     * @param context the context of the application
     * @param message the string message to show
     * @param length the length of the toast
     * @param gravity the gravity of the toast
     * @param xOffset the x offset of the toast
     * @param yOffset the y offset of the toast
     * */
    public static void message(Context context, String message, int length, int gravity, int xOffset, int yOffset) {
        Toast toast = Toast.makeText(context, message, length);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

}