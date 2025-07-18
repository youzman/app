package com.crossfireevents.app.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SettingsViewModel extends AndroidViewModel {

    private final SharedPreferences preferences;
    private final MutableLiveData<Boolean> notificationsEnabled;
    private final MutableLiveData<Boolean> notificationSoundEnabled;
    private final MutableLiveData<Boolean> notificationVibrationEnabled;
    private final MutableLiveData<String> appVersion;

    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_NOTIFICATION_SOUND = "notification_sound";
    private static final String KEY_NOTIFICATION_VIBRATION = "notification_vibration";

    public SettingsViewModel(Application application) {
        super(application);
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
        
        notificationsEnabled = new MutableLiveData<>(getNotificationsEnabled());
        notificationSoundEnabled = new MutableLiveData<>(getNotificationSoundEnabled());
        notificationVibrationEnabled = new MutableLiveData<>(getNotificationVibrationEnabled());
        
        // Get app version from package info
        String versionName = "1.0";
        try {
            versionName = application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        appVersion = new MutableLiveData<>(versionName);
    }

    public LiveData<Boolean> getNotificationsEnabledLiveData() {
        return notificationsEnabled;
    }
    
    public LiveData<Boolean> getNotificationSoundEnabledLiveData() {
        return notificationSoundEnabled;
    }
    
    public LiveData<Boolean> getNotificationVibrationEnabledLiveData() {
        return notificationVibrationEnabled;
    }
    
    public LiveData<String> getAppVersion() {
        return appVersion;
    }
    
    public boolean getNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }
    
    public boolean getNotificationSoundEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION_SOUND, true);
    }
    
    public boolean getNotificationVibrationEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION_VIBRATION, true);
    }
    
    public void setNotificationsEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
        notificationsEnabled.setValue(enabled);
    }
    
    public void setNotificationSoundEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATION_SOUND, enabled).apply();
        notificationSoundEnabled.setValue(enabled);
    }
    
    public void setNotificationVibrationEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATION_VIBRATION, enabled).apply();
        notificationVibrationEnabled.setValue(enabled);
    }
}