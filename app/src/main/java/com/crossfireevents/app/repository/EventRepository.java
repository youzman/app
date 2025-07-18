package com.crossfireevents.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.crossfireevents.app.dao.EventDao;
import com.crossfireevents.app.database.AppDatabase;
import com.crossfireevents.app.model.EventData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    
    private final EventDao eventDao;
    private final LiveData<List<EventData>> allEvents;
    private final ExecutorService executorService;
    private final FirebaseFirestore firestore;
    
    public EventRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        eventDao = database.eventDao();
        allEvents = eventDao.getAllEvents();
        executorService = Executors.newSingleThreadExecutor();
        firestore = FirebaseFirestore.getInstance();
    }
    
    public LiveData<List<EventData>> getAllEvents() {
        return allEvents;
    }
    
    public LiveData<EventData> getEventById(String eventId) {
        return eventDao.getEventById(eventId);
    }
    
    public LiveData<List<EventData>> getUpcomingEvents() {
        long currentTime = System.currentTimeMillis();
        return eventDao.getUpcomingEvents(currentTime);
    }
    
    public LiveData<List<EventData>> getCurrentEvents() {
        long currentTime = System.currentTimeMillis();
        return eventDao.getCurrentEvents(currentTime);
    }
    
    public LiveData<List<EventData>> getPastEvents() {
        long currentTime = System.currentTimeMillis();
        return eventDao.getPastEvents(currentTime);
    }
    
    public void insert(EventData event) {
        executorService.execute(() -> eventDao.insert(event));
    }
    
    public void update(EventData event) {
        executorService.execute(() -> eventDao.update(event));
    }
    
    public void delete(EventData event) {
        executorService.execute(() -> eventDao.delete(event));
    }
    
    public void deleteAll() {
        executorService.execute(eventDao::deleteAll);
    }
    
    public void refreshEvents() {
        firestore.collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<EventData> events = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            EventData event = document.toObject(EventData.class);
                            event.setId(document.getId());
                            events.add(event);
                        }
                        executorService.execute(() -> eventDao.insertAll(events));
                    }
                });
    }
}