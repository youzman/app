package com.crossfireevents.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crossfireevents.app.R;
import com.crossfireevents.app.model.NotificationData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationData> notifications;
    private final NotificationClickListener clickListener;
    private final SimpleDateFormat dateFormat;

    public interface NotificationClickListener {
        void onNotificationClick(NotificationData notification);
    }

    public NotificationAdapter(List<NotificationData> notifications, NotificationClickListener clickListener) {
        this.notifications = notifications;
        this.clickListener = clickListener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationData notification = notifications.get(position);
        holder.textTitle.setText(notification.getTitle());
        holder.textMessage.setText(notification.getMessage());
        
        // Format timestamp
        String formattedTime = dateFormat.format(new Date(notification.getTimestamp()));
        holder.textTime.setText(formattedTime);
        
        // Set read/unread indicator
        if (notification.isRead()) {
            holder.itemView.setAlpha(0.7f);
            holder.imageUnread.setVisibility(View.GONE);
        } else {
            holder.itemView.setAlpha(1.0f);
            holder.imageUnread.setVisibility(View.VISIBLE);
        }
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            clickListener.onNotificationClick(notification);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
    
    public void updateNotifications(List<NotificationData> newNotifications) {
        this.notifications = newNotifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textMessage;
        TextView textTime;
        ImageView imageUnread;

        NotificationViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_notification_title);
            textMessage = itemView.findViewById(R.id.text_notification_message);
            textTime = itemView.findViewById(R.id.text_notification_time);
            imageUnread = itemView.findViewById(R.id.image_notification_unread);
        }
    }
}