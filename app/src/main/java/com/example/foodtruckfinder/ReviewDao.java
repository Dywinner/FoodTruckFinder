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

    @Query("SELECT * FROM reviews ORDER BY ")
    List<FoodTruck> getRecentReviews();

}
