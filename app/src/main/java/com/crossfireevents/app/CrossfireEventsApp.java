package com.crossfireevents.app;

import android.app.Application;

import com.crossfireevents.app.util.NotificationUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Custom Application class for the Crossfire Events app.
 * This class is responsible for initializing app-wide components.
 */
public class CrossfireEventsApp extends Application {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        
        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        
        // Subscribe to Firebase topic for notifications
        FirebaseMessaging.getInstance().subscribeToTopic("crossfire_events");
        
        // Create notification channel for Android O and above
        NotificationUtil.createNotificationChannel(this);
    }
    
    /**
     * Gets the Firebase Analytics instance.
     *
     * @return The Firebase Analytics instance.
     */
    public FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
    }
}