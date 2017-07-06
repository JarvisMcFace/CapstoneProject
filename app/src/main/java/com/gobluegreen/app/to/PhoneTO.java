package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 7/6/17.
 */

public class PhoneTO implements Serializable {

    private static final long serialVersionUID = 3163959526113701587L;

    private PhoneType phoneType;
    private String phoneNumber;

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
