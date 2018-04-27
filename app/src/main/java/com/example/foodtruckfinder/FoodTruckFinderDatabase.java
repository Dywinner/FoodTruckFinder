package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/26/18.
 */

import android.arch.persistence.room.*;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;

@android.arch.persistence.room.Database(
        entities = { FoodTruckEntity.class, ReviewEntity.class },
        version = 1)
public abstract class FoodTruckFinderDatabase extends RoomDatabase{

    public abstract FoodTruckDao getFoodTruckDao();
    public abstract ReviewDao getReviewDao();

}
