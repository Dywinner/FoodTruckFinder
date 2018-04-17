package com.example.foodtruckfiner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ViewReviewActivity extends AppCompatActivity{

    TextView nameText;
    TextView descriptionText;
    TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_review);

        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name_data");
        final String description = intent.getExtras().getString("description_data");
        final int rating = intent.getExtras().getInt("rating_data");


        nameText = (TextView) findViewById(R.id.textView1);
        String pie3 = "Author: " + name;
        nameText.setText(pie3);

        descriptionText = (TextView) findViewById(R.id.textView2);
        String pie2 = "Description: " + description;
        descriptionText.setText(pie2);

        ratingText = (TextView) findViewById(R.id.textView3);
        String pie = "rating: " + rating;
        ratingText.setText(pie);



    }

}
