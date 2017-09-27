package com.gobluegreen.app.util;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.CleaningPriceFactors;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;

import java.util.List;

/**
 * Created by David on 7/25/17.
 */

public class DeriveEstimatedPriceHighLowRange {

    public static String execute(GoBluegreenApplication application) {

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();

        CleaningPriceFactors cleaningPriceFactors = PriceFactorCacheHelper.getCleaningPriceFactors(application);

        if (cleaningPriceFactors == null) {
            return application.getString(R.string.error_getting_price);

        }
        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            return "";
        }

        double estimatedTotalCost = 0;
        for (RoomTO roomTO : roomTOs) {
            estimatedTotalCost += roomTO.getPriceEstimate();
        }


        double estimatedLowRange = estimatedTotalCost * cleaningPriceFactors.getLowEstRangeFactor();
        double estimatedHighRange = estimatedTotalCost * cleaningPriceFactors.getHighEstRangeFactor();

        String estimatedPriceRange = CurrencyFormatter.execute(estimatedLowRange) + " - " + CurrencyFormatter.execute(estimatedHighRange);

        return estimatedPriceRange;
    }

}
