package com.gobluegreen.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentEstimateReviewSubmitBinding;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;

/**
 * Created by David on 7/14/17.
 */
public class EstimateReviewSubmitFragment extends Fragment {

    private View rootView;
    private FragmentEstimateReviewSubmitBinding binding;
    private GoBluegreenApplication application;
    private EstimateInProgressTO estimateInProgressTO;

    public EstimateReviewSubmitFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_estimate_review_submit, container, false);
        binding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (GoBluegreenApplication) getActivity().getApplication();

        estimateInProgressTO = CarpetQuoteCacheUtility.getEstimateInProgress(application);
        binding.layoutEstimateReviewSubmit.setEstimate(estimateInProgressTO);
    }

}
