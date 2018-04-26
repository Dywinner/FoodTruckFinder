package com.example.foodtruckfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

/**
 * Created by samprescott on 4/25/18.
 */

public class AddFoodTruck extends AppCompatActivity {
    private EditText nameText;
    private EditText latText;
    private EditText longText;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_truck);

        nameText = (EditText) findViewById(R.id.name_text);
        latText = (EditText) findViewById(R.id.lat_text);
        longText = (EditText) findViewById(R.id.long_text);
        submit = (Button) findViewById(R.id.button);

        Intent intent = this.getIntent();

        String latitude = Double.toString(intent.getDoubleExtra("lat_data", 0.0));
        String longitude = Double.toString(intent.getDoubleExtra("long_data", 0.0));

        latText.setText(latitude);
        longText.setText(longitude);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String latitude = latText.getText().toString();
                String longitude = longText.getText().toString();

                Intent intent = new Intent(AddFoodTruck.this, MainActivity.class);
                intent.putExtra("name_data", name);
                intent.putExtra("lat_data", latitude);
                intent.putExtra("long_data", longitude);
                setResult(1, intent);
                finish();
            }
        });
    }




}
