package com.gobluegreen.app.data.estimate;

import android.content.ContentValues;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.util.DeriveEstimatedPriceHighLowRange;
import com.gobluegreen.app.util.DeriveEstimatedTotalSquareFeet;
import com.gobluegreen.app.util.ListUtils;

import java.util.List;

/**
 * Created by David on 9/16/17.
 */

public class ContentValueCreator {

    private static final String EMPTY_STRING = "";

    public static ContentValues createEstimateValues(GoBluegreenApplication application) {

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();

        int estimatedSquareFeet = DeriveEstimatedTotalSquareFeet.execute(application);
        String priceEstimate = DeriveEstimatedPriceHighLowRange.execute(application);

        int numberOfRooms = 0;
        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isNotEmpty(roomTOs)) {
            numberOfRooms = roomTOs.size();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(EstimateContract.ESTIMATE_NUMBER_ROOMS, safeString(String.valueOf(numberOfRooms)));
        contentValues.put(EstimateContract.ESTIMATE_SQFT, safeString(String.valueOf(estimatedSquareFeet)));
        contentValues.put(EstimateContract.ESTIMATE_PRICE_ESTIMATE, safeString(priceEstimate));

        return contentValues;
    }

    public static ContentValues createCustomerValues(EstimateInProgressTO estimateInProgressTO, long estimateTokenId) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EstimateContract.CUSTOMER_TYPE, safeString(customerTO.getCustomerType().toString()));
        contentValues.put(EstimateContract.CUSTOMER_FIRST_NAME, safeString(customerTO.getFirstName()));
        contentValues.put(EstimateContract.CUSTOMER_LAST_NAME, safeString(customerTO.getLastName()));
        contentValues.put(EstimateContract.CUSTOMER_ADDRESS1, safeString(customerTO.getAddress1()));
        contentValues.put(EstimateContract.CUSTOMER_CITY, safeString(customerTO.getCity()));
        contentValues.put(EstimateContract.CUSTOMER_STATE, safeString(customerTO.getState()));
        contentValues.put(EstimateContract.CUSTOMER_ZIP, safeString(customerTO.getZipCode()));
        contentValues.put(EstimateContract.CUSTOMER_PHONE_NUMBER, safeString(customerTO.getPhoneNumber()));
        contentValues.put(EstimateContract.CUSTOMER_ESITMATE_ID, estimateTokenId);

        return contentValues;
    }

    private static String safeDouble(double value) {
        final boolean invalid = Double.isNaN(value);
        return invalid ? EMPTY_STRING : String.valueOf(value);
    }


    private static String safeString(String input) {
        return input == null ? EMPTY_STRING : input;
    }

    private static String safeInt(int input) {
        return String.valueOf(input);
    }


}
