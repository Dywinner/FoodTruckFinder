package com.example.foodtruckfiner;

import android.support.design.internal.ParcelableSparseArray;

import java.util.ArrayList;

public class Review extends ParcelableSparseArray {

    private String mName;
    private String mDescription;
    private int mRating;

    public Review(String name, String description, int rating) {
        mName = name;
        mDescription = description;
        mRating = rating;


    }

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        mName = name;
    }

    public String getmDescription() {
        return mDescription;
    }
    public void setmDescription(String description) {
        mDescription = description;
    }

    public Integer getmRating(){ return mRating;}

    public void setmRating(Integer rating) {mRating = rating;}

    public static ArrayList<Review> createInitialBucketList(int numItems) {
        ArrayList<Review> reviewItems = new ArrayList<Review>();

        for (int i = 1; i <= numItems; i++) {
            reviewItems.add(new Review("Dylan Nguyen", "This will be the final message from Saigon station. It has been a long fight and we have lost it.…Those who fail to learn from history are forced to repeat it. Let us hope that we will not have another Vietnam experience and that we have learned our lesson. Saigon signing off.", 0));
        }
        return reviewItems;
    }

}
