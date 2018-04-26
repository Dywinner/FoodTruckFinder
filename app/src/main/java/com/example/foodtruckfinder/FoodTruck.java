package com.example.foodtruckfinder;

import java.util.Calendar;

/**
 * Created by samprescott on 4/19/18.
 */

public class FoodTruck {
    private String mName;


    public FoodTruck() {
        this.mName = "";
    }

    public FoodTruck(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

}
