package com.gobluegreen.app.fragment;

/**
 * Created by David on 10/4/17.
 */

import com.gobluegreen.app.to.LocationTO;

public interface LocationsCallBack {

    void moveToLocation(LocationTO locationTO);

    void startGoogleMaps(LocationTO locationTO);
}
