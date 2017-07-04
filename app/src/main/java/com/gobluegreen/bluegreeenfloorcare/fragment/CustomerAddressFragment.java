package com.gobluegreen.bluegreeenfloorcare.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.bluegreeenfloorcare.R;
import com.gobluegreen.bluegreeenfloorcare.databinding.FragmentCustomerAddressBinding;

/**
 * Created by David on 7/4/17.
 */

public class CustomerAddressFragment extends Fragment {

    private View rootView;
    private FragmentCustomerAddressBinding fragmentCustomerAddressBinding;


    public CustomerAddressFragment() {
        //default left blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_customer_address, container, false);
        fragmentCustomerAddressBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
