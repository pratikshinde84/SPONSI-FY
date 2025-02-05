package com.example.sponsi_fy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<AddFragment.Event> eventList;
    private DatabaseReference databaseReference;
    private String CARD_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);

        // Retrieve the event type passed from the previous activity
        CARD_DATA = getIntent().getStringExtra("CARD_DATA");
        if (CARD_DATA == null) {
            Toast.makeText(this, "No event type provided!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        adapter = new PostAdapter(eventList, this); // Pass listener to adapter
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users_Details");

        fetchFilteredPosts();
    }

    private void fetchFilteredPosts() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    DataSnapshot postsSnapshot = userSnapshot.child("Posts");
                    for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                        AddFragment.Event event = postSnapshot.getValue(AddFragment.Event.class);
                        if (event != null && CARD_DATA.equals(event.eventType)) {
                            eventList.add(event);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                if (eventList.isEmpty()) {
                    Toast.makeText(PostActivity.this, "No posts found for the selected event type.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AddFragment.Event event) {
        Intent intent = new Intent(PostActivity.this, DetailsActivity.class);
        intent.putExtra("EVENT_NAME", event.eventName);
        intent.putExtra("EVENT_TYPE", event.eventType);
        intent.putExtra("EVENT_LOCATION", event.eventLocation);
        intent.putExtra("EVENT_DATE", event.eventDate);
        startActivity(intent);
    }
}
