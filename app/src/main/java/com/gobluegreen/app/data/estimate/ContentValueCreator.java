package com.gobluegreen.app.data.estimate;

import android.content.ContentValues;

import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.to.ServiceType;
import com.gobluegreen.app.util.ListUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by David on 9/16/17.
 */

public class ContentValueCreator {

    private static final String EMPTY_STRING = "";

    public static ContentValues createEstimateValues(EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EstimateContract.ESTIMATE_DAVE, customerTO.getFirstName());

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

    public static ContentValues[] createRoomValues(EstimateInProgressTO estimateInProgressTO, long estimateTokenId) {

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();

        if (ListUtils.isEmpty(roomTOs)) {
            return null;
        }

        int numberRooms = roomTOs.size();
        ContentValues[] createRoomValues = new ContentValues[numberRooms];

        for (int i = 0; i < numberRooms; i++) {

            RoomTO roomTO = roomTOs.get(i);

            ContentValues value = new ContentValues();
            value.put(EstimateContract.ROOM_LENGTH, safeInt(roomTO.getLength()));
            value.put(EstimateContract.ROOM_WIDTH, safeInt(roomTO.getWidth()));
            if (roomTO.isCarpetProtector()) {
                value.put(EstimateContract.ROOM_CARPET_PROTECTOR, "true");
            } else {
                value.put(EstimateContract.ROOM_CARPET_PROTECTOR, "false");
            }

            value.put(EstimateContract.ROOM_SQUARE_FEET, safeInt(roomTO.getSquareFeet()));
            value.put(EstimateContract.ROOM_PRICE_ESTIMATE, safeDouble(roomTO.getPriceEstimate()));
            value.put(EstimateContract.ROOM_ESTIMATE_SQFT, safeInt(roomTO.getEstimatedSquareFeet()));
            value.put(EstimateContract.ROOM_ESITMATE_ID, estimateTokenId);

            createRoomValues[i] = value;
        }

        return createRoomValues;
    }

    public static ContentValues[] createServicesValues(EstimateInProgressTO estimateInProgressTO, long estimateTokenId) {

        Set<ServiceType> serviceTypeSet = estimateInProgressTO.getServiceTypeSet();

        if (serviceTypeSet == null) {
            return null;
        }

        int numberServices = serviceTypeSet.size();
        ContentValues[] createRoomValues = new ContentValues[numberServices];

        int serviceIndex = 0;
        for (ServiceType serviceType : serviceTypeSet) {
            ContentValues value = new ContentValues();
            value.put(EstimateContract.SERVICE_TYPE, serviceType.toString());
            value.put(EstimateContract.SERVICE_ESTIMATE_ID, estimateTokenId);
            createRoomValues[serviceIndex] = value;
            serviceIndex++;

        }
        return createRoomValues;
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
