package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.CarpetCleaningServicesActivity;
import com.gobluegreen.app.activity.CustomerAddressActivity;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBuilderLandingBinding;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomType;
import com.gobluegreen.app.to.ServiceType;
import com.gobluegreen.app.to.UpholsteryType;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.ListUtils;
import com.gobluegreen.app.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;


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
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CARPET_CLEANING_REQUEST_CODE:
                addCarpetServicesToCardView();
                break;
            case CUSTOMER_INFORMATION_REQUEST_CODE:
                addCustomerInformationTOCardView();
                break;
        }
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

                shouldContinueButtonBeEnabled();
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

                shouldContinueButtonBeEnabled();
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

        landingBinding.layoutCustomerInformation.addModifyContactInformation.setOnClickListener(new View.OnClickListener() {
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

        if (upholsteryTypeSet.size() == 0) {
            estimateInProgressTO.setUpholsterySet(null);
        }
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

            if (estimateInProgressTO.getCustomerTO() != null) {
                addCustomerInformationTOCardView();
            }
        }

        shouldContinueButtonBeEnabled();
    }

    private void shouldContinueButtonBeEnabled() {

        if (estimateInProgressTO == null) {
            return;
        }

        if (estimateInProgressTO.getCustomerTO() == null) {
            return;
        }

        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();
        if (serviceTypeSet == null || serviceTypeSet.size() == 0) {
            return;
        }

        landingBinding.landingContinueButton.setOnClickListener(getContinueButtonOnClickListener);
        int enabledButtonColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        landingBinding.contiueLabel.setTextColor(enabledButtonColor);
    }

    private View.OnClickListener getContinueButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "David: " + "Continue onClick() called with: v = [" + v + "]");
        }
    };

    private void restoreEstimate() {

        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();

        if (serviceTypeSet == null) {
            return;
        }

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

        final String DOT = "\u00b7";
        landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        List<String> roomServiceList = new ArrayList<>();

        if (ListUtils.isNotEmpty(roomTypes)) {

            for (RoomType roomType : roomTypes) {
                roomServiceList.add(DOT + " " + roomType.getDescription());
            }

            final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_selected_carpet_servcies_item, roomServiceList);
            landingBinding.layoutCarpetCleaningServices.roomsToCleanGridView.setAdapter(gridViewArrayAdapter);
            gridViewArrayAdapter.notifyDataSetChanged();
        }

        shouldContinueButtonBeEnabled();
    }


    private void addCustomerInformationTOCardView() {

        String modifyText = getResources().getString(R.string.modify);
        landingBinding.layoutCustomerInformation.addModifyContactInformation.setText(modifyText);
        landingBinding.layoutCustomerInformation.customerInformationInitialDirections.setVisibility(View.GONE);
        landingBinding.layoutCustomerInformation.customerInformationCompleted.setVisibility(View.VISIBLE);

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        String customerName = customerTO.getFirstName() + " " + customerTO.getLastName();
        landingBinding.layoutCustomerInformation.customerInformationFullName.setText(customerName);

        String phone = customerTO.getPhoneNumber();
        landingBinding.layoutCustomerInformation.customerInformationPhone.setText(phone);

        String address = customerTO.getAddress1();
        if (StringUtils.isNotEmpty(address)) {
            landingBinding.layoutCustomerInformation.customerInformationAddress.setText(address);
            landingBinding.layoutCustomerInformation.customerInformationAddress.setVisibility(View.VISIBLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        String city = customerTO.getCity();
        if (StringUtils.isNotEmpty(city)) {
            stringBuilder.append(city);
        }

        String state = customerTO.getState();
        if (StringUtils.isNotEmpty(state)) {
            stringBuilder.append(" ").append(state);
        }

        String zip = customerTO.getZipCode();
        String selectedState = getString(R.string.select_state);
        if (StringUtils.isEmpty(zip) && !selectedState.equalsIgnoreCase(zip)) {
            stringBuilder.append(" ").append(zip);
        }

        String cityStateZip = stringBuilder.toString();
        if (StringUtils.isNotEmpty(cityStateZip)) {
            landingBinding.layoutCustomerInformation.customerInformationCityState.setText(cityStateZip);
            landingBinding.layoutCustomerInformation.customerInformationCityState.setVisibility(View.VISIBLE);
        }
        shouldContinueButtonBeEnabled();
    }

}

