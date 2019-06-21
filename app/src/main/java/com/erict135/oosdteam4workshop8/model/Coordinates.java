package com.erict135.oosdteam4workshop8.model;

public class Coordinates {

    private double Latitude;
    private double Longitude;

    public Coordinates(){

    }

    public Coordinates(double lat,double lon){
        Latitude=lat;
        Longitude=lon;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

}