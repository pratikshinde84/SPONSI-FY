package com.example.sponsi_fy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
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



        btm_nav_view = findViewById(R.id.bottom_nav_view);

        loadFragment(new HomeFragment());

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.about_us){
            Intent i=new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(i);
        }else if(item.getItemId()==R.id.contact_us) {
            Intent i=new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(i);
        }else if(item.getItemId()==R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogin", false);
            editor.apply();
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }else if(item.getItemId()==R.id.settings) {
            Intent i=new Intent(MainActivity.this,Settings_Activity.class);
            startActivity(i);
        } else if(item.getItemId()==R.id.feedback) {
            Intent i=new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(i);
        } else if(item.getItemId()==R.id.apply){
            Intent i=new Intent(MainActivity.this, RequestsActivity.class);
            startActivity(i);
        } else if(item.getItemId()==R.id.requests){
            Intent i=new Intent(MainActivity.this, ApplicationActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



    // Helper method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace the fragment container with the new fragment
        transaction.commit();
    }
}