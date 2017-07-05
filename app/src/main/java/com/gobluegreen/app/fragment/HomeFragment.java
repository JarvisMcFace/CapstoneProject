package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.CustomerAddress;
import com.gobluegreen.app.adapter.ServicesPagerAdapter;
import com.gobluegreen.app.databinding.FragmentHomeBinding;

/**
 *
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private FragmentHomeBinding fragmentHomeBinding;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        fragmentHomeBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding.servicesViewpager.setAdapter(new ServicesPagerAdapter(getFragmentManager()));
        fragmentHomeBinding.servicesIndicator.setViewPager(fragmentHomeBinding.servicesViewpager);

        fragmentHomeBinding.createEstimate.setOnClickListener(ceateEstimateOnClickListener);

    }


    private View.OnClickListener ceateEstimateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), CustomerAddress.class);
            startActivity(intent);
        }
    };
}
