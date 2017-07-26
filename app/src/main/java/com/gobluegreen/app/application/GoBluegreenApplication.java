package com.gobluegreen.app.application;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.gobluegreen.app.to.CleaningPriceFactorTO;
import com.gobluegreen.app.to.CleaningPriceFactors;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomType;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 7/5/17.
 */

public class GoBluegreenApplication extends Application {

    private static final String TAG = GoBluegreenApplication.class.getSimpleName();

    private EstimateInProgressTO estimateInProgressTO;
    private CleaningPriceFactors cleaningPriceFactors;
    private Map<RoomType, CleaningPriceFactorTO> cleaningPriceFactorTOMap;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());


        Gson gson = new Gson();

        cleaningPriceFactors = gson.fromJson(testCleaningCarpetPrices, CleaningPriceFactors.class);

        populatePriceMap();
        Log.d(TAG, "David: " + "onCreate() called");

    }

    public EstimateInProgressTO getEstimateInProgressTO() {
        return estimateInProgressTO;
    }

    public void setEstimateInProgressTO(EstimateInProgressTO estimateInProgressTO) {
        this.estimateInProgressTO = estimateInProgressTO;
    }

    public CleaningPriceFactors getCleaningPriceFactors() {
        return cleaningPriceFactors;
    }

    public Map<RoomType, CleaningPriceFactorTO> getCleaningPriceFactorTOMap() {
        return cleaningPriceFactorTOMap;
    }

    private String testCleaningCarpetPrices = "{\"cleaningPriceFactorTOs\":[{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"LIVING\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"DINING\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"DEN_OFFICE\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"MASTER\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"BEDROOM_2\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"FAMILY_GREAT\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"RECREATION\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"STAIRWAY_LANDING\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"HALLWAY\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"ADDITIONAL_ROOM1\",\"squareFeetFactor\":0.8},{\"carpetProtectorFactor\":0.2,\"moveFurnitureFactor\":0.0,\"pricePerSquareFeet\":0.5,\"roomType\":\"ADDITIONAL_ROOM2\",\"squareFeetFactor\":0.8}],\"highEstRangeFactor\":1.1,\"lowEstRangeFactor\":0.9}";

    private void populatePriceMap() {
        cleaningPriceFactorTOMap = new HashMap<>();

        List<CleaningPriceFactorTO> cleaningPriceFactorTOs = cleaningPriceFactors.getCleaningPriceFactorTOs();

        for (CleaningPriceFactorTO cleaningPriceFactorTO : cleaningPriceFactorTOs) {
            cleaningPriceFactorTOMap.put(cleaningPriceFactorTO.getRoomType(), cleaningPriceFactorTO);
        }
    }

}
