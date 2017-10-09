package com.gobluegreen.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.facebook.stetho.server.http.HttpStatus;
import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.LocationsAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentLocationBinding;
import com.gobluegreen.app.to.Coordinates;
import com.gobluegreen.app.to.LocationTO;
import com.gobluegreen.app.to.ServiceLocationsTO;
import com.gobluegreen.app.util.IsRequestedPackageInstalled;
import com.gobluegreen.app.util.ListUtils;
import com.gobluegreen.app.util.NetworkUtil;
import com.gobluegreen.app.util.OkHttpHelper;
import com.gobluegreen.app.util.OkHttpHelperCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 9/29/17.
 */
public class LocationFragment extends Fragment implements OkHttpHelperCallback, LocationsCallBack {

    private String TAG = LocationFragment.class.getSimpleName();
    private static final int LOCATION_REQUEST_ID = 1;

    private FragmentLocationBinding locationBinding;
    private GoBluegreenApplication application;
    private GoogleMap googleMap;
    private int bottomSheetPeekHeight;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayoutManager linearLayoutManager;
    private List<LocationTO> locationTOs;
    private List<Marker> markers;
    private Map<Marker, LocationTO> markerLocationMap;
    private boolean isListOpen;

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

        linearLayoutManager = new LinearLayoutManager(getActivity());
        locationBinding.recyclerViewLocationList.setLayoutManager(linearLayoutManager);

        initBottomSheet();
        initBottomShowListButton();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
        fetchLocations();
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

    @Override
    public void moveToLocation(LocationTO locationTO) {

        Log.d(TAG, "David: " + "moveToLocation() called with: locationTO = [" + locationTO + "]");
        Marker selectedMarker = getSelectedMarker(locationTO);
        selectedMarker.showInfoWindow();
        LatLng markerPosition = selectedMarker.getPosition();
        LatLng offsetPosition = new LatLng(markerPosition.latitude, markerPosition.longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(offsetPosition, 15));
    }

    private Marker getSelectedMarker(LocationTO selectedLocationTO) {

        for (Map.Entry<Marker, LocationTO> entry : markerLocationMap.entrySet()) {
            Marker keyMarker = entry.getKey();
            LocationTO locationTO = entry.getValue();

            if (selectedLocationTO.getCity().equalsIgnoreCase(locationTO.getCity())) {
                return keyMarker;
            }

        }
        return null;
    }

    @Override
    public void startGoogleMaps(LocationTO locationTO) {

        String googleMapsPackageURI = "com.google.android.apps.maps";

        WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(getActivity());
        boolean isGoogleMapsInstalled = IsRequestedPackageInstalled.execute(activityWeakReference, googleMapsPackageURI);

        if (isGoogleMapsInstalled) {
            String latitude = String.valueOf(locationTO.getCoordinates().getLatitude());
            String longitude = String.valueOf(locationTO.getCoordinates().getLongitude());

            Uri uri = Uri.parse("google.navigation:q="+ latitude + "," + longitude);
            Intent intent= new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            locationBinding.bottomSheetLocations.setVisibility(View.GONE);
            int accentColor = ContextCompat.getColor(getActivity(), R.color.white);
            Snackbar.make(locationBinding.getRoot(), getString(R.string.get_google_maps), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.get), getSnackBarOnClickListener(googleMapsPackageURI))
                    .setActionTextColor(accentColor)
                    .addCallback(new Snackbar.Callback() {

                        @Override
                        public void onShown(Snackbar sb) {
                            super.onShown(sb);
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            locationBinding.bottomSheetLocations.setVisibility(View.VISIBLE);
                        }
                    })
                    .show();
        }
    }

    private View.OnClickListener getSnackBarOnClickListener(final String googleMapPackageUri) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_VIEW);
                intent.setData(Uri.parse(("https://play.google.com/store/apps/details?id=" + googleMapPackageUri)));
                startActivity(intent);
            }
        };
    }
    private void initBottomSheet() {
        Resources resources = application.getResources();
        bottomSheetPeekHeight = resources.getDimensionPixelOffset(R.dimen.locations_bottom_sheet_height);
        bottomSheetBehavior = BottomSheetBehavior.from(locationBinding.bottomSheetLocations);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setPeekHeight(bottomSheetPeekHeight);

    }


    private void initBottomShowListButton() {
        locationBinding.showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(application, R.anim.slide_out_down);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        //left empty
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isListOpen = true;
                        bottomSheetBehavior.setPeekHeight(bottomSheetPeekHeight);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() != -1) {
                            try {
                                locationBinding.recyclerViewLocationList.smoothScrollToPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
                            } catch (IllegalArgumentException e) {
                                Log.e(TAG, "onAnimationEnd: ", e);
                            }
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        //left empty
                    }
                });
                locationBinding.showLocationsList.setAnimation(animation);
                locationBinding.showLocationsList.setVisibility(View.GONE);
            }
        });
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

    private void loadData() {

        ServiceLocationsTO serviceLocationsTO = application.getServiceLocationsTO();
        locationTOs = serviceLocationsTO.getLocationTOs();

        WeakReference<LocationsCallBack> weakReferenceCarpetRoomServiceCallBack = new WeakReference<LocationsCallBack>(this);
        LocationsAdapter locationsAdapter = new LocationsAdapter(application, locationTOs, weakReferenceCarpetRoomServiceCallBack);
        locationBinding.recyclerViewLocationList.setAdapter(locationsAdapter);

        updateMap();
    }


    private void updateMap() {
        if (ListUtils.isEmpty(locationTOs) || googleMap == null) {
            return;
        }

        addMarkers();
    }

    private void addMarkers() {

        if (ListUtils.isEmpty(locationTOs)) {
            return;
        }

        markers = new ArrayList<>();
        markerLocationMap = new LinkedHashMap<>();

        for (LocationTO locationTO : locationTOs) {

            Coordinates coordinates = locationTO.getCoordinates();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(cordinatesToLatLong(coordinates));
            markerOptions.title(locationTO.getCity());
            markerLocationMap.put(googleMap.addMarker(markerOptions), locationTO);

        }

    }

    private LatLng cordinatesToLatLong(Coordinates coordinates) {
        return new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
    }


    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (isDetached()) {
                return;
            }

            if (ListUtils.isEmpty(locationTOs) && newState == BottomSheetBehavior.STATE_DRAGGING) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return;
            }

            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setPeekHeight(bottomSheetPeekHeight);
                locationBinding.showLocationsList.setVisibility(View.GONE);
            }

            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                Animation animation = AnimationUtils.loadAnimation(application, R.anim.slide_in_up);
                animation.setInterpolator(new AccelerateInterpolator());
                locationBinding.showLocationsList.setVisibility(View.VISIBLE);
                isListOpen = true;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
}
