package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 27/07/17.
 */

public class GroupPlan {
    private String dayNumber,description,breakfast,lunch,dinner,date;

    public GroupPlan(String dayNumber, String description, String breakfast, String lunch, String dinner, String date) {

        this.dayNumber = dayNumber;
        this.description = description;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.date = date;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayNumber() {

        return dayNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public String getDate() {
        return date;
    }


}
