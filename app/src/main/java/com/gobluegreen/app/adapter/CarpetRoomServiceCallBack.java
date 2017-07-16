package com.gobluegreen.app.adapter;

import com.gobluegreen.app.to.RoomTO;

/**
 * Created by David on 7/15/17.
 */

public interface CarpetRoomServiceCallBack {

    void updateRoomLength(RoomTO updateRoomTO);
    void updateRoomWidth(RoomTO updateRoomTO);
}
