package com.example.sponsi_fy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private final List<Request> requestList;

    public RequestsAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = requestList.get(position);

        holder.eventName.setText(request.getEventName());
        holder.username.setText(request.getUsername());
        holder.name.setText(request.getName());
        holder.organization.setText(request.getOrganization());
        holder.mobileNumber.setText(request.getMobileNumber());
        holder.email.setText(request.getEmail());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, username, name, organization, mobileNumber, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.tv_event_name);
            username = itemView.findViewById(R.id.tv_username);
            name = itemView.findViewById(R.id.tv_name);
            organization = itemView.findViewById(R.id.tv_organization);
            mobileNumber = itemView.findViewById(R.id.tv_mobile_number);
            email = itemView.findViewById(R.id.tv_email);
        }
    }
}
