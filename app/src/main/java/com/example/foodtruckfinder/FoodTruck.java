package com.example.foodtruckfinder;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Calendar;

/**
 * Created by samprescott on 4/19/18.
 */

@IgnoreExtraProperties
public class FoodTruck {

    private String name;
    @Exclude
    private Double latitude;
    @Exclude
    private Double longitude;

    public FoodTruck() {
    }

    public FoodTruck(String name) {
        this.name = name;
    }

    public FoodTruck(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Double getLatitude() {
        return latitude;
    }

    @Exclude
    public Double getLongitude() {
        return longitude;
    }

    @Exclude
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Exclude
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
