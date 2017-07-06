package com.gobluegreen.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.databinding.FragmentEstimateServicesOfferingBinding;

/**
 * Created by David on 7/5/17.
 */
public class EstimateServicesOfferingFragment extends Fragment {

    private View rootView;
    private FragmentEstimateServicesOfferingBinding fragmentCustomerAddressBinding;

    public EstimateServicesOfferingFragment() {
        //default left blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_estimate_services_offering, container, false);
        fragmentCustomerAddressBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }


}
