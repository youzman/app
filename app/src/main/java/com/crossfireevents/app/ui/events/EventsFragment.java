package com.crossfireevents.app.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crossfireevents.app.R;
import com.crossfireevents.app.adapter.EventAdapter;
import com.crossfireevents.app.databinding.FragmentEventsBinding;
import com.crossfireevents.app.model.EventData;
import com.crossfireevents.app.viewmodel.EventsViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    private EventsViewModel eventsViewModel;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private TextView textNoEvents;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel = new ViewModelProvider(this).get(EventsViewModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize views
        recyclerView = binding.recyclerViewEvents;
        textNoEvents = binding.textNoEvents;
        progressBar = binding.progressBar;
        swipeRefreshLayout = binding.swipeRefreshLayout;
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(new ArrayList<>());
        recyclerView.setAdapter(eventAdapter);
        
        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            eventsViewModel.refreshEvents();
            swipeRefreshLayout.setRefreshing(false);
        });
        
        // Observe events
        eventsViewModel.getAllEvents().observe(getViewLifecycleOwner(), this::updateEventsList);
        
        // Observe loading state
        eventsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        return root;
    }
    
    private void updateEventsList(List<EventData> events) {
        if (events != null && !events.isEmpty()) {
            eventAdapter.updateEvents(events);
            recyclerView.setVisibility(View.VISIBLE);
            textNoEvents.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            textNoEvents.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}