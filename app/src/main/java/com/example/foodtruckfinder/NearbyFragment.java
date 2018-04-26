package com.example.foodtruckfinder;

import android.view.View;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.foodtruckfinder.R;

import java.util.ArrayList;

public class NearbyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nfrag, container, false);
        return (rootView);
    }
}
