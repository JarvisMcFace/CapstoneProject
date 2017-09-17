package com.gobluegreen.app.data.estimate;

/**
 * Created by David on 7/17/17.
 */
public class EstimateContract {

    //EstimateInProgressTO
    public static final String ESTIMATE_ID = "id";
    public static final String ESTIMATE_DATE = "estimateDate";
    public static final String ESTIMATE_DAVE= "estimatDave"; //TODO David

    //ServiceType
    public static final String SERVICE_ID = "_id";
    public static final String SERVICE_TYPE = "serviceType";
    public static final String SERVICE_ESTIMATE_ID = "estimate_id";

    //CustomerTO
    public static final String CUSTOMER_ID = "id";
    public static final String CUSTOMER_TYPE = "customerType";
    public static final String CUSTOMER_FIRST_NAME = "firstName";
    public static final String CUSTOMER_LAST_NAME = "lastName";
    public static final String CUSTOMER_ADDRESS1 = "address1";
    public static final String CUSTOMER_CITY = "city";
    public static final String CUSTOMER_STATE = "state";
    public static final String CUSTOMER_ZIP = "zipCode";
    public static final String CUSTOMER_PHONE_NUMBER = "phoneNumber";
    public static final String CUSTOMER_ESITMATE_ID = "estimateId";

    //ROOM
    public static final String ROOM_ID = "id";
    public static final String ROOM_LENGTH = "length";
    public static final String ROOM_WIDTH = "width";
    public static final String ROOM_MOVE_FURNITURE = "moveFurniture";
    public static final String ROOM_CARPET_PROTECTOR = "carpetProtector";
    public static final String ROOM_SQUARE_FEET = "squareFeet";
    public static final String ROOM_PRICE_ESTIMATE = "priceEstimate";
    public static final String ROOM_ESTIMATE_SQFT = "estimatesqft";
    public static final String ROOM_ESITMATE_ID = "estimateId";


}
