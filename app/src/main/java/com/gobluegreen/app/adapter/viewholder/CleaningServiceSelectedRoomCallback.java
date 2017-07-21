package com.gobluegreen.app.adapter.viewholder;

import com.gobluegreen.app.to.RoomType;

/**
 * Created by David on 7/20/17.
 */

public interface CleaningServiceSelectedRoomCallback {

    void onRoomSelected(boolean isChecked, RoomType roomType);
}
