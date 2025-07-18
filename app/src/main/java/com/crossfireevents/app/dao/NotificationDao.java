package com.crossfireevents.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crossfireevents.app.model.NotificationData;

import java.util.List;

@Dao
public interface NotificationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationData notification);
    
    @Update
    void update(NotificationData notification);
    
    @Delete
    void delete(NotificationData notification);
    
    @Query("DELETE FROM notifications")
    void deleteAll();
    
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    LiveData<List<NotificationData>> getAllNotifications();
    
    @Query("SELECT * FROM notifications WHERE isRead = 0 ORDER BY timestamp DESC")
    LiveData<List<NotificationData>> getUnreadNotifications();
    
    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    LiveData<Integer> getUnreadNotificationCount();
    
    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    void markAsRead(int notificationId);
    
    @Query("UPDATE notifications SET isRead = 1")
    void markAllAsRead();
}