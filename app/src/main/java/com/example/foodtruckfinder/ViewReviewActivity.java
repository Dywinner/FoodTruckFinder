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

    /*Defined TextView and RatingBar variables*/

    TextView nameText;
    TextView titleText;
    TextView descriptionText;
    TextView ratingText;
    RatingBar ratingBar;

    /* Called when clicking a Review in the RecyclerView of the ReviewFragment*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Links the layout of the Content View to the view_review xml file*/
        setContentView(R.layout.view_review);

        /*Grabs the intent used to start the activity*/
        Intent intent = getIntent();

        /*Retrieves data contained in the intent*/
        final String name = intent.getExtras().getString("name_data");
        final String description = intent.getExtras().getString("description_data");
        final int rating = intent.getExtras().getInt("rating_data");
        final String title = intent.getExtras().getString("title_data");

        /*Links the TextView with its layout in the xml file*/
        nameText = (TextView) findViewById(R.id.textView1);
        /*Sets the TextView to the value retrieved from the intent above*/
        String pie3 =  name;
        nameText.setText(pie3);

        /*Links the TextView to the xml and populates it with the title*/
        titleText = (TextView) findViewById(R.id.textView4);
        String pie4 = title;
        titleText.setText(pie4);

        /*Links the TextView to the xml and populates it with the description*/
        descriptionText = (TextView) findViewById(R.id.textView2);
        String pie2 = "Description: " + description;
        descriptionText.setText(pie2);

        /*Links the RatingBar to the xml and sets the rating to the integer rating retrieved from the intent*/
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);


    }

}
