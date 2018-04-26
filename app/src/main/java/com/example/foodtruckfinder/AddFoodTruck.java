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
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_truck);

        nameText = (EditText) findViewById(R.id.name_text);
        submit = (Button) findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameText.getText().toString();

                Intent intent = new Intent(AddFoodTruck.this, MainActivity.class);
                intent.putExtra("name_data", name);
                setResult(1, intent);
                finish();
            }
        });
    }




}
