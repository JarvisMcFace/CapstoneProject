package com.gobluegreen.app.util;

import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.EstimateItemTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.to.RoomType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 7/14/17.
 */

public class PopulateEstimateItems {

    public static List<EstimateItemTO> execute(GoBluegreenApplication application) {

        List<EstimateItemTO> estimateItemTOs = new ArrayList<>();

        EstimateInProgressTO estimateInProgressTO = application.getEstimateInProgressTO();

        if (estimateInProgressTO == null) {
            return estimateItemTOs;
        }

        populateCarpetCleaningServices(estimateItemTOs, estimateInProgressTO);


        return estimateItemTOs;
    }

    private static List<EstimateItemTO> populateCarpetCleaningServices(List<EstimateItemTO> estimateItemTOs, EstimateInProgressTO estimateInProgressTO) {

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();
        if (ListUtils.isEmpty(roomTOs)) {
            return estimateItemTOs;
        }

        for (RoomTO roomTO : roomTOs) {
            EstimateItemTO estimateItemTO = new EstimateItemTO(EstimateItemTO.ItemType.ROOM);
            estimateItemTO.setItemObject(roomTO);

            estimateItemTOs.add(estimateItemTO);
        }

        return estimateItemTOs;
    }

    private static EstimateInProgressTO addRooms(EstimateInProgressTO estimateInProgressTO) {

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();

        List<RoomTO> roomTOs = estimateInProgressTO.getRoomTOs();

        if (ListUtils.isEmpty(roomTOs)) {
            roomTOs = new ArrayList<>();
        }

        for (RoomType roomType : roomTypes) {

            RoomTO roomTO = new RoomTO();
            roomTO.setRoomType(roomType);

            roomTOs.add(roomTO);
        }

        estimateInProgressTO.setRoomTOs(roomTOs);
        return estimateInProgressTO;
    }
}
