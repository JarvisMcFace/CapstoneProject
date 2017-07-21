package com.gobluegreen.app.fragment;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.CarpetItemAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentCarpetCleaningServicesBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomDescriptionItemTO;
import com.gobluegreen.app.to.RoomType;
import com.gobluegreen.app.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 7/5/17.
 */
public class CarpetCleaningServicesFragment extends Fragment {

    private RecyclerView recyclerView;
    private boolean initialLaunch = true;
    private EstimateInProgressTO estimateInProgressTO;
    private FragmentCarpetCleaningServicesBinding carpetCleaningServicesBinding;
    private List<String> roomDescriptions;
    private List<String> selectedCleaningDescriptions;
    private CarpetItemAdapter adapter;

    public CarpetCleaningServicesFragment() {
        //default left blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carpet_cleaning_services, container, false);
        carpetCleaningServicesBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();
        estimateInProgressTO = application.getEstimateInProgressTO();

        recyclerView = carpetCleaningServicesBinding.carpetServicesRecyclerView;
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        roomDescriptions = initCarpetServices();
        carpetCleaningServicesBinding.carpetServiceDone.setOnClickListener(getDoneButtonOnClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        List<RoomDescriptionItemTO> roomDescriptionItemTOs = new ArrayList<>();

        if (initialLaunch) {
            selectedCleaningDescriptions = getPreviouslySelecteCarpetServices();
            initialLaunch = false;
        } else {
            if (ListUtils.isEmpty(selectedCleaningDescriptions)) {
                selectedCleaningDescriptions = new ArrayList<>();
            }
        }

        for (String roomDescription : roomDescriptions) {

            RoomDescriptionItemTO roomDescriptionItemTO = new RoomDescriptionItemTO();
            roomDescriptionItemTO.setDescription(roomDescription);
            RoomType roomType = RoomType.lookupRoomType(roomDescription);
            if (selectedCleaningDescriptions.contains(roomType.getDescription())) {
                roomDescriptionItemTO.setSelected(true);
            }

            roomDescriptionItemTOs.add(roomDescriptionItemTO);
        }

        adapter = new CarpetItemAdapter(roomDescriptionItemTOs);
        recyclerView.setAdapter(adapter);

    }

    private List<String> getPreviouslySelecteCarpetServices() {

        List<RoomType> previouslySelectedRoomType = estimateInProgressTO.getRoomTypes();

        if (ListUtils.isEmpty(previouslySelectedRoomType)) {
            return new ArrayList<>();
        }

        selectedCleaningDescriptions = new ArrayList<>();

        for (RoomType previouslyRoomType : previouslySelectedRoomType) {
            selectedCleaningDescriptions.add(previouslyRoomType.getDescription());
        }

        return selectedCleaningDescriptions;
    }

    @Override
    public void onPause() {

        List<RoomType> roomTypes = adapter.getRoomTypeList();
        if (ListUtils.isNotEmpty(roomTypes)) {
            estimateInProgressTO.setRoomTypes(roomTypes);
        }

        super.onPause();
    }

    private List<String> initCarpetServices() {

        if (ListUtils.isEmpty(roomDescriptions)) {
            roomDescriptions = new ArrayList<>();
        }

        for (RoomType roomType : RoomType.values()) {
            roomDescriptions.add(roomType.getDescription());
        }

        return roomDescriptions;
    }

    private View.OnClickListener getDoneButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Activity activity = getActivity();
            activity.setResult(EstimateBuilderLandingFragment.CARPET_CLEANING_REQUEST_CODE);
            activity.finish();
        }
    };
}
