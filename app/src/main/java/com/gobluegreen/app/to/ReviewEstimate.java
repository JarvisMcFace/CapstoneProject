package com.gobluegreen.app.to;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by David on 7/5/17.
 */
public class ReviewEstimate implements Serializable{

    private static final long serialVersionUID = -993644511096944070L;

    private Set<ServiceType> serviceTypeSet;
    private CustomerTO customerTO;
    private String  numberOfRooms;
    private String priceEstimatesRange;
    private String estimatedSqFt;

    public Set<ServiceType> getServiceTypeSet() {
        return serviceTypeSet;
    }

    public void setServiceTypeSet(Set<ServiceType> serviceTypeSet) {
        this.serviceTypeSet = serviceTypeSet;
    }

    public CustomerTO getCustomerTO() {
        return customerTO;
    }

    public void setCustomerTO(CustomerTO customerTO) {
        this.customerTO = customerTO;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getPriceEstimatesRange() {
        return priceEstimatesRange;
    }

    public void setPriceEstimatesRange(String priceEstimatesRange) {
        this.priceEstimatesRange = priceEstimatesRange;
    }

    public String getEstimatedSqFt() {
        return estimatedSqFt;
    }

    public void setEstimatedSqFt(String estimatedSqFt) {
        this.estimatedSqFt = estimatedSqFt;
    }
}
