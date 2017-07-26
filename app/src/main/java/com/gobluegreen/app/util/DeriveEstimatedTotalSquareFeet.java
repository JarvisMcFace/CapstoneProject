package com.gobluegreen.app.util;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomTO;

import java.util.List;

/**
 * Created by David on 7/25/17.
 */

public class DeriveEstimatedTotalSquareFeet {

    public static int execute(GoBluegreenApplication application) {

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            return 0;
        }

        int estimatedSquareFeet = 0;

        for (RoomTO roomTO : roomTOs) {
            estimatedSquareFeet += roomTO.getSquareFeet();
        }

        return estimatedSquareFeet;
    }
}
