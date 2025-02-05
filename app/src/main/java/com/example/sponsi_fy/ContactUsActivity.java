package com.example.sponsi_fy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactUsActivity extends AppCompatActivity {
    CardView email_card,phone_card,instagram_card,github_card,linkedin_card,leetcode_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);
        email_card=findViewById(R.id.card_email);
        phone_card=findViewById(R.id.card_phone);
        instagram_card=findViewById(R.id.card_insta);
        github_card=findViewById(R.id.card_git);
        linkedin_card=findViewById(R.id.card_linked_in);
        leetcode_card=findViewById(R.id.card_leet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupCardListeners();
    }

    private void setupCardListeners() {


        email_card.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:pratik216029@gmail.com")); // Replace with your email
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });


        phone_card.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:9284846391")); // Replace with your phone number
            startActivity(callIntent);
        });

        instagram_card.setOnClickListener(v -> {
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mr.pratik_deshmukh_/")); // Replace with your Instagram link
            startActivity(instaIntent);
        });

        leetcode_card.setOnClickListener(v -> {
            Intent leetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://leetcode.com/u/pratik216029/")); // Replace with your LeetCode link
            startActivity(leetIntent);
        });

        github_card.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/pratikshinde84")); // Replace with your GitHub link
            startActivity(githubIntent);
        });

        linkedin_card.setOnClickListener(v -> {
            Intent linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/pratik-shinde-2803612ab/")); // Replace with your LinkedIn link
            startActivity(linkedInIntent);
        });
    }
}
