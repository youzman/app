package com.crossfireevents.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.crossfireevents.app.dao.EventDao;
import com.crossfireevents.app.dao.NotificationDao;
import com.crossfireevents.app.model.EventData;
import com.crossfireevents.app.model.NotificationData;

@Database(entities = {EventData.class, NotificationData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    private static AppDatabase instance;
    
    public abstract EventDao eventDao();
    public abstract NotificationDao notificationDao();
    
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "crossfire_events_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}