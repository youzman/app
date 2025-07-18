package com.crossfireevents.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.crossfireevents.app.R;
import com.crossfireevents.app.model.EventData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventData> events;
    private final SimpleDateFormat dateFormat;

    public EventAdapter(List<EventData> events) {
        this.events = events;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventData event = events.get(position);
        holder.textTitle.setText(event.getTitle());
        
        // Format dates
        String startDate = dateFormat.format(new Date(event.getStartDate()));
        String endDate = dateFormat.format(new Date(event.getEndDate()));
        
        holder.textDate.setText(startDate + " - " + endDate);
        holder.textDescription.setText(event.getDescription());
        
        // Set rewards if available
        if (event.getRewards() != null && !event.getRewards().isEmpty()) {
            holder.textRewards.setVisibility(View.VISIBLE);
            holder.textRewards.setText("المكافآت: " + event.getRewards());
        } else {
            holder.textRewards.setVisibility(View.GONE);
        }
        
        // Set button click listener
        holder.buttonDetails.setOnClickListener(v -> {
            // TODO: Navigate to event details
            // For now, we'll just show a toast
            // Toast.makeText(v.getContext(), "تفاصيل الحدث: " + event.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    
    public void updateEvents(List<EventData> newEvents) {
        this.events = newEvents;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDate;
        TextView textDescription;
        TextView textRewards;
        Button buttonDetails;

        EventViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_event_title);
            textDate = itemView.findViewById(R.id.text_event_date);
            textDescription = itemView.findViewById(R.id.text_event_description);
            textRewards = itemView.findViewById(R.id.text_event_rewards);
            buttonDetails = itemView.findViewById(R.id.button_event_details);
        }
    }
}