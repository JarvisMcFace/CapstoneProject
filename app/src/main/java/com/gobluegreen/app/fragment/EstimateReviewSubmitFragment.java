package com.gobluegreen.app.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.HomeActivity;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.data.PersistentAsyncListener;
import com.gobluegreen.app.data.PersistentAsyncQueryHandler;
import com.gobluegreen.app.data.estimate.ContentValueCreator;
import com.gobluegreen.app.data.estimate.EstimateContentProvider;
import com.gobluegreen.app.data.estimate.EstimateDbAdapter;
import com.gobluegreen.app.databinding.FragmentEstimateReviewSubmitBinding;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;
import com.gobluegreen.app.util.DeriveEstimatedPriceHighLowRange;
import com.gobluegreen.app.util.DeriveEstimatedTotalSquareFeet;
import com.gobluegreen.app.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 7/14/17.
 */
public class EstimateReviewSubmitFragment extends Fragment implements PersistentAsyncListener {

    private View rootView;
    private FragmentEstimateReviewSubmitBinding binding;
    private GoBluegreenApplication application;
    private EstimateInProgressTO estimateInProgressTO;
    private ContentResolver contentResolver;
    private PersistentAsyncQueryHandler persistentAsyncQueryHandler;
    private List<String> tokenInProgress;

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

    private static final int ESTIMATE_TOKEN = 1;
    private static final int ESTIMATE_CUSTOEMR_TOKEN = 2;
    private static final int ESTIMATE_ROOM_TOKEN = 3;
    private static final int ESTIMATE_SERVICES_TOKEN = 4;


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

    private void submitEstimate() {

        int disabledColor = ContextCompat.getColor(getContext(), R.color.disabled_gray);
        binding.landingSubitButton.setEnabled(false);
        binding.buttonSubmitEstiamte.setTextColor(disabledColor);
        binding.progressHorizontal.setVisibility(View.VISIBLE);

        tokenInProgress = new ArrayList<>();

        Uri uri = EstimateContentProvider.CONTENT_URI;
        tokenInProgress.add(EstimateDbAdapter.ESTIMATE_TABLE);
        contentResolver = application.getContentResolver();
        persistentAsyncQueryHandler = new PersistentAsyncQueryHandler(contentResolver, this);

        ContentValues contentValues = ContentValueCreator.createEstimateValues(estimateInProgressTO);
        persistentAsyncQueryHandler.startInsert(ESTIMATE_TOKEN, null, uri, contentValues);


    }

    @Override
    public void onTransactionComplete(int processedToken, Object returnValue) {

        ContentValues contentValues = null;
        Uri uri = null;

        switch (processedToken) {

            case ESTIMATE_TOKEN:

                tokenInProgress.remove(EstimateDbAdapter.ESTIMATE_TABLE);

                long estimateTokenId = (long) returnValue;

                //Customer
                tokenInProgress.add(EstimateDbAdapter.CUSTOMER_TABLE);
                contentValues = ContentValueCreator.createCustomerValues(estimateInProgressTO, estimateTokenId);
                Uri customerUri = EstimateContentProvider.CONTENT_URI.buildUpon().appendPath(EstimateDbAdapter.CUSTOMER_TABLE).build();
                persistentAsyncQueryHandler.startInsert(ESTIMATE_CUSTOEMR_TOKEN, null, customerUri, contentValues);

                //Rooms
                Uri roomUri = EstimateContentProvider.CONTENT_URI.buildUpon().appendPath(EstimateDbAdapter.ROOM_TABLE).build();
                ContentValues[] roomContentValues = ContentValueCreator.createRoomValues(estimateInProgressTO, estimateTokenId);

                if (roomContentValues != null){
                    for (ContentValues roomContentValue : roomContentValues) {
                        tokenInProgress.add(EstimateDbAdapter.ROOM_TABLE);
                        persistentAsyncQueryHandler.startInsert(ESTIMATE_ROOM_TOKEN, null, roomUri, roomContentValue);
                    }
                }

                //Services
                Uri servicesUri = EstimateContentProvider.CONTENT_URI.buildUpon().appendPath(EstimateDbAdapter.SERVICE_TYPE_TABLE).build();
                ContentValues[] servicesContentValues = ContentValueCreator.createServicesValues(estimateInProgressTO, estimateTokenId);

                if (servicesContentValues != null){
                    for (ContentValues servicesContentValue : servicesContentValues) {
                        tokenInProgress.add(EstimateDbAdapter.SERVICE_TYPE_TABLE);
                        persistentAsyncQueryHandler.startInsert(ESTIMATE_SERVICES_TOKEN, null, servicesUri, servicesContentValue);
                    }
                }
                break;

            case ESTIMATE_CUSTOEMR_TOKEN:
                tokenInProgress.remove(EstimateDbAdapter.CUSTOMER_TABLE);
                break;

            case ESTIMATE_ROOM_TOKEN:
                tokenInProgress.remove(EstimateDbAdapter.ROOM_TABLE);
                break;

            case ESTIMATE_SERVICES_TOKEN:
                tokenInProgress.remove(EstimateDbAdapter.SERVICE_TYPE_TABLE);
                break;
        }

        if (ListUtils.isEmpty(tokenInProgress)) {
            binding.progressHorizontal.setVisibility(View.GONE);

            CarpetQuoteCacheUtility.deleteEstimateInProgress(application);
            Intent intent = HomeActivity.newIntent(getContext());
            startActivity(intent);
        }
    }
}
