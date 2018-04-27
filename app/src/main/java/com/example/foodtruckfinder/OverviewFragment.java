package com.example.foodtruckfinder;

import android.view.View;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtruckfinder.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private ImageView imageView;
    private TextView nameText;
    private TextView longitudeText;
    private TextView latitudeText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ofrag, container, false);

        nameText = (TextView) rootView.findViewById(R.id.name_text);
        String pie3 =  getArguments().getString("name_data");
        nameText.setText(pie3);

        longitudeText = (TextView) rootView.findViewById(R.id.long_text);
        String pie4 = getArguments().getString("long_data");
        longitudeText.setText(pie4);

        latitudeText = (TextView) rootView.findViewById(R.id.lat_text);
        String pie2 = getArguments().getString("lat_data");
        latitudeText.setText(pie2);

        return (rootView);

    }
}
