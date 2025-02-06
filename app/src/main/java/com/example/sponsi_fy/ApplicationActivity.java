package com.example.sponsi_fy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApplicationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicationAdapter adapter;
    private List<Application> applicationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        recyclerView = findViewById(R.id.recyclerView_applications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApplicationAdapter(applicationList);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users_Details")
                .child(username).child("Applications");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                applicationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Application application = snapshot.getValue(Application.class);
                    if (application != null) {
                        applicationList.add(application);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors here
            }
        });
    }
}
