package com.snapgroup.Classes;

/**
 * Created by snapgroup2 on 10/08/17.
 */

public class MemberInviteItem {
   public  String image,firstName,lastName,id;
    public MemberInviteItem(String image, String firstName, String lastName,String id) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id=id;
    }
    public String toString(){
        return image+","+firstName+","+lastName+","+id;
    }
}
