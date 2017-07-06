package com.gobluegreen.app.to;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by David on 7/5/17.
 */
public class EstimateInProgressTO  implements Serializable{

    private static final long serialVersionUID = -993644511096944070L;

    private Set<ServiceType> serviceTypeSet;
    private List<UpholsteryTO> upholsteryTOs;
    private List<RoomTO> roomTOs;
    private CustomerTO customerTO;

    public Set<ServiceType> getServiceTypeSet() {
        return serviceTypeSet;
    }

    public void setServiceTypeSet(Set<ServiceType> serviceTypeSet) {
        this.serviceTypeSet = serviceTypeSet;
    }

    public List<RoomTO> getRoomTOs() {
        return roomTOs;
    }

    public void setRoomTOs(List<RoomTO> roomTOs) {
        this.roomTOs = roomTOs;
    }

    public CustomerTO getCustomerTO() {
        return customerTO;
    }

    public void setCustomerTO(CustomerTO customerTO) {
        this.customerTO = customerTO;
    }

    public List<UpholsteryTO> getUpholsteryTOs() {
        return upholsteryTOs;
    }

    public void setUpholsteryTOs(List<UpholsteryTO> upholsteryTOs) {
        this.upholsteryTOs = upholsteryTOs;
    }
}
