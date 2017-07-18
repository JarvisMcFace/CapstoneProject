package com.gobluegreen.app.adapter;

import android.widget.TextView;

import com.gobluegreen.app.to.RoomTO;

/**
 * Created by David on 7/15/17.
 */

public interface CarpetRoomServiceCallBack {

    void updateRoomLength(RoomTO updateRoomTO, int position);
    void updateRoomWidth(RoomTO updateRoomTO, int position, TextView estimatedPrice);
}
