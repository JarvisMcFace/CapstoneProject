package com.gobluegreen.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.databinding.FragmentLocationBinding;
import com.gobluegreen.app.to.LocationTO;
import com.gobluegreen.app.to.ServiceLocationsTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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


        List<LocationTO> locationTOs = new ArrayList<>();
        LocationTO locationTO = new LocationTO();
        locationTO.setStreet("13440 Watertown Plank Rd");
        locationTO.setCity("Milwaukee");
        locationTO.setStreet("WI");
        locationTO.setPostalCode("53122");
        locationTO.setPhoneNumber("414.944.0859");

        locationTOs.add(locationTO);

        LocationTO locationTO2 = new LocationTO();
        locationTO2.setStreet("2421 S Stoughton Rd, Ste 1");
        locationTO2.setCity("Madison");
        locationTO2.setStreet("WI");
        locationTO2.setPostalCode("53716");
        locationTO2.setPhoneNumber("608.478.3007");
        locationTOs.add(locationTO2);


        LocationTO locationTO3 = new LocationTO();
        locationTO3.setStreet("2110 Pewaukee Rd, Ste 105");
        locationTO3.setCity("Waukesha");
        locationTO3.setStreet("WI");
        locationTO3.setPostalCode("53188");
        locationTO3.setPhoneNumber("262.649.2082");
        locationTOs.add(locationTO3);

        LocationTO locationTO4 = new LocationTO();
        locationTO4.setStreet("N56 W13441 Silver Spring Dr");
        locationTO4.setCity("Menomonee Falls");
        locationTO4.setStreet("WI");
        locationTO4.setPostalCode("53051");
        locationTO4.setPhoneNumber("262.581.9186");
        locationTOs.add(locationTO4);

        LocationTO locationTO5 = new LocationTO();
        locationTO5.setStreet("4587 County Rd TT");
        locationTO5.setCity("Sun Prairie");
        locationTO4.setStreet("WI");
        locationTO5.setPostalCode("53590");
        locationTO5.setPhoneNumber("608.257.2990");
        locationTOs.add(locationTO5);

        Gson gson = new Gson();

        ServiceLocationsTO serviceLocationsTO = new ServiceLocationsTO();
        serviceLocationsTO.setLocationTOs(locationTOs);
        String jsonString = gson.toJson(serviceLocationsTO, ServiceLocationsTO.class);


        updateMap();
    }

    private void updateMap() {

    }
}
