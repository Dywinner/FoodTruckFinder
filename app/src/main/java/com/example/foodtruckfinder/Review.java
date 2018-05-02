package com.example.foodtruckfinder;

import android.support.design.internal.ParcelableSparseArray;

import java.util.ArrayList;

/*Review class used to store user input from the AddReview Activity*/

public class Review extends ParcelableSparseArray {

    /*Member Variables*/

    private String mName;
    private String mTitle;
    private String mDescription;
    private int mRating;

    /*Constructor*/

    public Review(String name, String title, String description, int rating) {
        mName = name;
        mTitle = title;
        mDescription = description;
        mRating = rating;


    }

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        mName = name;
    }
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        mTitle = title;
    }

    public String getmDescription() {
        return mDescription;
    }
    public void setmDescription(String description) {
        mDescription = description;
    }

    public Integer getmRating(){ return mRating;}

    public void setmRating(Integer rating) {mRating = rating;}

    /*Method to create a sample list of Reviews. Used to test of the Recyler list can load the list of items */

    public static ArrayList<Review> createInitialBucketList(int numItems) {
        ArrayList<Review> reviewItems = new ArrayList<Review>();

        for (int i = 1; i <= numItems; i++) {
            reviewItems.add(new Review("Dylan Nguyen", "Review Title","This will be the final message from Saigon station. It has been a long fight and we have lost it.…Those who fail to learn from history are forced to repeat it. Let us hope that we will not have another Vietnam experience and that we have learned our lesson. Saigon signing off.", 0));
        }
        return reviewItems;
    }

}
