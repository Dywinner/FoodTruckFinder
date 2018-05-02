package com.example.foodtruckfinder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/*Review Database object for the SQLite Database*/

@Dao
public interface ReviewDao {

    /*Associates a SQL command with a insert Java method*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReviewEntity... reviewEntity);

     /*Associates a SQL command with a getRecentReviews Java method*/
    @Query("SELECT * FROM reviews ORDER BY id")
    List<ReviewEntity> getRecentReviews();

     /*Associates a SQL command with a getFoodTruckReviews Java method*/
    @Query("SELECT * FROM reviews WHERE food_truck_id= :food_truck_id")
    List<ReviewEntity> getFoodTruckReviews(final String food_truck_id);

     /*Associates a SQL command with a delete java method*/
    @Query("DELETE FROM reviews")
    void deleteAll();

}
