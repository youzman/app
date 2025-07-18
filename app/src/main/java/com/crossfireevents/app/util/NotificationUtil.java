package com.crossfireevents.app.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;

import com.crossfireevents.app.MainActivity;
import com.crossfireevents.app.R;
import com.crossfireevents.app.model.NotificationData;

/**
 * Utility class for creating and showing notifications.
 */
public class NotificationUtil {

    private static final String CHANNEL_ID = "crossfire_events_channel";
    private static final String CHANNEL_NAME = "Crossfire Events";
    private static final String CHANNEL_DESCRIPTION = "Notifications for Crossfire game events";
    
    /**
     * Creates a notification channel for Android O and above.
     *
     * @param context The context.
     */
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    
    /**
     * Shows a notification.
     *
     * @param context The context.
     * @param notificationData The notification data.
     */
    public static void showNotification(Context context, NotificationData notificationData) {
        // Check if notifications are enabled
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        if (!notificationsEnabled) {
            return;
        }
        
        // Create an intent for when the notification is tapped
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notification_id", notificationData.getId());
        if (notificationData.getEventId() != null) {
            intent.putExtra("event_id", notificationData.getEventId());
        }
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                notificationData.getId(),
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        
        // Get notification sound if enabled
        Uri defaultSoundUri = null;
        boolean soundEnabled = sharedPreferences.getBoolean("notification_sound", true);
        if (soundEnabled) {
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        
        // Get vibration setting
        boolean vibrationEnabled = sharedPreferences.getBoolean("notification_vibration", true);
        
        // Build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(notificationData.getTitle())
                .setContentText(notificationData.getMessage())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        
        if (soundEnabled) {
            notificationBuilder.setSound(defaultSoundUri);
        }
        
        if (vibrationEnabled) {
            notificationBuilder.setVibrate(new long[]{0, 500, 250, 500});
        }
        
        // Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        }
    }
}