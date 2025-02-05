package com.example.sponsi_fy;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    CardView card_tech,card_health,card_govt,card_media,card_sports,card_education;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        card_education=view.findViewById(R.id.card_education);
        card_govt=view.findViewById(R.id.card_government);
        card_health=view.findViewById(R.id.card_healthcare);
        card_tech=view.findViewById(R.id.card_technology);
        card_media=view.findViewById(R.id.card_media);
        card_sports=view.findViewById(R.id.card_sports);

        setCardClickListener(card_education, "Education");
        setCardClickListener(card_govt, "Government");
        setCardClickListener(card_health, "Healthcare");
        setCardClickListener(card_tech, "Technology");
        setCardClickListener(card_media, "Media");
        setCardClickListener(card_sports, "Sports");

        return view;
    }
    private void setCardClickListener(CardView card, String dataToPass) {
        card.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PostActivity.class);
            intent.putExtra("CARD_DATA", dataToPass);
            startActivity(intent);
        });
    }


}