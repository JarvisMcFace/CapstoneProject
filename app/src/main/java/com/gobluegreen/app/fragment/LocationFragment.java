package com.gobluegreen.app.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.server.http.HttpStatus;
import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.LocationsAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentLocationBinding;
import com.gobluegreen.app.to.ServiceLocationsTO;
import com.gobluegreen.app.util.NetworkUtil;
import com.gobluegreen.app.util.OkHttpHelper;
import com.gobluegreen.app.util.OkHttpHelperCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

/**
 * Created by David on 9/29/17.
 */
public class LocationFragment extends Fragment implements OkHttpHelperCallback {

    private static final int LOCATION_REQUEST_ID = 1;

    private FragmentLocationBinding locationBinding;
    private GoBluegreenApplication application;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        locationBinding = DataBindingUtil.bind(rootView);
        return locationBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (GoBluegreenApplication) getActivity().getApplication();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        locationBinding.recyclerViewLocationList.setLayoutManager(linearLayoutManager);
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;

//TODO David _ remove
//        List<LocationTO> locationTOs = new ArrayList<>();
//        LocationTO locationTO = new LocationTO();
//        locationTO.setStreet("13440 Watertown Plank Rd");
//        locationTO.setState("WI");
//        locationTO.setCity("Milwaukee");
//        locationTO.setState("WI");
//        locationTO.setPostalCode("53122");
//        locationTO.setPhoneNumber("414.944.0859");
//
//        locationTOs.add(locationTO);
//
//        LocationTO locationTO2 = new LocationTO();
//        locationTO2.setStreet("2421 S Stoughton Rd, Ste 1");
//        locationTO2.setCity("Madison");
//        locationTO2.setState("WI");
//        locationTO2.setPostalCode("53716");
//        locationTO2.setPhoneNumber("608.478.3007");
//        locationTOs.add(locationTO2);
//
//
//        LocationTO locationTO3 = new LocationTO();
//        locationTO3.setStreet("2110 Pewaukee Rd, Ste 105");
//        locationTO3.setCity("Waukesha");
//        locationTO3.setState("WI");
//        locationTO3.setPostalCode("53188");
//        locationTO3.setPhoneNumber("262.649.2082");
//        locationTOs.add(locationTO3);
//
//        LocationTO locationTO4 = new LocationTO();
//        locationTO4.setStreet("N56 W13441 Silver Spring Dr");
//        locationTO4.setCity("Menomonee Falls");
//        locationTO4.setState("WI");
//        locationTO4.setPostalCode("53051");
//        locationTO4.setPhoneNumber("262.581.9186");
//        locationTOs.add(locationTO4);
//
//        LocationTO locationTO5 = new LocationTO();
//        locationTO5.setStreet("4587 County Rd TT");
//        locationTO5.setCity("Sun Prairie");
//        locationTO4.setState("WI");
//        locationTO5.setPostalCode("53590");
//        locationTO5.setPhoneNumber("608.257.2990");
//        locationTOs.add(locationTO5);
//
//        Gson gson = new Gson();
//
//        ServiceLocationsTO serviceLocationsTO = new ServiceLocationsTO();
//        serviceLocationsTO.setLocationTOs(locationTOs);
//        String jsonString = gson.toJson(serviceLocationsTO, ServiceLocationsTO.class);

        fetchLocations();
//
        updateMap();
    }

    private void fetchLocations() {

        if (!NetworkUtil.isDeviceConnectedToNetwork(new WeakReference<Context>(getActivity()))) {
            Snackbar.make(locationBinding.getRoot(), getString(R.string.no_network_connection), Snackbar.LENGTH_SHORT).show();
            return;
        }

        String locationUrl = getString(R.string.locations_url);

        WeakReference<OkHttpHelperCallback> weakReferenceOkHttpHelperCallback = new WeakReference<OkHttpHelperCallback>(this);
        OkHttpHelper okHttpHelper = new OkHttpHelper(weakReferenceOkHttpHelperCallback, LOCATION_REQUEST_ID);
        okHttpHelper.execute(locationUrl);

    }

    private void updateMap() {

    }

    @Override
    public void performOnPostExecute(String jsonResults, int requestId, int responseCode) {

        if (HttpStatus.HTTP_OK != responseCode) {
            locationBinding.emptyState.setVisibility(View.VISIBLE);
            Snackbar.make(locationBinding.getRoot(), getString(R.string.no_network_connection), Snackbar.LENGTH_SHORT).show();
            return;
        }

        locationBinding.emptyState.setVisibility(View.GONE);
        locationBinding.showLocationsList.setVisibility(View.VISIBLE);
        if (LOCATION_REQUEST_ID == requestId) {
            application.setLocationCallSuccessful(true);
        }

        Gson gson = new Gson();
        ServiceLocationsTO serviceLocationsTO = gson.fromJson(jsonResults, ServiceLocationsTO.class);
        application.setServiceLocationsTO(serviceLocationsTO);

        loadData();
    }

    private void loadData() {
        LocationsAdapter locationsAdapter = new LocationsAdapter(application);
        locationBinding.recyclerViewLocationList.setAdapter(locationsAdapter);
    }
}
