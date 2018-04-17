package com.example.foodtruckfiner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddReview extends AppCompatActivity {

    EditText nameText;
    EditText descriptionText;
    EditText ratingText;
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
        btn = (Button) findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String rating2 = ratingText.getText().toString();
                int rating = Integer.parseInt(rating2);



                Intent intent = new Intent(AddReview.this, ReviewsFragment.class);
                intent.putExtra("name_data", name);
                intent.putExtra("description_data", description );
                intent.putExtra("rating_data", rating);
                setResult(1, intent);
                finish();
            }
        });

    }

}