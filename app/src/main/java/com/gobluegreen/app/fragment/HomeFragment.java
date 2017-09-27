package com.gobluegreen.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.EstimateBuilderLandingActivity;
import com.gobluegreen.app.activity.HomeActivity;
import com.gobluegreen.app.adapter.ServicesPagerAdapter;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.FragmentHomeBinding;
import com.gobluegreen.app.util.PriceFactorCacheHelper;

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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentHomeBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding.servicesViewpager.setAdapter(new ServicesPagerAdapter(getFragmentManager()));
        fragmentHomeBinding.servicesIndicator.setViewPager(fragmentHomeBinding.servicesViewpager);

        fragmentHomeBinding.layoutCardViewEsitmateBuilder.createEstimate.setOnClickListener(createEstimateOnClickListener);
        fragmentHomeBinding.layoutCardViewReviewEsitmates.reviewEstimate.setOnClickListener(createReviewEstimateOnClickListener);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Intent intent = getActivity().getIntent();
        if (intent != null) {
            boolean showSubmitMessage = intent.getBooleanExtra(HomeActivity.EXTRA_HOME_ESITMATED_SUBMITED, false);
            if (showSubmitMessage) {
                showSubmitSnackBar();
            }
        }
    }


    private View.OnClickListener createEstimateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), EstimateBuilderLandingActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener createReviewEstimateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            Intent intent = ReviewEstimateActivity.newIntent(getContext());
//            startActivity(intent);

            GoBluegreenApplication application = (GoBluegreenApplication) getActivity().getApplication();
            PriceFactorCacheHelper.deletesCleaningPriceFactor(application);
        }
    };

    private void showSubmitSnackBar() {

        Snackbar.make(rootView, R.string.success_submit_message, Snackbar.LENGTH_SHORT).show();

    }
}
