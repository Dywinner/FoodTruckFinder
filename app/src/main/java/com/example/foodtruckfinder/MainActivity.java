package com.example.foodtruckfinder;

import android.arch.persistence.room.Database;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MainActivity extends AppCompatActivity implements
        OnCameraMoveStartedListener,
        OnCameraMoveListener,
        OnCameraIdleListener,
        OnMapLongClickListener,
        OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;

    private CameraPosition mCameraPosition;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private FloatingActionButton mFab;
    private DatabaseReference mDatabase;

    private DatabaseGenerator dbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addFoodTruck(view)) {
                    Snackbar.make(view, "Added Food Truck Location", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Could not add Food Truck Location", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        //used to build database
        dbg = new DatabaseGenerator(getApplication());




    }

    private boolean addFoodTruck(View view) {

        if(mLastKnownLocation == null) {
            return false;
        }

        Intent intent = new Intent(this, AddFoodTruck.class);
        intent.putExtra("lat_data", mLastKnownLocation.getLatitude());
        intent.putExtra("long_data", mLastKnownLocation.getLongitude());
        startActivityForResult(intent, 1);


        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) { //addFoodTruck
            String name = data.getStringExtra("name_data");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            FoodTruck truck = new FoodTruck(name);

            String userId = ref.child("foodtrucks").push().getKey();
            ref.child("foodtrucks").child(userId).setValue(truck);
            GeoFire geoFire = new GeoFire(ref.child("geofire"));

            String latitude = data.getStringExtra("lat_data");
            String longitude = data.getStringExtra("long_data");

            //must use this constructor, does not work without CompletionListener
            geoFire.setLocation(userId,
                    new GeoLocation(Double.parseDouble(latitude), Double.parseDouble(longitude)),
                    new GeoFire.CompletionListener() {

                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error != null) {
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                            } else {
                                System.out.println("Location saved on server successfully!");
                            }
                        }

                    });

            placeLocalMarkers();

            //mDatabase.child("foodtrucks").push().setValue(truck);
        }

        if(resultCode == 2) {


        }
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        final LatLng tempLatLng = latLng;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent intent = new Intent(MainActivity.this, AddFoodTruck.class);
                        intent.putExtra("lat_data", tempLatLng.latitude);
                        intent.putExtra("long_data", tempLatLng.longitude);
                        startActivityForResult(intent, 2);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Add new Food Truck here?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();



    }


    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                FoodTruckEntity temp = (FoodTruckEntity) marker.getTag();
                Intent intent = new Intent(MainActivity.this, FoodTruckDetail.class);
                intent.putExtra("id_data", temp.id); // used to associate reviews
                intent.putExtra("name_data", temp.name);
                intent.putExtra("lat_data", temp.latitude);
                intent.putExtra("long_data", temp.longitude);

                startActivity(intent);
            }
        });

        mMap.setOnMapLongClickListener(this);

        // Prompt the user for permission.
        while(!mLocationPermissionGranted) {
            getLocationPermission();
        }

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        GeoLocation geoLocation = new GeoLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
        getLocalTrucks(geoLocation, getCameraRadius());

        placeLocalMarkers();

    }

    /**
     * Function: getCameraRadius
     *
     * @return returns radius in kilometers
     *
     */
    private float getCameraRadius() {
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        LatLng farRight = visibleRegion.farRight;
        LatLng farLeft = visibleRegion.farLeft;
        LatLng nearRight = visibleRegion.nearRight;
        LatLng nearLeft = visibleRegion.nearLeft;

        float[] distanceWidth = new float[2];
        Location.distanceBetween(
                (farRight.latitude+nearRight.latitude)/2,
                (farRight.longitude+nearRight.longitude)/2,
                (farLeft.latitude+nearLeft.latitude)/2,
                (farLeft.longitude+nearLeft.longitude)/2,
                distanceWidth
        );


        float[] distanceHeight = new float[2];
        Location.distanceBetween(
                (farRight.latitude+nearRight.latitude)/2,
                (farRight.longitude+nearRight.longitude)/2,
                (farLeft.latitude+nearLeft.latitude)/2,
                (farLeft.longitude+nearLeft.longitude)/2,
                distanceHeight
        );



        float distance = 0;

        if (distanceWidth[0]>distanceHeight[0]){
            distance = distanceWidth[0];
        } else {
            distance = distanceHeight[0];
        }

        return distance/2000;
    }


    private void getLocalTrucks(GeoLocation geoLocation, float radius) {


        GeoFire geoFire= new GeoFire(FirebaseDatabase.getInstance().getReference().child("geofire"));
        GeoQuery geoQuery = geoFire.queryAtLocation(geoLocation, radius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, final GeoLocation location) {
                //key has entered search area, add it to local list

                // check local list size, if too large, replace random marker
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("foodtrucks");

                Query query = ref.orderByKey().limitToFirst(20);
                query.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FoodTruckEntity test = dataSnapshot.child(key).getValue(FoodTruckEntity.class);
                        test.latitude = location.latitude;
                        test.longitude = location.longitude;
                        test.id = key;
                        //write to local database from firebase
                        dbg.insertFoodTruckEntity(test);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });


                }
            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });



    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int reason) {

    }

    private void placeLocalMarkers() {
        List<FoodTruckEntity> foodTruckEntityList = new ArrayList<>();

        try {
            foodTruckEntityList = dbg.getFoodTruckEntityList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        for(FoodTruckEntity truckEntity: foodTruckEntityList) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(truckEntity.latitude, truckEntity.longitude))
                    .title(truckEntity.name)
            ).setTag(truckEntity);
        }
    }

    @Override
    public void onCameraIdle() {
        // clear database
        dbg.clearDatabase();

        GeoLocation geoLocation = new GeoLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);

        getLocalTrucks(geoLocation, getCameraRadius());

        placeLocalMarkers();

    }

    public ReviewEntity[] pullReviews() {
        ReviewEntity[] reviewEntities = new ReviewEntity[20];
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//
//
//        Query query = ref.child("reviews").orderByKey().equalTo(food_truck_id).limitToFirst(20);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                System.out.println("reviews found!");
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // ...
//            }
//        });


        return reviewEntities;
    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    updateLocationUI();
                }
            }
        }

    }


    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                System.out.println("this might be a problem");
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
