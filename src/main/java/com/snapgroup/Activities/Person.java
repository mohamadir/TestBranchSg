package com.snapgroup.Activities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple container object for contact data
 *
 * Created by mgod on 9/12/13.
 * @author mgod
 */
public class Person implements Parcelable {
    private String name;
    private String email;
    public String id;

   public  Person(String n, String e,String id) {
        name = n;
        email = e;
       this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return name; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
    }

    public Person(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}