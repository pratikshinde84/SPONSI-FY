package com.example.sponsi_fy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    private TextView eventNameTextView, eventTypeTextView, eventLocationTextView, eventDateTextView;
    private TextView eventDurationTextView, eventHeadNameTextView, mobileNumberTextView, emailTextView, descriptionTextView;
    private Button applyButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        // Reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference("Users_Details");

        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventType = getIntent().getStringExtra("EVENT_TYPE");
        String eventLocation = getIntent().getStringExtra("EVENT_LOCATION");
        String eventDate = getIntent().getStringExtra("EVENT_DATE");

        // Initialize UI components
        eventNameTextView = findViewById(R.id.tv_event_name);
        eventTypeTextView = findViewById(R.id.tv_event_type);
        eventLocationTextView = findViewById(R.id.tv_event_location);
        eventDateTextView = findViewById(R.id.tv_event_date);
        eventDurationTextView = findViewById(R.id.tv_event_duration);
        eventHeadNameTextView = findViewById(R.id.tv_event_head_name);
        mobileNumberTextView = findViewById(R.id.tv_mobile_number);
        emailTextView = findViewById(R.id.tv_email);
        descriptionTextView = findViewById(R.id.tv_description);
        applyButton = findViewById(R.id.btn_apply);

        // Query the entire "Users_Details" to find the event in all users' posts
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean eventFound = false;

                // Loop through all users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Access the user's posts
                    for (DataSnapshot postSnapshot : userSnapshot.child("Posts").getChildren()) {
                        Event event = postSnapshot.getValue(Event.class);

                        // Check if the event matches the criteria
                        if (event != null && event.getEventName().equals(eventName) &&
                                event.getEventType().equals(eventType) &&
                                event.getEventLocation().equals(eventLocation) &&
                                event.getEventDate().equals(eventDate)) {

                            // Set event details in the UI
                            eventNameTextView.setText(event.getEventName());
                            eventTypeTextView.setText(event.getEventType());
                            eventLocationTextView.setText(event.getEventLocation());
                            eventDateTextView.setText(event.getEventDate());
                            eventDurationTextView.setText(event.getEventDuration());
                            eventHeadNameTextView.setText(event.getEventHeadName());
                            mobileNumberTextView.setText(event.getMobileNumber());
                            emailTextView.setText(event.getEmail());
                            descriptionTextView.setText(event.getDescription());

                            eventFound = true;
                            break; // Break out of the loop once event is found
                        }
                    }
                    if (eventFound) {
                        break; // Stop searching if the event is found
                    }
                }

                // Handle case where event wasn't found
                if (!eventFound) {
                    Log.d("Event Search", "Event not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors
                Log.e("Database Error", databaseError.getMessage());
            }
        });
    }
}
