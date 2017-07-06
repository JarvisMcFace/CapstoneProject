package com.gobluegreen.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateBuilderLandingBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/5/17.
 */
public class EstimateBuilderLandingFragment extends Fragment {

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
        initViews();
    }

    private void initViews() {

        landingBinding.servicesSelectionCarpetCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    landingBinding.carpetCleaningServicesCardview.setVisibility(View.VISIBLE);
                } else {
                    landingBinding.carpetCleaningServicesCardview.setVisibility(View.GONE);
                    //TODO cleanup estimate object
                }
            }
        });
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
