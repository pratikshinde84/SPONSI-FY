package com.example.sponsi_fy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class AddFragment extends Fragment {

    private EditText eventName, eventLocation, eventDuration, eventHeadName, mobileNumber, email, description;
    private Button saveButton;
    TextView eventDate;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Spinner eventTypeSpinner = view.findViewById(R.id.event_type);

        String[] items = {"Technology", "Education", "Sports", "Healthcare", "Government","Media"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eventTypeSpinner.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users_Details");

        eventName = view.findViewById(R.id.event_name);
        eventDate = view.findViewById(R.id.event_date);
        eventLocation = view.findViewById(R.id.event_loc);
        eventDuration = view.findViewById(R.id.event_dur);
        eventHeadName = view.findViewById(R.id.event_head);
        mobileNumber = view.findViewById(R.id.event_mobile);
        email = view.findViewById(R.id.event_email);
        description = view.findViewById(R.id.event_desc);
        saveButton = view.findViewById(R.id.btn_add);

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        (view, year1, month1, dayOfMonth) -> {
                            eventDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        saveButton.setOnClickListener(v -> saveEventToDatabase());
        return view;
    }

    private void saveEventToDatabase() {

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username == null) {
            Toast.makeText(getContext(), "User not logged in. Please log in first.", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = eventName.getText().toString().trim();
        String type = ((Spinner) requireView().findViewById(R.id.event_type)).getSelectedItem().toString().trim();
        String date = eventDate.getText().toString().trim();
        String location = eventLocation.getText().toString().trim();
        String duration = eventDuration.getText().toString().trim();
        String headName = eventHeadName.getText().toString().trim();
        String mobile = mobileNumber.getText().toString().trim();
        String desc = description.getText().toString().trim();
        String mail = email.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(date) ||
                TextUtils.isEmpty(location) || TextUtils.isEmpty(duration) ||
                TextUtils.isEmpty(headName) || TextUtils.isEmpty(mobile)) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(name, type, date, location, duration, headName, mobile, mail, desc);

        databaseReference.child(username).child("Posts").push().setValue(event)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Event saved successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to save event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clearFields() {
        eventName.setText("");
        eventDate.setText("");
        eventLocation.setText("");
        eventDuration.setText("");
        eventHeadName.setText("");
        mobileNumber.setText("");
        email.setText("");
        description.setText("");
    }

    public static class Event {
        public String eventName;
        public String eventType;
        public String eventDate;
        public String eventLocation;
        public String eventDuration;
        public String eventHeadName;
        public String mobileNumber;
        public String email;
        public String description;

        public Event() {}

        public Event(String eventName, String eventType, String eventDate, String eventLocation,
                     String eventDuration, String eventHeadName, String mobileNumber, String email, String description) {
            this.eventName = eventName;
            this.eventType = eventType;
            this.eventDate = eventDate;
            this.eventLocation = eventLocation;
            this.eventDuration = eventDuration;
            this.eventHeadName = eventHeadName;
            this.mobileNumber = mobileNumber;
            this.email = email;
            this.description = description;
        }
    }
}