package com.snapgroup.Classes;

/**
 * Created by snapgroup2 on 29/08/17.
 */

public class HoursService {
    String startHour,endHour,lat,lon,radius,date;

    public HoursService(String startHour, String endHour, String lat, String lon, String radius, String date) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.date = date;
    }

    public HoursService() {
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
