package com.example.foodtruckfinder;

import java.util.Calendar;

/**
 * Created by samprescott on 4/19/18.
 */

public class FoodTruck {
    private Double mLongitude;
    private Double mLatitude;

    private String mName;


    public FoodTruck() {
        this.mLongitude = 0.0;
        this.mLatitude = 0.0;
        this.mName = "";
    }

    public FoodTruck(String name, Double longitude, Double latitude) {
        this.mName = name;
        this.mLongitude = longitude;
        this.mLatitude = latitude;
    }

    public String getName() {
        return this.mName;
    }

    public Double getLatitude() {
        return this.mLatitude;
    }

    public Double getLongitutde() {
        return this.mLongitude;
    }
}
