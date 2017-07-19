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
    private boolean moveFurniture;
    private boolean carpetProtector;
    private int squareFeet;
    private String priceEstimate;

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

    public boolean isMoveFurniture() {
        return moveFurniture;
    }

    public void setMoveFurniture(boolean moveFurniture) {
        this.moveFurniture = moveFurniture;
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

    public String getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(String priceEstimate) {
        this.priceEstimate = priceEstimate;
    }
}
