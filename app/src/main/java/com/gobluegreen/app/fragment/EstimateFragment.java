package com.gobluegreen.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.CarpetRoomServiceCallBack;
import com.gobluegreen.app.adapter.EstimateAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.EstimateItemTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.to.RoomType;
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
        estimateBinding.estimateRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();

        populateRoomsToEstimate();
        List<EstimateItemTO> estimateItemTOs = PopulateEstimateItems.execute(application);

        WeakReference<CarpetRoomServiceCallBack> weakReferenceCarpetRoomServiceCallBack =  new WeakReference<CarpetRoomServiceCallBack>(this);
        estimateAdapter = new EstimateAdapter(estimateItemTOs,weakReferenceCarpetRoomServiceCallBack);
        estimateBinding.estimateRecyclerView.setAdapter(estimateAdapter);
    }

    @Override
    public void updateRoomLength(RoomTO updateRoomTO, int position) {

       List<RoomTO> roomTOs =  estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            return;
        }

        for (RoomTO roomTO : roomTOs) {
            if (updateRoomTO.getRoomType() == roomTO.getRoomType()){
                int length = updateRoomTO.getLength();
                roomTO.setLength(length);
                break;
            }
        }
    }

    @Override
    public void updateRoomWidth(RoomTO updateRoomTO, int position, TextView estimatedPrice) {
        List<RoomTO> roomTOs =  estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            return;
        }

        for (RoomTO roomTO : roomTOs) {
            if (updateRoomTO.getRoomType() == roomTO.getRoomType()){
                int width = updateRoomTO.getWidth();
                roomTO.setWidth(width);
                break;
            }
        }
    }

    private void populateRoomsToEstimate() {

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        if (ListUtils.isEmpty(roomTypes)) {
            return;
        }

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            roomTOs= new ArrayList<>();
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
}
