package com.example.sponsi_fy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    private TextView eventNameTextView, eventTypeTextView, eventLocationTextView, eventDateTextView;
    private TextView eventDurationTextView, eventHeadNameTextView, mobileNumberTextView, emailTextView, descriptionTextView;
    private Button applyButton,contactButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users_Details");

        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventType = getIntent().getStringExtra("EVENT_TYPE");
        String eventLocation = getIntent().getStringExtra("EVENT_LOCATION");
        String eventDate = getIntent().getStringExtra("EVENT_DATE");

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
        contactButton=findViewById(R.id.btn_contact);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(username).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String organization = dataSnapshot.child("organization").getValue(String.class);
                            String email = dataSnapshot.child("email").getValue(String.class);
                            String mobile = dataSnapshot.child("mobile").getValue(String.class);

                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String posterUsername = null;

                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        for (DataSnapshot postSnapshot : userSnapshot.child("Posts").getChildren()) {
                                            Event event = postSnapshot.getValue(Event.class);

                                            if (event != null && event.getEventName().equals(eventName) &&
                                                    event.getEventType().equals(eventType) &&
                                                    event.getEventLocation().equals(eventLocation) &&
                                                    event.getEventDate().equals(eventDate)) {
                                                posterUsername = userSnapshot.getKey();
                                                break;
                                            }
                                        }
                                        if (posterUsername != null) {
                                            break;
                                        }
                                    }

                                    if (posterUsername == null) {
                                        Log.d("Event Search", "Event poster not found.");
                                        return;
                                    }

                                    DatabaseReference posterRequestsRef = mDatabase.child(posterUsername).child("Requests");
                                    String requestKey = posterRequestsRef.push().getKey();

                                    if (requestKey != null) {
                                        posterRequestsRef.child(eventName).setValue(new User(
                                                username, name, organization, email, mobile, eventName
                                        ));
                                    }

                                    DatabaseReference currentUserApplicationsRef = mDatabase.child(username).child("Applications");
                                    String applicationKey = currentUserApplicationsRef.push().getKey();

                                    if (applicationKey != null) {
                                        currentUserApplicationsRef.child(applicationKey).setValue(new Event(
                                                eventName, eventType, eventLocation, eventDate,
                                                eventDurationTextView.getText().toString(),
                                                eventHeadNameTextView.getText().toString(),
                                                mobileNumberTextView.getText().toString(),
                                                emailTextView.getText().toString(),
                                                descriptionTextView.getText().toString()
                                        ));
                                    }
                                    Toast.makeText(DetailsActivity.this, "Applied successfully for the event", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("Database Error", databaseError.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(DetailsActivity.this, "User profile not found in database.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Database Error", databaseError.getMessage());
                    }
                });
            }
        });



        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean eventFound = false;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : userSnapshot.child("Posts").getChildren()) {
                        Event event = postSnapshot.getValue(Event.class);

                        if (event != null && event.getEventName().equals(eventName) &&
                                event.getEventType().equals(eventType) &&
                                event.getEventLocation().equals(eventLocation) &&
                                event.getEventDate().equals(eventDate)) {

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
                            break;
                        }
                    }
                    if (eventFound) {
                        break;
                    }
                }

                if (!eventFound) {
                    Log.d("Event Search", "Event not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Error", databaseError.getMessage());
            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mobileNumberTextView.getText().toString().trim();

                if (!mobile.startsWith("+")) {
                    mobile = "91" + mobile;
                }

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://wa.me/" + mobile));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailsActivity.this, "Unable to open WhatsApp. Please check the app is installed and the number format is correct.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
