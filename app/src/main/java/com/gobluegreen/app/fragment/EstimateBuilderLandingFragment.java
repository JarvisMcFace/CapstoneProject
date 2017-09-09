package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.CarpetCleaningServicesActivity;
import com.gobluegreen.app.activity.CustomerAddressActivity;
import com.gobluegreen.app.activity.EstimateActivity;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBuilderLandingBinding;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
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



/**
 * Created by David on 7/5/17.
 */
public class EstimateBuilderLandingFragment extends Fragment {

    private static final String TAG = EstimateBuilderLandingFragment.class.getSimpleName();

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
    public void onResume() {
        super.onResume();
        Log.d(TAG, "David: " + "onResume() called");
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
                    estimateInProgressTO.setUpholsterySet(null);
                    removeServiceFromEstimate(ServiceType.UPHOLSTERY);
                    landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.GONE);
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
                    removeUpholsteryToEstimate(UpholsteryType.SOFA);
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
                    removeUpholsteryToEstimate(UpholsteryType.CHAIR);
                }
            }
        });

        landingBinding.layoutCustomerInformation.addModifyContactInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasPickedCustomerType()) {
                  return;
                }

                Intent intent = new Intent(getActivity(), CustomerAddressActivity.class);
                startActivityForResult(intent, CUSTOMER_INFORMATION_REQUEST_CODE);
            }
        });

        landingBinding.layoutCarpetCleaningServices.modifyCarpetingCleaningRooms.setOnClickListener(getModifyCarpetCleaningOnClickListener);

        landingBinding.layoutCustomerType.customerTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                landingBinding.layoutCustomerType.customerTypeError.setVisibility(View.GONE);
                if (checkedId == R.id.customer_type_residential) {
                    setCustomerType(CustomerType.RESIDENTIAL);
                    residentialScreenInit();
                } else {
                    setCustomerType(CustomerType.COMMERCIAL);
                }
            }
        });
    }

    private boolean hasPickedCustomerType() {

        if (landingBinding.layoutCustomerType.customerTypeRadioGroup.getCheckedRadioButtonId() <= 0){
            landingBinding.layoutCustomerType.customerTypeError.setVisibility(View.VISIBLE);
            return false;
        }

       return true;
    }


    private void addUpholsteryToEstimate(UpholsteryType upholsteryType) {

        Set<UpholsteryType> upholsterySet = estimateInProgressTO.getUpholsterySet();

        if (upholsterySet == null) {
            upholsterySet = new HashSet<>();
            estimateInProgressTO.setUpholsterySet(upholsterySet);
        }

        upholsterySet.add(upholsteryType);
    }

    private void removeUpholsteryToEstimate(UpholsteryType upholsteryType) {

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
        if ((serviceTypeSet == null || serviceTypeSet.size() == 0)  && CustomerType.COMMERCIAL != estimateInProgressTO.getCustomerTO().getCustomerType()) {
            return;
        }

        landingBinding.landingContinueButton.setOnClickListener(getContinueButtonOnClickListener);
        int enabledButtonColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        landingBinding.contiueLabel.setTextColor(enabledButtonColor);
    }

    private View.OnClickListener getContinueButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), EstimateActivity.class);
            startActivity(intent);
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

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.RESIDENTIAL == customerType) {
            landingBinding.layoutCustomerType.customerTypeCommercial.setChecked(true);
            landingBinding.layoutServicesSelection.servicesSelectionCardview.setVisibility(View.GONE);
        } else {
            landingBinding.layoutCustomerType.customerTypeResidential.setChecked(true);
            landingBinding.layoutServicesSelection.servicesSelectionCardview.setVisibility(View.VISIBLE);
        }
    }

    private void addUpholsteryEstimate() {

        landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.VISIBLE);

        Set<UpholsteryType> upholsterySet = estimateInProgressTO.getUpholsterySet();
        if (upholsterySet == null || upholsterySet.size() == 0) {
            return;
        }

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

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        if (ListUtils.isEmpty(roomTypes)) {
            return;
        }

        final String DOT = "\u00b7";
        int roomTypeSize = roomTypes.size();
        if (roomTypeSize == 0) {
            String addText = getResources().getString(R.string.add_rooms);
            landingBinding.layoutCarpetCleaningServices.modifyCarpetingCleaningRooms.setText(addText);
        } else {
            String modifyText = getResources().getString(R.string.modify);
            landingBinding.layoutCarpetCleaningServices.modifyCarpetingCleaningRooms.setText(modifyText);
        }

        landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);
        List<String> roomServiceList = new ArrayList<>();

        if (ListUtils.isNotEmpty(roomTypes)) {

            for (RoomType roomType : roomTypes) {
                roomServiceList.add(DOT + " " + roomType.getDescription());
            }

            final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_selected_carpet_servcies_item, roomServiceList);
            landingBinding.layoutCarpetCleaningServices.roomsToCleanGridView.setAdapter(gridViewArrayAdapter);
            gridViewArrayAdapter.notifyDataSetChanged();
        } else {
            landingBinding.layoutCarpetCleaningServices.roomsToCleanGridView.setAdapter(null);
        }

        shouldContinueButtonBeEnabled();
    }


    private void addCustomerInformationTOCardView() {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        if (customerTO == null) {
            return;
        }

        if (CustomerType.COMMERCIAL == customerTO.getCustomerType()) {
            String businessName = customerTO.getBusinessName();
            landingBinding.layoutCustomerInformation.customerInformationBusinessName.setText(businessName);
        }

        String firstName = customerTO.getFirstName();
        String lastName = customerTO.getLastName();
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            return;
        }

        String modifyText = getResources().getString(R.string.modify);
        landingBinding.layoutCustomerInformation.addModifyContactInformation.setText(modifyText);
        landingBinding.layoutCustomerInformation.customerInformationInitialDirections.setVisibility(View.GONE);
        landingBinding.layoutCustomerInformation.customerInformationCompleted.setVisibility(View.VISIBLE);

        String fullName = firstName + " " + lastName;
        landingBinding.layoutCustomerInformation.customerInformationFullName.setText(fullName);

        String phone = customerTO.getPhoneNumber();
        if (StringUtils.isNotEmpty(phone)) {
            landingBinding.layoutCustomerInformation.customerInformationPhone.setText(phone);
        }

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

    private void residentialScreenInit() {
        landingBinding.layoutServicesSelection.servicesSelectionCardview.setVisibility(View.VISIBLE);

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        if (ListUtils.isNotEmpty(roomTypes)) {
            landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);
        }
        Set<UpholsteryType> upholsterySet = estimateInProgressTO.getUpholsterySet();
        if (upholsterySet != null && upholsterySet.size() > 0) {
            landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.VISIBLE);
        }
    }

    private void commercialScreenInit() {
        landingBinding.layoutServicesSelection.servicesSelectionCardview.setVisibility(View.GONE);
        landingBinding.layoutCarpetCleaningServices.carpetCleaningServicesCardview.setVisibility(View.GONE);
        landingBinding.layoutUpholsteryServices.upholsteryCleaningServicesCardview.setVisibility(View.GONE);
    }

    private void setCustomerType(CustomerType customerType) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        if (customerTO == null) {
            customerTO = new CustomerTO();
        }

        if (CustomerType.COMMERCIAL == customerType) {
            commercialScreenInit();
        }

        customerTO.setCustomerType(customerType);
        estimateInProgressTO.setCustomerTO(customerTO);
    }
}

