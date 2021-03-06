package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 7/6/17.
 */

public class RoomTO implements Serializable {

    private static final long serialVersionUID = 6131794314671804481L;

    private RoomType roomType;
    private int length;
    private int width;
    private boolean carpetProtector;
    private int squareFeet;
    private double priceEstimate;
    private int numberSteps;
    private int estimatedSquareFeet;
    private boolean dimensionByLengthWidth;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isCarpetProtector() {
        return carpetProtector;
    }

    public void setCarpetProtector(boolean carpetProtector) {
        this.carpetProtector = carpetProtector;
    }

    public int getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(int squareFeet) {
        this.squareFeet = squareFeet;
    }

    public double getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(double priceEstimate) {
        this.priceEstimate = priceEstimate;
    }

    public int getNumberSteps() {
        return numberSteps;
    }

    public void setNumberSteps(int numberSteps) {
        this.numberSteps = numberSteps;
    }

    public int getEstimatedSquareFeet() {
        return estimatedSquareFeet;
    }

    public void setEstimatedSquareFeet(int estimatedSquareFeet) {
        this.estimatedSquareFeet = estimatedSquareFeet;
    }

    public boolean isDimensionByLengthWidth() {
        return dimensionByLengthWidth;
    }

    public void setDimensionByLengthWidth(boolean dimensionByLengthWidth) {
        this.dimensionByLengthWidth = dimensionByLengthWidth;
    }
}

