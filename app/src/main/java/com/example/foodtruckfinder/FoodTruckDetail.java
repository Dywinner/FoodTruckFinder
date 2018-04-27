package com.example.foodtruckfinder;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FoodTruckDetail extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(getIntent().getStringExtra("name_data"));


        toolbar.setTitle("");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }



    public void openGmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        String latLng = "(" + String.valueOf(getIntent().getDoubleExtra("lat_data", 0.0)) + ", " + String.valueOf(getIntent().getDoubleExtra("long_data", 0.0)) + ")";

        emailIntent.putExtra(Intent.EXTRA_TEXT, latLng);
        startActivity(Intent.createChooser(emailIntent, null));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return either an OverviewFragment or ReviewsFragment class).
            switch (position) {
                case 0:
                    OverviewFragment of = new OverviewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name_data", getIntent().getStringExtra("name_data"));
                    bundle.putString("long_data", String.valueOf(getIntent().getDoubleExtra("long_data", 0.0)));
                    bundle.putString("lat_data", String.valueOf(getIntent().getDoubleExtra("lat_data", 0.0)));
                    of.setArguments(bundle);
                    return of;

                case 1:
                    ReviewsFragment rf = new ReviewsFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("food_truck_id_data", getIntent().getStringExtra("id_data"));
                    rf.setArguments(bundle1);
                    return rf;

                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
