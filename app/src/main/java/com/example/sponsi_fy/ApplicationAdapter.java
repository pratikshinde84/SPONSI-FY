package com.example.sponsi_fy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private final List<Application> applicationList;

    public ApplicationAdapter(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.eventName.setText(application.getEventName());
        holder.eventType.setText(application.getEventType());
        holder.eventLocation.setText(application.getEventLocation());
        holder.eventDate.setText(application.getEventDate());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventType, eventLocation, eventDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tv_event_name);
            eventType = itemView.findViewById(R.id.tv_event_type);
            eventLocation = itemView.findViewById(R.id.tv_event_location);
            eventDate = itemView.findViewById(R.id.tv_event_date);
        }
    }
}
