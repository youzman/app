package com.crossfireevents.app.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crossfireevents.app.R;
import com.crossfireevents.app.adapter.NotificationAdapter;
import com.crossfireevents.app.databinding.FragmentNotificationsBinding;
import com.crossfireevents.app.model.NotificationData;
import com.crossfireevents.app.viewmodel.NotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationsViewModel;
    private NotificationAdapter notificationAdapter;
    private RecyclerView recyclerView;
    private TextView textNoNotifications;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Enable options menu
        setHasOptionsMenu(true);
        
        // Initialize views
        recyclerView = binding.recyclerViewNotifications;
        textNoNotifications = binding.textNoNotifications;
        progressBar = binding.progressBar;
        swipeRefreshLayout = binding.swipeRefreshLayout;
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationAdapter = new NotificationAdapter(new ArrayList<>(), notification -> {
            // Mark notification as read when clicked
            notificationsViewModel.markAsRead(notification.getId());
            // TODO: Navigate to event details if notification has eventId
        });
        recyclerView.setAdapter(notificationAdapter);
        
        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // No refresh action needed as notifications are local
            swipeRefreshLayout.setRefreshing(false);
        });
        
        // Observe notifications
        notificationsViewModel.getAllNotifications().observe(getViewLifecycleOwner(), this::updateNotificationsList);
        
        // Observe loading state
        notificationsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        return root;
    }
    
    private void updateNotificationsList(List<NotificationData> notifications) {
        if (notifications != null && !notifications.isEmpty()) {
            notificationAdapter.updateNotifications(notifications);
            recyclerView.setVisibility(View.VISIBLE);
            textNoNotifications.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            textNoNotifications.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notifications_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_mark_all_read) {
            notificationsViewModel.markAllAsRead();
            Toast.makeText(getContext(), "تم تعليم جميع الإشعارات كمقروءة", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_delete_all) {
            notificationsViewModel.deleteAllNotifications();
            Toast.makeText(getContext(), "تم حذف جميع الإشعارات", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}