package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 27/07/17.
 */

public class GroupInList {
    private int _id,grpuMax;
    private String title,descritption,image,origin,destination;
    String start_date,end_date;


    public int getGrpuMax() {
        return grpuMax;
    }

    public void setGrpuMax(int grpuMax) {
        this.grpuMax = grpuMax;
    }

    public GroupInList(int _id, String title, String descritption, String image, String origin, String destination, String start_date, String end_date, int grpuMax) {
        this._id = _id;
        this.title = title;
        this.descritption = descritption;
        this.image = image;
        this.origin = origin;
        this.destination = destination;
        this.start_date = start_date;
        this.end_date = end_date;
        this.grpuMax = grpuMax;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescritption() {
        return descritption;
    }

    public String getImage() {
        return image;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
