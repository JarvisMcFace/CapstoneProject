package com.gobluegreen.bluegreeenfloorcare.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.bluegreeenfloorcare.R;
import com.gobluegreen.bluegreeenfloorcare.databinding.HomeFragmentBinding;
import com.gobluegreen.bluegreeenfloorcare.adapter.ServicesPagerAdapter;

/**
 *
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private HomeFragmentBinding homeFragmentBinding;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.home_fragment, container, false);
        homeFragmentBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        homeFragmentBinding.servicesViewpager.setAdapter(new ServicesPagerAdapter(getFragmentManager()));
        homeFragmentBinding.servicesIndicator.setViewPager(homeFragmentBinding.servicesViewpager);
//        homeFragmentBinding.servicesViewpager.setCurrentItem(2);

    }
}
