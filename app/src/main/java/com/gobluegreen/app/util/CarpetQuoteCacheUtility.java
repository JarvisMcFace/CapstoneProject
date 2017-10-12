package com.gobluegreen.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.google.gson.Gson;

/**
 * Created by David on 7/11/17.
 */

public class CarpetQuoteCacheUtility {


    private static final String SAVED_CARPET_ESTIMATE_KEY = "com.gobluegreen.app.estimate.save";

    public static void saveEstimateInProgress(GoBluegreenApplication application) {

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();

        if (estimateInProgressTO == null) {
            return;
        }

        Gson gson = new Gson();
        String estimateString = gson.toJson(estimateInProgressTO);

        SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_CARPET_ESTIMATE_KEY, estimateString);
        editor.apply();
    }

    public static void deleteEstimateInProgress(GoBluegreenApplication application) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SAVED_CARPET_ESTIMATE_KEY);
        editor.apply();
    }

    public static EstimateInProgressTO getEstimateInProgress(GoBluegreenApplication application) {

        try {
            SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);


            if (!sharedPreferences.contains(SAVED_CARPET_ESTIMATE_KEY)) {
                return null;
            }

            String savedEstimate = sharedPreferences.getString(SAVED_CARPET_ESTIMATE_KEY, null);

            if (StringUtils.isEmpty(savedEstimate)) {
                return null;
            }

            Gson gson = new Gson();
            EstimateInProgressTO estimateInProgressTO = gson.fromJson(savedEstimate, EstimateInProgressTO.class);
            return estimateInProgressTO;

        } catch (Exception e) {
            Log.e("CarpetQuoteCacheUtility", Log.getStackTraceString(e));
            return null;
        }
    }
}
