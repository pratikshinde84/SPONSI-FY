package com.example.sponsi_fy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    Button btn_sign_in;
    String email, username;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();

                                username = auth.getCurrentUser().getDisplayName();
                                email = auth.getCurrentUser().getEmail();

                                // Get Realtime Database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference userRef = database.getReference("UserDetails");

                                // Query to check if the email already exists in the database
                                Query emailQuery = userRef.orderByChild("email").equalTo(email);

                                emailQuery.get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        if (task1.getResult().exists()) {
                                            // If the email already exists, show a message
                                            Toast.makeText(AuthenticationActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // If email doesn't exist, store the user details in Realtime Database
                                            Map<String, Object> userDetails = new HashMap<>();
                                            userDetails.put("email", email);
                                            userDetails.put("username", username);

                                            // Store data under "UserDetails" with "username" as the key
                                            userRef.child(username).setValue(userDetails).addOnSuccessListener(aVoid -> {
                                                // Successfully added user details to Realtime Database
                                                Toast.makeText(AuthenticationActivity.this, "User details stored", Toast.LENGTH_SHORT).show();

                                                // Proceed to next activity
                                                Intent i = new Intent(AuthenticationActivity.this, RegisterActivity.class);
                                                startActivity(i);
                                                finish();
                                            }).addOnFailureListener(e -> {
                                                // Handle failure in storing data
                                                Toast.makeText(AuthenticationActivity.this, "Error storing user details", Toast.LENGTH_SHORT).show();
                                            });
                                        }
                                    } else {
                                        // Print the error if the query fails
                                        Log.e("AuthenticationActivity", "Error checking email: " + task1.getException().getMessage());
                                        Toast.makeText(AuthenticationActivity.this, "Error checking email: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(AuthenticationActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();
        FirebaseApp.initializeApp(this);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(AuthenticationActivity.this, options);
        auth = FirebaseAuth.getInstance();

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
    }
}
