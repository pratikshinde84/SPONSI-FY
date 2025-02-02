package com.example.sponsi_fy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, mobileInput, orgInput, addressInput;
    private Button submitButton;

    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        mobileInput = findViewById(R.id.mobile_input);
        orgInput = findViewById(R.id.org_input);
        addressInput = findViewById(R.id.address_input);
        submitButton = findViewById(R.id.btn_edit_profile);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users_Details");

        // Access SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            // Load existing user profile data and populate EditTexts
            loadUserProfile(username);
        } else {
            Toast.makeText(EditProfileActivity.this, "Username not found. Please log in again.", Toast.LENGTH_SHORT).show();
        }

        submitButton.setOnClickListener(v -> {
            if (username != null) {
                storeUserData(username);
            } else {
                Toast.makeText(EditProfileActivity.this, "Username not found. Please log in again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserProfile(String username) {
        databaseReference.child(username).child("Profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserProfile profile = snapshot.getValue(UserProfile.class);
                            if (profile != null) {
                                nameInput.setText(profile.name);
                                emailInput.setText(profile.email);
                                mobileInput.setText(profile.mobile);
                                orgInput.setText(profile.organization);
                                addressInput.setText(profile.address);
                            }
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Profile data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditProfileActivity.this, "Failed to load profile data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeUserData(String username) {
        // Get input data from EditTexts
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String mobile = mobileInput.getText().toString().trim();
        String org = orgInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile)
                || TextUtils.isEmpty(org) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a UserProfile object
        UserProfile profile = new UserProfile(name, email, mobile, org, address);

        // Save data to Firebase
        databaseReference.child(username).child("Profile").setValue(profile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class); // Assuming ProfileFragment is part of MainActivity
                    intent.putExtra("fragment", "ProfileFragment"); // Optional: pass any flag or extra to indicate which fragment to display
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public static class UserProfile {
        public String name;
        public String email;
        public String mobile;
        public String organization;
        public String address;

        // Default constructor is required for calls to DataSnapshot.getValue(UserProfile.class)
        public UserProfile() {
        }

        public UserProfile(String name, String email, String mobile, String organization, String address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.organization = organization;
            this.address = address;
        }
    }
}
