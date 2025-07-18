package com.crossfireevents.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.crossfireevents.app.model.EventData;

import java.util.List;

@Dao
public interface EventDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventData event);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EventData> events);
    
    @Update
    void update(EventData event);
    
    @Delete
    void delete(EventData event);
    
    @Query("DELETE FROM events")
    void deleteAll();
    
    @Query("SELECT * FROM events ORDER BY startDate ASC")
    LiveData<List<EventData>> getAllEvents();
    
    @Query("SELECT * FROM events WHERE id = :eventId")
    LiveData<EventData> getEventById(String eventId);
    
    @Query("SELECT * FROM events WHERE startDate > :currentTime ORDER BY startDate ASC")
    LiveData<List<EventData>> getUpcomingEvents(long currentTime);
    
    @Query("SELECT * FROM events WHERE startDate <= :currentTime AND endDate >= :currentTime ORDER BY endDate ASC")
    LiveData<List<EventData>> getCurrentEvents(long currentTime);
    
    @Query("SELECT * FROM events WHERE endDate < :currentTime ORDER BY endDate DESC")
    LiveData<List<EventData>> getPastEvents(long currentTime);
}