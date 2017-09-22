package com.gobluegreen.app.data.estimate;

import android.database.Cursor;
import android.util.Log;

import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.ReviewEstimateTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by David on 9/19/17.
 */

public class ReviewEstimateCursorHelper {

    private static final String TAG = ReviewEstimateCursorHelper.class.getSimpleName();

    public static List<ReviewEstimateTO> retrieveAllEstimates(Cursor cursor) {

        List<ReviewEstimateTO> reviewEstimateTOs = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast() && cursor.getCount() > 0) {
                ReviewEstimateTO reviewEstimateTO = retrieveReviewEstimateTO(cursor);
                if (reviewEstimateTO != null) {
                    reviewEstimateTOs.add(reviewEstimateTO);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return reviewEstimateTOs;
    }

    public static ReviewEstimateTO retrieveReviewEstimateTO(Cursor cursor) {

        //Estimate
        final int estimateDateIndex = cursor.getColumnIndex(EstimateContract.ESTIMATE_DATE);
        final int numberOfRoomsIndex = cursor.getColumnIndex(EstimateContract.ESTIMATE_NUMBER_ROOMS);
        final int priceEstimateIndex = cursor.getColumnIndex(EstimateContract.ESTIMATE_PRICE_ESTIMATE);
        final int estimatesqftIndex = cursor.getColumnIndex(EstimateContract.ESTIMATE_SQFT);

        //CustomerTO
        final int customerTypeIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_TYPE);
        final int firstNameIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_FIRST_NAME);
        final int lastNameIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_LAST_NAME);
        final int address1Index = cursor.getColumnIndex(EstimateContract.CUSTOMER_ADDRESS1);
        final int cityIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_CITY);
        final int stateIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_STATE);
        final int zipCodeIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_ZIP);
        final int phoneNumberIndex = cursor.getColumnIndex(EstimateContract.CUSTOMER_PHONE_NUMBER);


        try {
            String estimateDateValue = cursor.getString(estimateDateIndex);
            String numberOfRoomsValue = cursor.getString(numberOfRoomsIndex);
            String priceEstimateValue = cursor.getString(priceEstimateIndex);
            String estimatesqftValue = cursor.getString(estimatesqftIndex);

            //CustomerTO
            String customerTypeValue = cursor.getString(customerTypeIndex);
            String firstNameValue = cursor.getString(firstNameIndex);
            String lastNameValue = cursor.getString(lastNameIndex);
            String address1Value = cursor.getString(address1Index);
            String cityValue = cursor.getString(cityIndex);
            String stateValue = cursor.getString(stateIndex);
            String zipCodeValue = cursor.getString(zipCodeIndex);
            String phoneNumberValue = cursor.getString(phoneNumberIndex);


            ReviewEstimateTO reviewEstimateTO = new ReviewEstimateTO();

            CustomerTO customerTO = new CustomerTO();
            CustomerType customerType = CustomerType.valueOf(customerTypeValue);
            customerTO.setCustomerType(customerType);
            customerTO.setFirstName(firstNameValue);
            customerTO.setLastName(lastNameValue);
            customerTO.setAddress1(address1Value);
            customerTO.setCity(cityValue);
            customerTO.setState(stateValue);
            customerTO.setZipCode(zipCodeValue);
            customerTO.setPhoneNumber(phoneNumberValue);


            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(Long.valueOf(estimateDateValue));

            reviewEstimateTO.setCustomerTO(customerTO);
            reviewEstimateTO.setEstimateDate(calendar);
            reviewEstimateTO.setPriceEstimatesRange(priceEstimateValue);
            reviewEstimateTO.setNumberOfRooms(numberOfRoomsValue);
            reviewEstimateTO.setEstimatedSqFt(estimatesqftValue);

            return reviewEstimateTO;
        }catch (Exception ex) {
            Log.e(TAG, "getReviewEstimateData: ", ex);
            return null;
        }



    }


}
