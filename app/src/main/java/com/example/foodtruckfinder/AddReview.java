package com.example.foodtruckfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.foodtruckfinder.R;

public class AddReview extends AppCompatActivity {

    EditText nameText;
    EditText descriptionText;
    EditText ratingText;
    RatingBar ratingBar;
    EditText titleText;
    Float ratingfloat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameText = (EditText) findViewById(R.id.name_text);
        descriptionText = (EditText) findViewById(R.id.description_text);
        ratingText = (EditText) findViewById(R.id.rating_text);
        titleText = (EditText) findViewById(R.id.title_text);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingfloat = v;
            }
        });

        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                Integer rating = (Integer)Math.round(ratingfloat);
                String title = titleText.getText().toString();


                Intent intent = new Intent(AddReview.this, ReviewsFragment.class);
                intent.putExtra("name_data", name);
                intent.putExtra("description_data", description );
                intent.putExtra("rating_data", rating);
                intent.putExtra("title_data", title);
                setResult(1, intent);
                finish();
            }
        });

    }

}
