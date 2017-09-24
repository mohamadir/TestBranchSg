package com.snapgroup.Classes;

/**
 * Created by root on 15/08/17.
 */

public class HotelServiceLocation
{
    public String location;
    public String date;


    public HotelServiceLocation(String location, String date) {
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Day number == " + this.date + " location ===   " + this.location;
    }
}
