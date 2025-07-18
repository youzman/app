package com.crossfireevents.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.crossfireevents.app.model.NotificationData;
import com.crossfireevents.app.repository.NotificationRepository;

import java.util.List;

public class NotificationsViewModel extends AndroidViewModel {

    private final NotificationRepository notificationRepository;
    private final LiveData<List<NotificationData>> allNotifications;
    private final LiveData<List<NotificationData>> unreadNotifications;
    private final LiveData<Integer> unreadNotificationCount;
    private final MutableLiveData<Boolean> isLoading;

    public NotificationsViewModel(Application application) {
        super(application);
        notificationRepository = new NotificationRepository(application);
        allNotifications = notificationRepository.getAllNotifications();
        unreadNotifications = notificationRepository.getUnreadNotifications();
        unreadNotificationCount = notificationRepository.getUnreadNotificationCount();
        isLoading = new MutableLiveData<>(false);
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
    
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    
    public void markAsRead(int notificationId) {
        notificationRepository.markAsRead(notificationId);
    }
    
    public void markAllAsRead() {
        notificationRepository.markAllAsRead();
    }
    
    public void deleteNotification(NotificationData notification) {
        notificationRepository.delete(notification);
    }
    
    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}