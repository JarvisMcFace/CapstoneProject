package com.gobluegreen.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gobluegreen.app.R;
import com.gobluegreen.app.fragment.FeatureServicesTemplateFragment;

/**
 * Created by David on 7/1/17.
 */
public class ServicesPagerAdapter extends FragmentStatePagerAdapter {

    private final static int NUMBER_OF_OFFERED_SERVICES_SCREENS = 2;

    public ServicesPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_OFFERED_SERVICES_SCREENS;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return FeatureServicesTemplateFragment.newInstance(
                        R.string.feature_service_title_we_call_wi_come,
                        R.string.feature_service_sub_title_we_call_wi_come,
                        R.drawable.carpet_cleaning);

            case 1:
                return FeatureServicesTemplateFragment.newInstance(
                        R.string.feature_service_title_we_call_wi_come,
                        R.string.feature_service_sub_title_we_call_wi_come,
                        R.drawable.carpet_cleaning);

        }

        return null;
    }


}
