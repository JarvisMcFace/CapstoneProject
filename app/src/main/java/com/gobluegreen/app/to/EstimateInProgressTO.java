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
    private Set<UpholsteryType> upholsterySet;
    private List<RoomTO> roomTOs;
    private CustomerTO customerTO;
    private List<RoomType> roomTypes;

    public Set<ServiceType> getServiceTypeSet() {
        ServiceType new3 = ServiceType.CARPET;

        new3.ordinal();
        return serviceTypeSet;
    }

    public void setServiceTypeSet(Set<ServiceType> serviceTypeSet) {
        this.serviceTypeSet = serviceTypeSet;
    }

    public Set<UpholsteryType> getUpholsterySet() {
        return upholsterySet;
    }

    public void setUpholsterySet(Set<UpholsteryType> upholsterySet) {
        this.upholsterySet = upholsterySet;
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

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }
}
