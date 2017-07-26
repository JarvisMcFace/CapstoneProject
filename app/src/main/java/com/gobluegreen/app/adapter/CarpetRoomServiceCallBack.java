package com.gobluegreen.app.adapter;

import com.gobluegreen.app.to.RoomTO;

/**
 * Created by David on 7/15/17.
 */

public interface CarpetRoomServiceCallBack {

    String showEstimatedPrice(RoomTO roomTO);

    void updateEstimatedPriceRange();

    void updateEstimatedTotalSquareFeet();
}
