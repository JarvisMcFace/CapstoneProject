package com.gobluegreen.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.CleaningPriceFactorTO;
import com.gobluegreen.app.to.CleaningPriceFactors;
import com.gobluegreen.app.to.RoomType;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 9/26/17.
 */

public class PriceFactorCacheHelper {

    private static final String SHARED_PREF_PRICE_FACTOR_KEY = "SharedPreferencesPriceFactorFile";


    public static void savePriceFactors(GoBluegreenApplication application, CleaningPriceFactors cleaningPriceFactors) {

        if (cleaningPriceFactors == null) {
            return;
        }

        Gson gson = new Gson();
        String priceFactorString = gson.toJson(cleaningPriceFactors);

        SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREF_PRICE_FACTOR_KEY, priceFactorString);
        editor.apply();
    }

    public static void deletesCleaningPriceFactor(GoBluegreenApplication application) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SHARED_PREF_PRICE_FACTOR_KEY);
        editor.apply();
    }

    public static CleaningPriceFactors getCleaningPriceFactors(GoBluegreenApplication application) {

        try {
            SharedPreferences sharedPreferences = application.getSharedPreferences(SharedPreferenceFileName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

            if (!sharedPreferences.contains(SHARED_PREF_PRICE_FACTOR_KEY)) {
                return null;
            }

            String savedEstimate = sharedPreferences.getString(SHARED_PREF_PRICE_FACTOR_KEY, null);

            if (StringUtils.isEmpty(savedEstimate)) {
                return null;
            }

            Gson gson = new Gson();
            return gson.fromJson(savedEstimate, CleaningPriceFactors.class);

        } catch (Exception e) {
            Log.e("PriceFactorCacheHelper", Log.getStackTraceString(e));
            return null;
        }
    }

    public static Map<RoomType, CleaningPriceFactorTO> getCleaningPriceFactorTOMap(GoBluegreenApplication application) {


        CleaningPriceFactors cleaningPriceFactors = getCleaningPriceFactors(application);


        Map<RoomType, CleaningPriceFactorTO> cleaningPriceFactorTOMap = new HashMap<>();

        List<CleaningPriceFactorTO> cleaningPriceFactorTOs = cleaningPriceFactors.getCleaningPriceFactorTOs();

        for (CleaningPriceFactorTO cleaningPriceFactorTO : cleaningPriceFactorTOs) {
            cleaningPriceFactorTOMap.put(cleaningPriceFactorTO.getRoomType(), cleaningPriceFactorTO);
        }

        return cleaningPriceFactorTOMap;
    }
}

