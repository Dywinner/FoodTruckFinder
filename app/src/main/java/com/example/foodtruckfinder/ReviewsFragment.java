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

        String id = getArguments().getString("food_truck_id_data");

        System.out.println("from fragment: " + id);

        dbg = new DatabaseGenerator(this.getActivity().getApplication());

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

        for(ReviewEntity reviewEntity: reviewEntities) {

            Review review = new Review(reviewEntity.name, reviewEntity.title, reviewEntity.description, reviewEntity.rating);
            items.add(review);
        }



        // Initialize items
        // Create adapter passing in the sample user data
        ReviewAdapter adapter = new ReviewAdapter(this.getContext(), items);
        // Attach the adapter to the recyclerview to populate items
        rvItems.setAdapter(adapter);
        // Set layout manager to position the items
        rvItems.setLayoutManager(new LinearLayoutManager(this.getContext()));

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview(view);
            }
        });
        return (rootView);
    }

    public void addReview(View view) {
        Intent intent = new Intent(this.getContext(), AddReview.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if (resultCode == 1) {
                // Make sure the request was successful
                String name = data.getExtras().getString("name_data");
                String description = data.getExtras().getString("description_data");
                Integer rating = data.getExtras().getInt("rating_data");
                String title = data.getExtras().getString("title_data");
                Review review = new Review(name,title, description, rating);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                String id = ref.child("reviews").child("-LB-YIRZg77QJdhrKy4L").push().getKey();
                ref.child("reviews").child("-LB-YIRZg77QJdhrKy4L").child(id).setValue(review);


                items.add(review);
                rvItems.getAdapter().notifyDataSetChanged();
            }

        }

    }

}
