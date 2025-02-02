package com.example.sponsi_fy;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView btm_nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        btm_nav_view = findViewById(R.id.bottom_nav_view);

        // Set default fragment (e.g., HomeFragment)
        loadFragment(new HomeFragment());

        // Set listener for BottomNavigationView item selection
        btm_nav_view.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_fragment) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.add_fragment) {
                loadFragment(new AddFragment());
                return true;
            } else if (itemId == R.id.profile_fragment) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });

        // Handle window insets (for edge-to-edge UI)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Helper method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace the fragment container with the new fragment
        transaction.commit();
    }
}