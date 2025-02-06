package com.example.sponsi_fy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private final List<Request> requestsList;

    public RequestsAdapter(List<Request> requestsList) {
        this.requestsList = requestsList;
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
        Request request = requestsList.get(position);
        holder.requestId.setText(request.getRequestId());
        holder.clubName.setText(request.getClubName());
        holder.sponsorName.setText(request.getSponsorName());
        holder.requestDetails.setText(request.getRequestDetails());
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestId, clubName, sponsorName, requestDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            requestId = itemView.findViewById(R.id.tv_request_id);
            clubName = itemView.findViewById(R.id.tv_club_name);
            sponsorName = itemView.findViewById(R.id.tv_sponsor_name);
            requestDetails = itemView.findViewById(R.id.tv_request_details);
        }
    }
}
