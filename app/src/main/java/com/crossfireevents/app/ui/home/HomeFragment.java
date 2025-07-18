package com.crossfireevents.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.crossfireevents.app.R;
import com.crossfireevents.app.databinding.FragmentHomeBinding;
import com.crossfireevents.app.model.EventData;
import com.crossfireevents.app.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textHome = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textHome::setText);
        
        // Set up upcoming events
        final TextView textNextEvent = binding.textNextEvent;
        homeViewModel.getUpcomingEvents().observe(getViewLifecycleOwner(), events -> {
            if (events != null && !events.isEmpty()) {
                // Display the next upcoming event
                EventData nextEvent = events.get(0);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String eventDate = dateFormat.format(new Date(nextEvent.getStartDate()));
                textNextEvent.setText(nextEvent.getTitle() + "\n" + eventDate);
            } else {
                textNextEvent.setText(R.string.no_events);
            }
        });
        
        // Set up notification status
        final TextView textNotificationStatus = binding.textNotificationStatus;
        homeViewModel.getUnreadNotificationCount().observe(getViewLifecycleOwner(), count -> {
            if (count > 0) {
                textNotificationStatus.setText("لديك " + count + " إشعارات جديدة");
            } else {
                textNotificationStatus.setText("الإشعارات مفعلة");
            }
        });
        
        // Set up buttons
        Button buttonViewEvents = binding.buttonViewEvents;
        buttonViewEvents.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_events);
        });
        
        Button buttonNotificationSettings = binding.buttonNotificationSettings;
        buttonNotificationSettings.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_settings);
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}