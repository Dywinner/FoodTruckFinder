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

/*Called when the user wants to add a new Review*/

public class AddReview extends AppCompatActivity {

    EditText nameText;
    EditText descriptionText;
    EditText ratingText;
    RatingBar ratingBar;
    EditText titleText;
    Float ratingfloat;
    Button btn;

    /*Called at the start of the activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Calls the onCreate of the superclass Activity to avoid unwanted behavior*/
        super.onCreate(savedInstanceState);
        /*Links the layout of the Activity to the activity_add_review it*/
        setContentView(R.layout.activity_add_review);

        /*Creates toolbar and links it to its XML*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Links EditText to its XML layout*/

        nameText = (EditText) findViewById(R.id.name_text);
        descriptionText = (EditText) findViewById(R.id.description_text);
        titleText = (EditText) findViewById(R.id.title_text);
        /*Initializes the RatingBar with a rating of two*/
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingfloat = 2.0f;

        /*RatingBar event listener which changes the rating whenever the RatingBar is clicked*/

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingfloat = v;
            }
        });

        /*Registers a button that sends the data from the EditText to the Review Fragment*/
        btn = (Button) findViewById(R.id.button);


        /*OnClickListener for the Button*/
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                Integer rating = (Integer)Math.round(ratingfloat);
                String title = titleText.getText().toString();

                /*Takes the data from the EditText and bundles it into an intent then sends the intent to the ReviewFragmentClass*/

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
