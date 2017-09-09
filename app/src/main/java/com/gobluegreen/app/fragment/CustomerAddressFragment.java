package com.gobluegreen.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentCustomerAddressBinding;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.CustomerInformationValidator;
import com.gobluegreen.app.util.StringUtils;

/**
 * Created by David on 7/4/17.
 */

public class CustomerAddressFragment extends Fragment {

    private View rootView;
    private FragmentCustomerAddressBinding customerAddressBinding;
    private EstimateInProgressTO estimateInProgressTO;

    public CustomerAddressFragment() {
        //default left blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customer_address, container, false);
        customerAddressBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        estimateInProgressTO = (EstimateInProgressTO) intent.getSerializableExtra(EstimateBuilderLandingFragment.EXTRA_ESTIMATE_IN_PROGRESS);

        initEstimateInProgress();

        customerAddressBinding.customerInformationCardview.customerPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        customerAddressBinding.customerInformationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInputFieldValid()) {
                    populateCustomerTO();
                    Activity activity = getActivity();
                    activity.setResult(EstimateBuilderLandingFragment.CUSTOMER_INFORMATION_REQUEST_CODE);
                    activity.finish();
                }
            }
        });

        initBusinessCustomer();

    }

    @Override
    public void onPause() {

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();
        CarpetQuoteCacheUtility.saveEstimateInProgress(application);
        super.onPause();
    }

    private void populateCustomerTO() {

        CustomerType customerType = estimateInProgressTO.getCustomerTO().getCustomerType();

        CustomerTO customerTO = new CustomerTO();
        customerTO.setBusinessName(customerAddressBinding.customerInformationCardview.businessName.getText().toString());
        customerTO.setFirstName(customerAddressBinding.customerInformationCardview.customerFirstName.getText().toString());
        customerTO.setLastName(customerAddressBinding.customerInformationCardview.customerLastName.getText().toString());
        customerTO.setAddress1(customerAddressBinding.customerInformationCardview.customerAddress.getText().toString());
        customerTO.setCity(customerAddressBinding.customerInformationCardview.customerCity.getText().toString());
        customerTO.setZipCode(customerAddressBinding.customerInformationCardview.customerPostalCode.getText().toString());
        customerTO.setPhoneNumber(customerAddressBinding.customerInformationCardview.customerPhone.getText().toString());
        customerTO.setCustomerType(customerType);
        customerTO.setPhoneNumber(customerAddressBinding.customerInformationCardview.customerPhone.getText().toString());

        String selectedStateSpinner = getString(R.string.select_state);
        String customerSelectedState = customerAddressBinding.customerInformationCardview.customerState.getSelectedItem().toString();
        if (!selectedStateSpinner.equalsIgnoreCase(customerSelectedState)) {
            customerTO.setState(customerSelectedState);
        }

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();
        if (estimateInProgressTO != null) {
            estimateInProgressTO.setCustomerTO(customerTO);
        }

    }

    private void initEstimateInProgress() {

        GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();

        EstimateInProgressTO estimateInProgressTO = CarpetQuoteCacheUtility.getEstimateInProgress(application);
        if (estimateInProgressTO == null) {
            return;
        }

        this.estimateInProgressTO = estimateInProgressTO;

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        if (customerTO == null) {
            return;
        }

        customerAddressBinding.customerInformationCardview.customerFirstName.setText(customerTO.getFirstName());
        customerAddressBinding.customerInformationCardview.customerLastName.setText(customerTO.getLastName());
        customerAddressBinding.customerInformationCardview.customerPhone.setText(customerTO.getPhoneNumber());

        if (CustomerType.COMMERCIAL == customerTO.getCustomerType()) {
            customerAddressBinding.customerInformationCardview.businessName.setText(customerTO.getBusinessName());

        }
        String address = customerTO.getAddress1();
        if (StringUtils.isNotEmpty(address)) {
            customerAddressBinding.customerInformationCardview.customerAddress.setText(customerTO.getAddress1());
        }

        String city = customerTO.getCity();
        if (StringUtils.isNotEmpty(city)) {
            customerAddressBinding.customerInformationCardview.customerCity.setText(customerTO.getCity());
        }

        String postalCode = customerTO.getZipCode();
        if (StringUtils.isNotEmpty(postalCode)) {
            customerAddressBinding.customerInformationCardview.customerPostalCode.setText(customerTO.getZipCode());
        }

        String customerState = customerTO.getState();
        if (StringUtils.isNotEmpty(customerState)) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.states_list, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            customerAddressBinding.customerInformationCardview.customerState.setAdapter(adapter);
            if (!customerState.equals(null)) {
                int spinnerPosition = adapter.getPosition(customerState);
                customerAddressBinding.customerInformationCardview.customerState.setSelection(spinnerPosition);
            }
        }
    }


    private void initBusinessCustomer() {

        if (estimateInProgressTO == null) {
            return;
        }

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        if (CustomerType.COMMERCIAL == customerTO.getCustomerType()) {
            customerAddressBinding.customerInformationCardview.inputLayoutBusinessContact.setVisibility(View.VISIBLE);
        } else {
            customerAddressBinding.customerInformationCardview.inputLayoutBusinessContact.setVisibility(View.GONE);
        }
    }

    private boolean isInputFieldValid() {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        if (customerTO == null) {
            customerTO = new CustomerTO();
            estimateInProgressTO.setCustomerTO(customerTO);
        }

        if (CustomerType.COMMERCIAL == customerTO.getCustomerType()) {
            String businessName = customerAddressBinding.customerInformationCardview.businessName.getText().toString();
            if (!CustomerInformationValidator.isValidCharacters(businessName)) {
                customerAddressBinding.customerInformationCardview.businessName.setError(getString(R.string.error_customer_business_not_valid));
                customerAddressBinding.customerInformationCardview.businessName.requestFocus();
                return false;
            }
        }

        String firstName = customerAddressBinding.customerInformationCardview.customerFirstName.getText().toString();
        if (!CustomerInformationValidator.isValidCharacters(firstName)) {
            customerAddressBinding.customerInformationCardview.customerFirstName.setError(getString(R.string.error_customer_first_not_valid));
            customerAddressBinding.customerInformationCardview.customerFirstName.requestFocus();
            return false;
        }

        String lastName = customerAddressBinding.customerInformationCardview.customerLastName.getText().toString();
        if (!CustomerInformationValidator.isValidCharacters(lastName)) {
            customerAddressBinding.customerInformationCardview.customerLastName.setError(getString(R.string.error_customer_last_not_valid));
            customerAddressBinding.customerInformationCardview.customerLastName.requestFocus();
            return false;
        }

        String phoneNumber = customerAddressBinding.customerInformationCardview.customerPhone.getText().toString();
        if (!CustomerInformationValidator.isInvalidPhoneNumber(phoneNumber)) {
            customerAddressBinding.customerInformationCardview.customerPhone.setError(getString(R.string.error_customer_phone_not_valid));
            customerAddressBinding.customerInformationCardview.customerPhone.requestFocus();
            return false;
        }

        String address = customerAddressBinding.customerInformationCardview.customerAddress.getText().toString();
        if (StringUtils.isNotEmpty(address))
            if (!CustomerInformationValidator.isValidCharacters(address)) {
                customerAddressBinding.customerInformationCardview.customerAddress.setError(getString(R.string.error_customer_address_not_valid));
                customerAddressBinding.customerInformationCardview.customerAddress.requestFocus();
                return false;
            }

        String city = customerAddressBinding.customerInformationCardview.customerCity.getText().toString();
        if (StringUtils.isNotEmpty(city)) {
            if (!CustomerInformationValidator.isValidCharacters(city)) {
                customerAddressBinding.customerInformationCardview.customerCity.setError(getString(R.string.error_customer_city_not_valid));
                customerAddressBinding.customerInformationCardview.customerCity.requestFocus();
                return false;
            }
        }

        String postalCode = customerAddressBinding.customerInformationCardview.customerPostalCode.getText().toString();
        if (StringUtils.isNotEmpty(postalCode)) {
            if (!CustomerInformationValidator.isValidPostalCode(postalCode)) {
                customerAddressBinding.customerInformationCardview.customerPostalCode.setError(getString(R.string.error_customer_zip_not_valid));
                customerAddressBinding.customerInformationCardview.customerPostalCode.requestFocus();
                return false;
            }
        }

        return true;
    }
}
