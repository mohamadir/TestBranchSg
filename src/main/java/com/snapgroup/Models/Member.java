package com.snapgroup.Models;

/**
 * Created by root on 27/07/17.
 */

public class Member {
    String first_name ;
    String last_name;
    String ImgId;

    public Member(String first_name, String last_name, String imgId)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        ImgId = imgId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImgId() {
        return ImgId;
    }

    public void setImgId(String imgId) {
        ImgId = imgId;
    }
}
