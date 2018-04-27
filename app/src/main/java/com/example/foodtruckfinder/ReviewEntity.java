package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/26/18.
 */

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "reviews",
        foreignKeys = @ForeignKey(
                entity = FoodTruckEntity.class,
                parentColumns = "id",
                childColumns = "food_truck_id", onDelete = CASCADE
        ),
        indices = {@Index("food_truck_id")}
)
public class ReviewEntity {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "rating")
    public int rating;

    @ColumnInfo(name = "food_truck_id")
    public String foodTruckId;

    public ReviewEntity(String name, String title, String description, int rating) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

}
