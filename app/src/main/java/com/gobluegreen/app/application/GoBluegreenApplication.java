package com.gobluegreen.app.application;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/5/17.
 */

public class GoBluegreenApplication extends Application {

    private EstimateInProgressTO estimateInProgressTO;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());


    }

    public EstimateInProgressTO getEstimateInProgressTO() {
        return estimateInProgressTO;
    }

    public void setEstimateInProgressTO(EstimateInProgressTO estimateInProgressTO) {
        this.estimateInProgressTO = estimateInProgressTO;
    }
}
