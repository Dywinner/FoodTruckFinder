package com.example.foodtruckfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodtruckfinder.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


        // Store a member variable for the contacts
        private static List<Review> rItems;
        // Store the context for easy access
        public Context mContext;

        // Pass in the contact array into the constructor
        public ReviewAdapter(Context context, List<Review> items) {
            rItems = items;
            mContext = context;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public TextView descTextView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.item_name);
                descTextView = (TextView) itemView.findViewById(R.id.item_desc);
                //checkbox goes here



                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ViewReviewActivity.class);
                        Log.println(Log.ASSERT, "hello" + getItemId(), "hello");
                        intent.putExtra("name_data", rItems.get(getAdapterPosition()).getmName());
                        intent.putExtra("title_data", rItems.get(getAdapterPosition()).getmTitle());
                        intent.putExtra("description_data", rItems.get(getAdapterPosition()).getmDescription());
                        intent.putExtra("rating_data", rItems.get(getAdapterPosition()).getmRating());
                        intent.putExtra("pos_data", getAdapterPosition());
                        ((Activity) v.getContext()).startActivity(intent);
                    }
                });

            }

        }






        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View bucketItemView = inflater.inflate(R.layout.review, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(bucketItemView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ReviewAdapter.ViewHolder viewHolder, final int position) {
            // Get the data model based on position
            Review item = rItems.get(position);



            // Set item views based on your views and data model
            TextView textView1 = viewHolder.nameTextView;
            textView1.setText(item.getmName());
            textView1.setClickable(true);
            textView1.setActivated(false);
            textView1.setEnabled(false);


            TextView textView2 = viewHolder.descTextView;
            textView2.setText(item.getmTitle());
            textView2.setClickable(false);
            textView2.setActivated(false);
            textView2.setEnabled(false);



        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return rItems.size();
        }

    }



