package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/26/18.
 */

import android.arch.persistence.room.*;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.content.Context;

@android.arch.persistence.room.Database(
        entities = { FoodTruckEntity.class, ReviewEntity.class },
        version = 1)
public abstract class FoodTruckFinderDatabase extends RoomDatabase{

    public abstract FoodTruckDao getFoodTruckDao();
    public abstract ReviewDao getReviewDao();

    private static FoodTruckFinderDatabase INSTANCE;


    static FoodTruckFinderDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FoodTruckFinderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoodTruckFinderDatabase.class, "food_truck_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
