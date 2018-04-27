package com.example.foodtruckfinder;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

/**
 * Created by samprescott on 4/26/18.
 */

@Entity (tableName = "foodtrucks")
public class FoodTruckEntity {

    @PrimaryKey (autoGenerate = false)
    @NonNull
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "latitude")
    public Double latitude;

    @ColumnInfo(name = "longitude")
    public Double longitude;

    @Ignore
    public FoodTruckEntity() {}

    public FoodTruckEntity(String name, Double latitude, Double longitude) {
        this. name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return ("{FoodTruckEntity: id = " + id +
                ", value = {name=" + name +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                "}");
    }
}
