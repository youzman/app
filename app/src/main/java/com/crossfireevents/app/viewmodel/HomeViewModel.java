package com.crossfireevents.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.crossfireevents.app.model.EventData;
import com.crossfireevents.app.repository.EventRepository;
import com.crossfireevents.app.repository.NotificationRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final EventRepository eventRepository;
    private final NotificationRepository notificationRepository;
    private final MutableLiveData<String> text;
    private final LiveData<List<EventData>> upcomingEvents;
    private final LiveData<Integer> unreadNotificationCount;

    public HomeViewModel(Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        notificationRepository = new NotificationRepository(application);
        text = new MutableLiveData<>();
        text.setValue("مرحبًا بك في تطبيق إشعارات كروس فاير! احصل على آخر أخبار وأحداث اللعبة.");
        upcomingEvents = eventRepository.getUpcomingEvents();
        unreadNotificationCount = notificationRepository.getUnreadNotificationCount();
        
        // Refresh events from Firestore
        refreshEvents();
    }

    public LiveData<String> getText() {
        return text;
    }
    
    public LiveData<List<EventData>> getUpcomingEvents() {
        return upcomingEvents;
    }
    
    public LiveData<Integer> getUnreadNotificationCount() {
        return unreadNotificationCount;
    }
    
    public void refreshEvents() {
        eventRepository.refreshEvents();
    }
}