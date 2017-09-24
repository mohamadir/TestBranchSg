package com.snapgroup.Classes;

/**
 * Created by snapgroup2 on 24/08/17.
 */

public class ActivitiesLocation {
   public  double lat,lon,radius;

    public ActivitiesLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
