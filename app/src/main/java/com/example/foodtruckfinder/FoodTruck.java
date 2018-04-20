package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/19/18.
 */

public class FoodTruck {
    public Double longitude;
    public Double latitude;

    public String name;

    public FoodTruck() {
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.name = "";

    }

    public FoodTruck(String name, Double longitude, Double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
