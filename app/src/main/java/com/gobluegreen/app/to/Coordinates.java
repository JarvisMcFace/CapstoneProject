package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 10/4/17.
 */

public class Coordinates implements Serializable{

    private static final long serialVersionUID = -4689214973726935813L;

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
