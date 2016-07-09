package com.goeuro.entity;

/**
 * Created by phanindra on 7/9/16.
 */
public class GeoPosition {
    private double latitude,longitude;

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
    public GeoPosition(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public GeoPosition(){

    }

}
