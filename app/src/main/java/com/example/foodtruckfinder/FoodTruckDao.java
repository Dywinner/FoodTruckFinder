package com.example.foodtruckfinder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by samprescott on 4/26/18.
 */

@Dao
public interface FoodTruckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FoodTruck foodTruck);

    @Query("SELECT * FROM foodtrucks")
    List<FoodTruckEntity> getLocalFoodTrucks();

}
