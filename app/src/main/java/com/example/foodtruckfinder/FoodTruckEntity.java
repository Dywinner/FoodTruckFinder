package com.example.foodtruckfinder;

import android.arch.persistence.room.*;

/**
 * Created by samprescott on 4/26/18.
 */

@Entity (tableName = "foodtruck")
public class FoodTruckEntity {

    @PrimaryKey (autoGenerate = true)
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "latitude")
    public Double latitude;

    @ColumnInfo(name = "longitude")
    public Double longitude;

    public FoodTruckEntity(String name, Double latitude, Double longitude) {
        this. name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
