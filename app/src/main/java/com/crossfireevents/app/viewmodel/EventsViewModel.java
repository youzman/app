package com.crossfireevents.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.crossfireevents.app.model.EventData;
import com.crossfireevents.app.repository.EventRepository;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    private final EventRepository eventRepository;
    private final LiveData<List<EventData>> allEvents;
    private final LiveData<List<EventData>> upcomingEvents;
    private final LiveData<List<EventData>> currentEvents;
    private final MutableLiveData<Boolean> isLoading;

    public EventsViewModel(Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
        upcomingEvents = eventRepository.getUpcomingEvents();
        currentEvents = eventRepository.getCurrentEvents();
        isLoading = new MutableLiveData<>(false);
        
        // Refresh events from Firestore
        refreshEvents();
    }

    public LiveData<List<EventData>> getAllEvents() {
        return allEvents;
    }
    
    public LiveData<List<EventData>> getUpcomingEvents() {
        return upcomingEvents;
    }
    
    public LiveData<List<EventData>> getCurrentEvents() {
        return currentEvents;
    }
    
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    
    public void refreshEvents() {
        isLoading.setValue(true);
        eventRepository.refreshEvents();
        isLoading.setValue(false);
    }
    
    public LiveData<EventData> getEventById(String eventId) {
        return eventRepository.getEventById(eventId);
    }
}