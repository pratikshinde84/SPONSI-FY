package com.example.sponsi_fy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    CardView cv_cardinfo;
    TextView tv_cardtitle, tv_newuser, tv_tc;
    EditText et_username, et_password;
    CheckBox chk_show;
    Button btn_login;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login page");

        // Initialize views
        cv_cardinfo = findViewById(R.id.cv_login_cardview);
        tv_cardtitle = findViewById(R.id.tv_login_textinfo);
        tv_newuser = findViewById(R.id.tv_login_newuser);
        et_username = findViewById(R.id.et_login_username);
        et_password = findViewById(R.id.et_login_password);
        chk_show = findViewById(R.id.login_showhide);
        btn_login = findViewById(R.id.btn_login);
        tv_tc = findViewById(R.id.tv_tc);

        // Firebase reference
        reference = FirebaseDatabase.getInstance().getReference("Users_Details");

        // Check if user is already logged in
        checkIfUserIsLoggedIn();

        // Show/hide password
        chk_show.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        // New user sign-up
        tv_newuser.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, AuthenticationActivity.class);
            startActivity(i);
        });

        // Terms and conditions
        tv_tc.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/"));
            startActivity(i);
        });

        // Login button click
        btn_login.setOnClickListener(view -> {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(username, password);
            }
        });
    }

    private void checkIfUserIsLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLogin", false);

        if (isLoggedIn) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void loginUser(String username, String password) {
        reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("pass").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(password)) {

                        // Store login status in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putBoolean("isLogin", true); // Set isLogin to true
                        editor.apply();

                        // Navigate to MainActivity
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
