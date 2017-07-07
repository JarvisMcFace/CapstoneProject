package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.CarpetCleaningServicesActivity;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBuilderLandingBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.ServiceType;

import java.util.HashSet;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by David on 7/5/17.
 */
public class EstimateBuilderLandingFragment extends Fragment {

    private static final int CARPET_CLEANING_REQUEST_CODE = 100;

    private View rootView;
    private FragmentEstimateBuilderLandingBinding landingBinding;
    private EstimateInProgressTO estimateInProgressTO;

    public EstimateBuilderLandingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_estimate_builder_landing, container, false);
        landingBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "David: " + "onResume() called");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initEstimateInProgress();
        initServicesCardViews();
    }

    private void initServicesCardViews() {

        landingBinding.layoutServicesSelection.servicesSelectionCarpetCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);
                    addServiceToEstimate(ServiceType.CARPET);

                    Intent intent = new Intent(getActivity(), CarpetCleaningServicesActivity.class);
                    startActivityForResult(intent, CARPET_CLEANING_REQUEST_CODE);
                } else {
                    landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.GONE);
                    removeServiceFromEstimate(ServiceType.CARPET);
                    estimateInProgressTO.setRoomTOs(null);
                }
            }
        });

        landingBinding.layoutServicesSelection.servicesSelectionUpholsteryCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.VISIBLE);
                    addServiceToEstimate(ServiceType.UPHOLSTERY);
                } else {
                    landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.GONE);
                    removeServiceFromEstimate(ServiceType.UPHOLSTERY);
                    estimateInProgressTO.setUpholsteryTOs(null);
                }
            }
        });
    }

    private void addServiceToEstimate(ServiceType serviceType) {
        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();

        if (serviceTypeSet == null) {
            serviceTypeSet = new HashSet<>();
            estimateInProgressTO.setServiceTypeSet(serviceTypeSet);
        }

        serviceTypeSet.add(serviceType);
    }

    private void removeServiceFromEstimate(ServiceType serviceType) {

        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();

        if (serviceTypeSet == null) {
            return;
        }
        serviceTypeSet.remove(serviceType);
    }


    private void initEstimateInProgress() {
        GoBluegreenApplication goBluegreenApplication = (GoBluegreenApplication) getActivity().getApplication();

        estimateInProgressTO = goBluegreenApplication.getEstimateInProgressTO();
        if (estimateInProgressTO == null) {
            estimateInProgressTO = new EstimateInProgressTO();
            goBluegreenApplication.setEstimateInProgressTO(estimateInProgressTO);
        }
    }
}
