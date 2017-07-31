package com.gobluegreen.app.to;

import java.io.Serializable;

/**
 * Created by David on 7/23/17.
 */

public class CleaningPriceFactorTO implements Serializable{

    private static final long serialVersionUID = -3243306504364074940L;

    private RoomType roomType;
    private double squareFeetFactor;
    private double pricePerSquareFeet;  // $.5
    private double moveFurnitureFactor;    //no value
    private double carpetProtectorFactor;   //sq *.2
    private double pricePerStep;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getSquareFeetFactor() {
        return squareFeetFactor;
    }

    public void setSquareFeetFactor(double squareFeetFactor) {
        this.squareFeetFactor = squareFeetFactor;
    }

    public double getPricePerSquareFeet() {
        return pricePerSquareFeet;
    }

    public void setPricePerSquareFeet(double pricePerSquareFeet) {
        this.pricePerSquareFeet = pricePerSquareFeet;
    }

    public double getMoveFurnitureFactor() {
        return moveFurnitureFactor;
    }

    public void setMoveFurnitureFactor(double moveFurnitureFactor) {
        this.moveFurnitureFactor = moveFurnitureFactor;
    }

    public double getCarpetProtectorFactor() {
        return carpetProtectorFactor;
    }

    public void setCarpetProtectorFactor(double carpetProtectorFactor) {
        this.carpetProtectorFactor = carpetProtectorFactor;
    }

    public double getPricePerStep() {
        return pricePerStep;
    }

    public void setPricePerStep(double pricePerStep) {
        this.pricePerStep = pricePerStep;
    }
}
