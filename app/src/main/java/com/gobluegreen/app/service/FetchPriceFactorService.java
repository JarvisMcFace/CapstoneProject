package com.gobluegreen.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.CleaningPriceFactors;
import com.gobluegreen.app.util.PriceFactorCacheHelper;
import com.gobluegreen.app.util.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by David on 9/25/17.
 */

public class FetchPriceFactorService extends IntentService {

    private static final String TAG = FetchPriceFactorService.class.getSimpleName();
    private OkHttpClient client = new OkHttpClient();
    private GoBluegreenApplication application;


    public FetchPriceFactorService() {
        super(FetchPriceFactorService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        application = (GoBluegreenApplication) getApplication();

        if (intent == null) {
            return;
        }


        fetchPriceFactors();
    }

    private void fetchPriceFactors() {

        String fireBaseUrl = application.getString(R.string.firebase_api_url);

        Request.Builder builder = new Request.Builder();
        builder.url(fireBaseUrl);

        Request request = builder.build();

        Response response = null;
        String jsonResults = null;
        try {
            response = client.newCall(request).execute();
            jsonResults = response.body().string();
        } catch (IOException e) {
            Log.e("OkHttpHelper", "doInBackground: ", e);
            e.printStackTrace();
        }


        if (StringUtils.isNotEmpty(jsonResults)) {

            CleaningPriceFactors cleaningPriceFactors = new CleaningPriceFactors();

            Gson gson = new Gson();
            cleaningPriceFactors = gson.fromJson(jsonResults, CleaningPriceFactors.class);

            PriceFactorCacheHelper.savePriceFactors(application, cleaningPriceFactors);

        }
    }


}
