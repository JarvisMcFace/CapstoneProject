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
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.DeriveEstimatedPriceHighLowRange;
import com.gobluegreen.app.util.DeriveEstimatedTotalSquareFeet;
import com.gobluegreen.app.util.ListUtils;
import com.google.gson.Gson;

import java.util.List;

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

        updateReviewQuotes();

        binding.landingSubitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEstimate();
            }
        });


    }

    private void submitEstimate() {

        Gson gson = new Gson();

        String estimateString = gson.toJson(estimateInProgressTO, EstimateInProgressTO.class);




    }

    private void updateReviewQuotes() {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        if (CustomerType.COMMERCIAL == customerTO.getCustomerType()) {
            return;
        }

        String priceEstimateRange = DeriveEstimatedPriceHighLowRange.execute(application);
        int squareFeetEstimate = DeriveEstimatedTotalSquareFeet.execute(application);


        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();

        if (ListUtils.isEmpty(roomTOs)) {
            return;
        }

        binding.layoutEstimateReviewSubmit.estimatePriceRangeTitle.setVisibility(View.VISIBLE);
        binding.layoutEstimateReviewSubmit.estimateSquareFeetTitle.setVisibility(View.VISIBLE);
        binding.layoutEstimateReviewSubmit.estimatePriceRange.setVisibility(View.VISIBLE);
        binding.layoutEstimateReviewSubmit.estimateTotalSquareFeet.setVisibility(View.VISIBLE);

        binding.layoutEstimateReviewSubmit.estimatePriceRange.setText(priceEstimateRange);
        String sqft = getResources().getString(R.string.square_feet);
        binding.layoutEstimateReviewSubmit.estimateTotalSquareFeet.setText(squareFeetEstimate + " " + sqft);

    }

}
