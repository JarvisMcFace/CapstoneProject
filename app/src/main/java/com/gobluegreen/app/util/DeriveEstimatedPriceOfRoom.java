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


        Map<RoomType, CleaningPriceFactorTO> cleaningPriceFactorTOMap =  application.getCleaningPriceFactorTOMap();

        CleaningPriceFactorTO cleaningPriceFactorTO = cleaningPriceFactorTOMap.get(roomTO.getRoomType());


        double squareFeetFactor = cleaningPriceFactorTO.getSquareFeetFactor();
        double pricePerSquareFeet = cleaningPriceFactorTO.getPricePerSquareFeet();
        double carpetProtectorFactor = cleaningPriceFactorTO.getCarpetProtectorFactor();

        double carpetProtectorCost = 0;
        if (roomTO.isCarpetProtector()){
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

    private double squareFeetFactor;
    private double pricePerSquareFeet;  // $.5
    private double moveFurnitureFactor;    //no value
    private double carpetProtectorFactor;   //sq *.
}
