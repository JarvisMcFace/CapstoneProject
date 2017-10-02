package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 10/1/17.
 */

public class LocationTO implements Serializable {

    private static final long serialVersionUID = -5048765127715975221L;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
