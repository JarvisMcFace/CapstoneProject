package com.gobluegreen.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.databinding.FragmentLocationBinding;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by David on 9/29/17.
 */
public class LocationFragment extends Fragment {

    private FragmentLocationBinding locationBinding;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        locationBinding = DataBindingUtil.bind(rootView);
        return locationBinding.getRoot();
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateMap();
    }

    private void updateMap() {

    }
}
