package com.gobluegreen.app.application;

import android.app.Application;

import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/5/17.
 */

public class GoBluegreenApplication extends Application {

    private EstimateInProgressTO estimateInProgressTO;


    public EstimateInProgressTO getEstimateInProgressTO() {
        return estimateInProgressTO;
    }

    public void setEstimateInProgressTO(EstimateInProgressTO estimateInProgressTO) {
        this.estimateInProgressTO = estimateInProgressTO;
    }
}
