package com.gobluegreen.app.to;

import java.io.Serializable;
import java.util.List;

/**
 * Created by David on 7/24/17.
 */

public class CleaningPriceFactors implements Serializable {

    private static final long serialVersionUID = 2858258483826818466L;

    private List<CleaningPriceFactorTO> cleaningPriceFactorTOs;
    private double lowEstRangeFactor;  // .9
    private double highEstRangeFactor; // 1.1

    public List<CleaningPriceFactorTO> getCleaningPriceFactorTOs() {
        return cleaningPriceFactorTOs;
    }

    public void setCleaningPriceFactorTOs(List<CleaningPriceFactorTO> cleaningPriceFactorTOs) {
        this.cleaningPriceFactorTOs = cleaningPriceFactorTOs;
    }

    public double getLowEstRangeFactor() {
        return lowEstRangeFactor;
    }

    public void setLowEstRangeFactor(double lowEstRangeFactor) {
        this.lowEstRangeFactor = lowEstRangeFactor;
    }

    public double getHighEstRangeFactor() {
        return highEstRangeFactor;
    }

    public void setHighEstRangeFactor(double highEstRangeFactor) {
        this.highEstRangeFactor = highEstRangeFactor;
    }
}
