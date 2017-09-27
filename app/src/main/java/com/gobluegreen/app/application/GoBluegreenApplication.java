package com.gobluegreen.app.application;

import android.app.Application;
import android.content.Intent;

import com.facebook.stetho.Stetho;
import com.gobluegreen.app.service.FetchPriceFactorService;
import com.gobluegreen.app.to.CleaningPriceFactors;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/5/17.
 */

public class GoBluegreenApplication extends Application {

    private static final String TAG = GoBluegreenApplication.class.getSimpleName();

    private EstimateInProgressTO estimateInProgressTO;
    private CleaningPriceFactors cleaningPriceFactors;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());

        Intent intent = new Intent(this, FetchPriceFactorService.class);
        this.startService(intent);
    }

    public EstimateInProgressTO getEstimateInProgressTO() {
        return estimateInProgressTO;
    }

    public void setEstimateInProgressTO(EstimateInProgressTO estimateInProgressTO) {
        this.estimateInProgressTO = estimateInProgressTO;
    }




}
