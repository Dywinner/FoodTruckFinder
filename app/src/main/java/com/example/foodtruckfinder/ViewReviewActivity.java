package com.example.foodtruckfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.foodtruckfinder.R;

public class ViewReviewActivity extends AppCompatActivity{

    TextView nameText;
    TextView titleText;
    TextView descriptionText;
    TextView ratingText;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_review);

        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name_data");
        final String description = intent.getExtras().getString("description_data");
        final int rating = intent.getExtras().getInt("rating_data");
        final String title = intent.getExtras().getString("title_data");


        nameText = (TextView) findViewById(R.id.textView1);
        String pie3 =  name;
        nameText.setText(pie3);

        titleText = (TextView) findViewById(R.id.textView4);
        String pie4 = title;
        titleText.setText(pie4);

        descriptionText = (TextView) findViewById(R.id.textView2);
        String pie2 = "Description: " + description;
        descriptionText.setText(pie2);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);


    }

}
