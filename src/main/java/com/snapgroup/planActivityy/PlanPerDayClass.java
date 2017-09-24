package com.snapgroup.planActivityy;

/**
 * Created by root on 10/09/17.
 */

public class PlanPerDayClass {
    public String breakfast,description,dinner,lunch,day_number,date;
    public int group_id,hotel_id;

    public PlanPerDayClass(String breakfast, String description, String dinner, String lunch, String day_number, String date) {
        this.breakfast = breakfast;
        this.description = description;
        this.dinner = dinner;
        this.lunch = lunch;
        this.day_number = day_number;
        this.date = date;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDay_number() {
        return day_number;
    }

    public void setDay_number(String day_number) {
        this.day_number = day_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}
