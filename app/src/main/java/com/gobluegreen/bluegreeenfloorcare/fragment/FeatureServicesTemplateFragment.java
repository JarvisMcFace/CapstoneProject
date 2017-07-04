package com.gobluegreen.bluegreeenfloorcare.fragment;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.bluegreeenfloorcare.R;

/**

 */
public class FeatureServicesTemplateFragment extends Fragment {

    private static final String FEATURE_SERVICE_TITLE_RESOURCE_ID = "com.gobluegreen.bluegreeenfloorcare.fragmet.feature.service.title_id";
    private static final String FEATURE_SERVICE_SUBTITLE_RESOURCE_ID = "com.gobluegreen.bluegreeenfloorcare.fragmet.feature.service.subtitle_id";
    private static final String FEATURE_SERVICE_IMAGE_ID = "com.gobluegreen.bluegreeenfloorcare.fragmet.feature.service.image_resource";

    private int featureServiceTitleResourceId;
    private int featureServiceSubTitleResourceId;
    private int imageResourceId;

    public FeatureServicesTemplateFragment() {
        // Required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param featureServiceTitleResourceId feature text Id
     * @param featureServiceTitleResourceId sub text Id
     * @param imageResourceId               resource Id for Image
     * @return A new instance of fragment FeatureServicesTemplateFragment.
     */
    public static FeatureServicesTemplateFragment newInstance(@StringRes int featureServiceTitleResourceId, @StringRes int featureServiceSubTitleResourceId, @DrawableRes int imageResourceId) {
        FeatureServicesTemplateFragment fragment = new FeatureServicesTemplateFragment();
        Bundle args = new Bundle();
        args.putInt(FEATURE_SERVICE_TITLE_RESOURCE_ID, featureServiceTitleResourceId);
        args.putInt(FEATURE_SERVICE_SUBTITLE_RESOURCE_ID, featureServiceSubTitleResourceId);
        args.putInt(FEATURE_SERVICE_IMAGE_ID, imageResourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            featureServiceTitleResourceId = getArguments().getInt(FEATURE_SERVICE_TITLE_RESOURCE_ID);
            featureServiceSubTitleResourceId = getArguments().getInt(FEATURE_SERVICE_SUBTITLE_RESOURCE_ID);
            imageResourceId = getArguments().getInt(FEATURE_SERVICE_IMAGE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feature_servcies_template, container, false);
    }

}
