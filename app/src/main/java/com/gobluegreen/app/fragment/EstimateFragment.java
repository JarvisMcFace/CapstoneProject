package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.EstimateReviewSubmitActivity;
import com.gobluegreen.app.adapter.CarpetRoomServiceCallBack;
import com.gobluegreen.app.adapter.EstimateAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.EstimateItemTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.to.RoomType;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.DeriveEstimatedPriceHighLowRange;
import com.gobluegreen.app.util.DeriveEstimatedPriceOfRoom;
import com.gobluegreen.app.util.DeriveEstimatedTotalSquareFeet;
import com.gobluegreen.app.util.ListUtils;
import com.gobluegreen.app.util.PopulateEstimateItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by David on 7/14/17.
 */
public class EstimateFragment extends Fragment implements CarpetRoomServiceCallBack {

    private View rootView;
    private FragmentEstimateBinding estimateBinding;
    private GoBluegreenApplication application;
    private EstimateInProgressTO estimateInProgressTO;
    private EstimateAdapter estimateAdapter;

    public EstimateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_estimate, container, false);
        estimateBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (GoBluegreenApplication) getActivity().getApplication();

        estimateInProgressTO = application.getEstimateInProgressTO();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        estimateBinding.layoutEstimate.estimateRecyclerView.setLayoutManager(linearLayoutManager);

        estimateBinding.landingContinueButton.setOnClickListener(getContinueButtonOnClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        populateRoomsToEstimate();
        List<EstimateItemTO> estimateItemTOs = PopulateEstimateItems.execute(application);

        WeakReference<CarpetRoomServiceCallBack> weakReferenceCarpetRoomServiceCallBack = new WeakReference<CarpetRoomServiceCallBack>(this);
        estimateAdapter = new EstimateAdapter(estimateItemTOs, weakReferenceCarpetRoomServiceCallBack);
        estimateBinding.layoutEstimate.estimateRecyclerView.setAdapter(estimateAdapter);
    }

    @Override
    public void updateEstimateHeader(RoomTO roomTO) {

        if (roomTO == null) {
            return;
        }

        if (roomTO.getRoomType() != RoomType.STAIRWAY_LANDING && roomTO.isDimensionByLengthWidth()){


            int roomLength = roomTO.getLength();
            int roomWidth = roomTO.getWidth();

            if (roomLength > 0 && roomWidth > 0 ) {
                int squareFeet = roomLength * roomTO.getWidth();
                roomTO.setSquareFeet(squareFeet);
            } else {
                roomTO.setSquareFeet(0);
            }
        }



        double estimatedRoomPrice = DeriveEstimatedPriceOfRoom.execute(application, roomTO);
        if (estimatedRoomPrice >= 0) {
            roomTO.setPriceEstimate(estimatedRoomPrice);
        }


        String estimatedPriceRange = DeriveEstimatedPriceHighLowRange.execute(application);
        estimateBinding.layoutEstimate.estimatePriceRange.setText(estimatedPriceRange);

        int estimatedTotalSquareFeet = DeriveEstimatedTotalSquareFeet.execute(application);
        estimateBinding.layoutEstimate.estimatedTotalSquareFeet.setText(String.valueOf(estimatedTotalSquareFeet));
    }

    @Override
    public void onPause() {

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();
        CarpetQuoteCacheUtility.saveEstimateInProgress(application);
        super.onPause();
    }

    private void populateRoomsToEstimate() {

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        if (ListUtils.isEmpty(roomTypes)) {
            return;
        }

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            roomTOs = new ArrayList<>();
            estimateInProgressTO.setRoomTOs(roomTOs);
        }

        for (RoomType roomType : roomTypes) {

            boolean roomAlreadyExist = doesRoomAlreadyExistInList(roomType);

            if (roomAlreadyExist) {
                continue;
            }

            RoomTO roomTO = new RoomTO();
            roomTO.setRoomType(roomType);
            roomTOs.add(roomTO);
        }

        estimateInProgressTO.setRoomTOs(roomTOs);
    }

    private boolean doesRoomAlreadyExistInList(@Nonnull RoomType roomTypeExist) {

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();

        for (RoomTO roomTO : roomTOs) {

            RoomType roomType = roomTO.getRoomType();

            if (roomType.equals(roomTypeExist)) {
                return true;
            }
        }
        return false;
    }


    private View.OnClickListener getContinueButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = EstimateReviewSubmitActivity.newIntent(getContext());
            startActivity(intent);
        }
    };
}
