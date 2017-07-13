package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.CarpetCleaningServicesActivity;
import com.gobluegreen.app.activity.CustomerAddressActivity;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBuilderLandingBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomType;
import com.gobluegreen.app.to.ServiceType;
import com.gobluegreen.app.to.UpholsteryType;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by David on 7/5/17.
 */
public class EstimateBuilderLandingFragment extends Fragment {

    public static final String EXTRA_ESTIMATE_IN_PROGRESS = "com.gobluegreen.app.fragment.estimate.in.progress";
    public static final int CARPET_CLEANING_REQUEST_CODE = 100;
    public static final int CUSTOMER_INFORMATION_REQUEST_CODE = 200;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initEstimateInProgress();
        initServicesCardViews();
    }

    @Override
    public void onPause() {

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();
        CarpetQuoteCacheUtility.saveEstimateInProgress(application);
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CARPET_CLEANING_REQUEST_CODE) {

            addCarpetServicesToCardView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initServicesCardViews() {

        landingBinding.layoutServicesSelection.servicesSelectionCarpetCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = landingBinding.layoutServicesSelection.servicesSelectionCarpetCheckbox.isChecked();
                if (isChecked) {
                    addServiceToEstimate(ServiceType.CARPET);
                    startCarpetServiceSelectionActivity();
                } else {
                    landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.GONE);
                    removeServiceFromEstimate(ServiceType.CARPET);
                    estimateInProgressTO.setRoomTOs(null);
                    estimateInProgressTO.setRoomTypes(null);
                }
            }
        });

        landingBinding.layoutServicesSelection.servicesSelectionUpholsteryCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = landingBinding.layoutServicesSelection.servicesSelectionUpholsteryCheckbox.isChecked();
                if (isChecked) {
                    landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.VISIBLE);
                    addServiceToEstimate(ServiceType.UPHOLSTERY);
                } else {
                    landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.GONE);
                    removeServiceFromEstimate(ServiceType.UPHOLSTERY);
                    estimateInProgressTO.setUpholsterySet(null);
                }
            }
        });

        landingBinding.layoutUpholsteryServices.servicesSelectionSofaCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = landingBinding.layoutUpholsteryServices.servicesSelectionSofaCheckbox.isChecked();
                if (isChecked) {
                    addUpholsteryToEstimate(UpholsteryType.SOFA);
                } else {
                    removeUpHolsteryToEstimate(UpholsteryType.SOFA);
                }
            }
        });

        landingBinding.layoutUpholsteryServices.servicesSelectionChairCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = landingBinding.layoutUpholsteryServices.servicesSelectionSofaCheckbox.isChecked();
                if (isChecked) {
                    addUpholsteryToEstimate(UpholsteryType.CHAIR);
                } else {
                    removeUpHolsteryToEstimate(UpholsteryType.CHAIR);
                }
            }
        });

        landingBinding.layoutCustomerInformation.addContactInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerAddressActivity.class);
                startActivityForResult(intent, CUSTOMER_INFORMATION_REQUEST_CODE);
            }
        });

        landingBinding.layoutCarpetCleaningServices.modifyCarpetingCleaningRooms.setOnClickListener(getModifyCarpetCleaningOnClickListener);
    }

    private void addUpholsteryToEstimate(UpholsteryType upholsteryType) {

        Set<UpholsteryType> upholsterySet = estimateInProgressTO.getUpholsterySet();

        if (upholsterySet == null) {
            upholsterySet = new HashSet<>();
            estimateInProgressTO.setUpholsterySet(upholsterySet);
        }

        upholsterySet.add(upholsteryType);
    }

    private void removeUpHolsteryToEstimate(UpholsteryType upholsteryType) {

        Set<UpholsteryType> upholsteryTypeSet = estimateInProgressTO.getUpholsterySet();

        if (upholsteryTypeSet == null) {
            return;
        }
        upholsteryTypeSet.remove(upholsteryType);
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

        estimateInProgressTO = CarpetQuoteCacheUtility.getEstimateInProgress(goBluegreenApplication);

        if (estimateInProgressTO == null) {
            estimateInProgressTO = new EstimateInProgressTO();
            goBluegreenApplication.setEstimateInProgressTO(estimateInProgressTO);
        } else {
            goBluegreenApplication.setEstimateInProgressTO(estimateInProgressTO);
            restoreEstimate();
        }
    }

    private void restoreEstimate() {

        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();

        for (ServiceType serviceType : serviceTypeSet) {

            switch (serviceType) {

                case CARPET:
                    landingBinding.layoutServicesSelection.servicesSelectionCarpetCheckbox.setChecked(true);
                    addCarpetServicesToCardView();
                    break;
                case TILE_GROUT:
                    break;
                case UPHOLSTERY:
                    landingBinding.layoutServicesSelection.servicesSelectionUpholsteryCheckbox.setChecked(true);
                    addUpholsteryEstimate();
                    break;
                case WOOD_FLOOR:
                    break;
            }
        }
    }

    private void addUpholsteryEstimate() {

        Set<UpholsteryType> upholsterySet = estimateInProgressTO.getUpholsterySet();
        if (upholsterySet == null || upholsterySet.size() == 0) {
            return;
        }

        landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.VISIBLE);

        for (UpholsteryType upholsteryType : upholsterySet) {

            switch (upholsteryType) {

                case SOFA:
                    landingBinding.layoutUpholsteryServices.servicesSelectionSofaCheckbox.setChecked(true);
                    break;
                case CHAIR:
                    landingBinding.layoutUpholsteryServices.servicesSelectionChairCheckbox.setChecked(true);
                    break;
            }
        }
    }

    private View.OnClickListener getModifyCarpetCleaningOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startCarpetServiceSelectionActivity();
        }
    };

    private void startCarpetServiceSelectionActivity() {
        Intent intent = new Intent(getActivity(), CarpetCleaningServicesActivity.class);
        intent.putExtra(EXTRA_ESTIMATE_IN_PROGRESS, estimateInProgressTO);
        startActivityForResult(intent, CARPET_CLEANING_REQUEST_CODE);
    }

    private void addCarpetServicesToCardView() {

        landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        List<String> roomServiceList = new ArrayList<>();

        if (ListUtils.isNotEmpty(roomTypes)) {

            for (RoomType roomType : roomTypes) {
                roomServiceList.add(roomType.getDescription());
            }

            final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_selected_carpet_servcies_item, roomServiceList);
            landingBinding.layoutCarpetCleaningServices.roomsToCleanGridView.setAdapter(gridViewArrayAdapter);
            gridViewArrayAdapter.notifyDataSetChanged();
        }
    }
}

