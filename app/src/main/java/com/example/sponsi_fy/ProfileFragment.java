package com.example.sponsi_fy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private TextView nameText, emailText, mobileText, orgText, addressText;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    Button btn_edit_profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        nameText = view.findViewById(R.id.dev_name);
        emailText = view.findViewById(R.id.dev_email);
        mobileText = view.findViewById(R.id.dev_mobile);
        orgText = view.findViewById(R.id.dev_org);
        addressText = view.findViewById(R.id.dev_addr);
        btn_edit_profile=view.findViewById(R.id.btn_edit_profile);


        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });


        // Initialize Firebase Database and SharedPreferences
        databaseReference = FirebaseDatabase.getInstance().getReference("Users_Details");
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            // Load user profile data
            loadUserProfile(username);
        } else {
            Toast.makeText(getActivity(), "Username not found. Please log in again.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void loadUserProfile(String username) {
        databaseReference.child(username).child("Profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserProfile profile = snapshot.getValue(UserProfile.class);
                            if (profile != null) {
                                nameText.setText(profile.name);
                                emailText.setText(profile.email);
                                mobileText.setText(profile.mobile);
                                orgText.setText(profile.organization);
                                addressText.setText(profile.address);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Profile data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Failed to load profile data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // UserProfile class to structure the data
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
