package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/26/18.
 */

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;
/*Defines a database entity for the Review Object*/

@Entity(tableName = "reviews",
        /*Foreign key definition for the Review Entity */
        foreignKeys = @ForeignKey(
                entity = FoodTruckEntity.class,
                parentColumns = "id",
                childColumns = "food_truck_id", onDelete = CASCADE
        ),
        indices = {@Index("food_truck_id")}
)
public class ReviewEntity {

    /*Columns for the Review Entity object in the table*/

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

    @Ignore
    public ReviewEntity() {

    }

    /*Constructor for the Review Entity*/
    public ReviewEntity(String name, String title, String description, int rating) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    /*toString method*/
    @Override
    public String toString() {
        return "{ReviewEntity: id=" + id + " " +
                "{food_truck_id=" + foodTruckId +
                ", name=" + name +
                ", title=" + title +
                ", description" + description +
                ", rating" + rating +
                "} }";
    }

}
