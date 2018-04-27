package com.example.foodtruckfinder;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by samprescott on 4/26/18.
 */

public interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Review review);

    @Query("SELECT * FROM reviews ORDER BY id")
    List<ReviewEntity> getRecentReviews();

    @Query("SELECT * FROM reviews WHERE food_truck_id=:food_truck_id")
    List<ReviewEntity> getFoodTruckReviews(final int food_truck_id);

}
