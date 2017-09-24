package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 26/07/17.
 */

public class UserProfile {
    private int memberID;
    private String firstName,lastName;
    private String imageUrl;

    public UserProfile(int memberID, String firstName, String lastName, String imageUrl) {
        this.memberID = memberID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
