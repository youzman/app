package com.crossfireevents.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.crossfireevents.app.dao.NotificationDao;
import com.crossfireevents.app.database.AppDatabase;
import com.crossfireevents.app.model.NotificationData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {
    
    private final NotificationDao notificationDao;
    private final LiveData<List<NotificationData>> allNotifications;
    private final LiveData<List<NotificationData>> unreadNotifications;
    private final LiveData<Integer> unreadNotificationCount;
    private final ExecutorService executorService;
    
    public NotificationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        notificationDao = database.notificationDao();
        allNotifications = notificationDao.getAllNotifications();
        unreadNotifications = notificationDao.getUnreadNotifications();
        unreadNotificationCount = notificationDao.getUnreadNotificationCount();
        executorService = Executors.newSingleThreadExecutor();
    }
    
    public LiveData<List<NotificationData>> getAllNotifications() {
        return allNotifications;
    }
    
    public LiveData<List<NotificationData>> getUnreadNotifications() {
        return unreadNotifications;
    }
    
    public LiveData<Integer> getUnreadNotificationCount() {
        return unreadNotificationCount;
    }
    
    public void insert(NotificationData notification) {
        executorService.execute(() -> notificationDao.insert(notification));
    }
    
    public void update(NotificationData notification) {
        executorService.execute(() -> notificationDao.update(notification));
    }
    
    public void delete(NotificationData notification) {
        executorService.execute(() -> notificationDao.delete(notification));
    }
    
    public void deleteAll() {
        executorService.execute(notificationDao::deleteAll);
    }
    
    public void markAsRead(int notificationId) {
        executorService.execute(() -> notificationDao.markAsRead(notificationId));
    }
    
    public void markAllAsRead() {
        executorService.execute(notificationDao::markAllAsRead);
    }
}