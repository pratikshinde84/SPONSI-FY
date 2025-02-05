package com.example.sponsi_fy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<AddFragment.Event> eventList;

    public EventAdapter(List<AddFragment.Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        AddFragment.Event event = eventList.get(position);
        holder.eventName.setText(event.eventName);
        holder.eventType.setText(event.eventType);
        holder.eventLocation.setText(event.eventLocation);
        holder.eventDate.setText(event.eventDate);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventType, eventLocation, eventDate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tv_event_name);
            eventType = itemView.findViewById(R.id.tv_event_type);
            eventLocation = itemView.findViewById(R.id.tv_event_location);
            eventDate = itemView.findViewById(R.id.tv_event_date);
        }
    }
}
