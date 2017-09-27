package com.gobluegreen.app.util;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.CleaningPriceFactorTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.to.RoomType;

import java.util.Map;

/**
 * Created by David on 7/24/17.
 */

public class DeriveEstimatedPriceOfRoom {

    public static double execute(GoBluegreenApplication application, RoomTO roomTO) {


        Map<RoomType, CleaningPriceFactorTO> cleaningPriceFactorTOMap = PriceFactorCacheHelper.getCleaningPriceFactorTOMap(application);

        CleaningPriceFactorTO cleaningPriceFactorTO = cleaningPriceFactorTOMap.get(roomTO.getRoomType());

        if (roomTO.getRoomType() == RoomType.STAIRWAY_LANDING) {

            double perStepPrice = cleaningPriceFactorTO.getPricePerStep();
            return roomTO.getNumberSteps() * perStepPrice;
        }

        double squareFeetFactor = cleaningPriceFactorTO.getSquareFeetFactor();
        double pricePerSquareFeet = cleaningPriceFactorTO.getPricePerSquareFeet();
        double carpetProtectorFactor = cleaningPriceFactorTO.getCarpetProtectorFactor();

        double carpetProtectorCost = 0;
        if (roomTO.isCarpetProtector()) {
            carpetProtectorCost = calculateCarpetProtectorPrice(roomTO, carpetProtectorFactor);
        }

        int estimatedSquareFeetToQuote = (int) (roomTO.getSquareFeet() * squareFeetFactor);

        return estimatedSquareFeetToQuote + carpetProtectorCost;
    }

    private static double calculateCarpetProtectorPrice(RoomTO roomTO, double carpetProtectorFactor) {

        if (roomTO.isCarpetProtector()) {
            return roomTO.getSquareFeet() * carpetProtectorFactor;
        }

        return 0;
    }
}
