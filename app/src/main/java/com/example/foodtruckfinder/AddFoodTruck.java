package com.example.foodtruckfinder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samprescott on 4/25/18.
 */

public class AddFoodTruck extends AppCompatActivity {
    private EditText nameText;
    private EditText latText;
    private EditText longText;
    private Button submit;

    static private final int TAKE_PHOTO_PERMISSION = 1;
    static private final int REQUEST_TAKE_PHOTO = 2;
    static private final int PICK_IMAGE_REQUEST = 3;

    private ImageView imageView;
    private Button takePictureButton;

    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_truck);

        nameText = (EditText) findViewById(R.id.name_text);
        latText = (EditText) findViewById(R.id.lat_text);
        longText = (EditText) findViewById(R.id.long_text);

        takePictureButton = (Button) findViewById(R.id.takePictureButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        submit = (Button) findViewById(R.id.button);

        Intent intent = this.getIntent();

        String latitude = Double.toString(intent.getDoubleExtra("lat_data", 0.0));
        String longitude = Double.toString(intent.getDoubleExtra("long_data", 0.0));

        latText.setText(latitude);
        longText.setText(longitude);

        // We are giving you the code that checks for permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, TAKE_PHOTO_PERMISSION);
        }
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture(v);
            }
        });



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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // This is called when permissions are either granted or not for the app
        // You do not need to edit this code.

        if (requestCode == TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void takePicture(View view){
        // Add code here to start the process of taking a picture
        // Note you can send an intent to the camera to take a picture...
        // So start by considering that!
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    public void getImageFromLibrary(View view) {
        // Add code here to start the process of getting a picture from the library
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Add code here to handle results from both taking a picture or pulling
        // from the image gallery.

        if (requestCode == REQUEST_TAKE_PHOTO) {
            // Add here.
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST) {
            //Add here.
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // Add other methods as necessary here
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }




}
