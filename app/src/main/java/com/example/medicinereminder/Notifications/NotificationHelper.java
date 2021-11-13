package com.example.medicinereminder.Notifications;

import static android.app.Notification.VISIBILITY_PUBLIC;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.R;

public class NotificationHelper {

    /**
     * Creates a notification channel for a notification
     * @author Arshad Shah
     * @param context The Context of the Application
     * @param importance The importance of the Channel (a string)
     * @param showBadge Show Badge for Channel
     * @param name The name of the channel
     * @param description The Description for the channel
     * @param channel_id The id of the Channel Creates a Channel
     * */
    public static void createNotificationChannel(Context context,
                                                 int importance,
                                                 Boolean showBadge,
                                                 String name,
                                                 String description,
            String channel_id) {

        //get notification service
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //create channel
        NotificationChannel channel = new NotificationChannel(channel_id, name, importance);

        //set attributes for the channel
        channel.setDescription(description);
        channel.setShowBadge(showBadge);
        channel.enableLights(true);
        channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
        channel.enableVibration(true);
        // 3
        notificationManager.createNotificationChannel(channel);
    }
    
    /**
     * Delete Notification Channel
     * @author Arshad Shah
     * @param context The Context of the Application
     * @param channel_id The id of the Channel to be deleted
     */
    public static void deleteNotificationChannel(Context context, String channel_id) {
        //get notification service
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //delete channel
        notificationManager.deleteNotificationChannel(channel_id);
    }

    /**
     * Creates a Notification
     * @author Arshad Shah
     * @param context The Context of the Application
     * @param channel_id The id of the Channel (a string)
     * @param title the title of the notification
     * @param notification_id The id of the Notification ( Unique Integer)
     */
    public static void createNotification(Context context, String channel_id,String message, String title, int notification_id){
        //get notification service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //create an intent to open the app when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //pending intent to open app
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, notificationIntent, 0);

        //build the notification
        Builder notificationBuilder = new Builder(context, channel_id);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        notificationManager.notify(notification_id, notificationBuilder.build());
    }
}
