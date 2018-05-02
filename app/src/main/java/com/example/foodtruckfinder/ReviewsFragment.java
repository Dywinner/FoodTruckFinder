package com.example.foodtruckfinder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtruckfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ReviewsFragment extends Fragment {
    ArrayList<Review> items;
    List<ReviewEntity> reviewEntityList;
    RecyclerView rvItems;
    DatabaseGenerator dbg;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rfrag, container, false);
        rvItems = (RecyclerView) rootView.findViewById(R.id.reviewItems);

        /*Takes the foodtruck id from the intent*/
        String id = getArguments().getString("food_truck_id_data");

        System.out.println("from fragment: " + id);

        dbg = new DatabaseGenerator(this.getActivity().getApplication());

        /*Retrieves all Reviews in the database associated with the id*/
        List<ReviewEntity> reviewEntities = null;
        try {
            reviewEntities = dbg.getReviewEntityList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        items = new ArrayList<>();

        /*Populates a Review Object with the data retrieved from the Database*/
        for(ReviewEntity reviewEntity: reviewEntities) {

            Review review = new Review(reviewEntity.name, reviewEntity.title, reviewEntity.description, reviewEntity.rating);
            items.add(review);
        }


        /* Create adapter passing in the sample user data*/
        ReviewAdapter adapter = new ReviewAdapter(this.getContext(), items);
        /* Attach the adapter to the recyclerview to populate items*/
        rvItems.setAdapter(adapter);
        /*Set layout manager to position the items*/
        rvItems.setLayoutManager(new LinearLayoutManager(this.getContext()));

        /*Floating action button to add a review*/

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview(view);
            }
        });
        return (rootView);

    }

    /*Linked to the Flotaing action button above and called on a Click event*/

    public void addReview(View view) {
        Intent intent = new Intent(this.getContext(), AddReview.class);
        startActivityForResult(intent, 1);
    }

    /*Used to retrieve intent data from the add Review Activity */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Check which request we're responding to */
        if (requestCode == 1) {
            if (resultCode == 1) {
                // Make sure the request was successful
                String name = data.getExtras().getString("name_data");
                String description = data.getExtras().getString("description_data");
                Integer rating = data.getExtras().getInt("rating_data");
                String title = data.getExtras().getString("title_data");
                Review review = new Review(name,title, description, rating);

                /*Inserts Review entity into the Firebase Database and gives it a unique key*/

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                String id = ref.child("reviews").child("-LB-YIRZg77QJdhrKy4L").push().getKey();
                ref.child("reviews").child("-LB-YIRZg77QJdhrKy4L").child(id).setValue(review);

                /*Inserts Review object into the Review Bucket List*/
                items.add(review);
                rvItems.getAdapter().notifyDataSetChanged();
            }

        }

    }

}
