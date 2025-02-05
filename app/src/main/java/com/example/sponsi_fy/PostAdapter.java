package com.example.sponsi_fy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<AddFragment.Event> eventList;
    private final OnItemClickListener onItemClickListener;

    // Constructor with listener for handling item clicks
    public PostAdapter(List<AddFragment.Event> eventList, OnItemClickListener onItemClickListener) {
        this.eventList = eventList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        AddFragment.Event event = eventList.get(position);
        holder.eventName.setText(event.eventName);
        holder.eventType.setText(event.eventType);
        holder.eventLocation.setText(event.eventLocation);
        holder.eventDate.setText(event.eventDate);

        // Set OnClickListener for the CardView
        holder.itemView.setOnClickListener(v -> {
            // Pass the event details to the next activity
            onItemClickListener.onItemClick(event);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventType, eventLocation, eventDate;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tv_event_name);
            eventType = itemView.findViewById(R.id.tv_event_type);
            eventLocation = itemView.findViewById(R.id.tv_event_location);
            eventDate = itemView.findViewById(R.id.tv_event_date);
        }
    }

    // Interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(AddFragment.Event event);
    }
}
