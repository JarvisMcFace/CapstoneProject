package com.gobluegreen.app.fragment;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.ReviewEstimateAdapter;
import com.gobluegreen.app.data.EstimateLoader;
import com.gobluegreen.app.databinding.FragmentReviewEstimatesBinding;

/**
 * Created by David on 7/5/17.
 */
public class ReviewEstimateFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ReviewEstimateFragment.class.getSimpleName();
    private static final int LOADER_ID = 0;
    private View rootView;
    private FragmentReviewEstimatesBinding reviewBinding;

    public ReviewEstimateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_estimates, container, false);
        reviewBinding = DataBindingUtil.bind(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        reviewBinding.reviewEstimateRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return EstimateLoader.loadAllEstimates(getContext());
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        ReviewEstimateAdapter reviewEstimateAdapter = new ReviewEstimateAdapter(cursor);
        reviewBinding.reviewEstimateRecyclerView.setAdapter(reviewEstimateAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        reviewBinding.reviewEstimateRecyclerView.setAdapter(null);
    }
}



