package com.snapgroup.Classes;

/**
 * Created by root on 18/09/17.
 */

public class JoinPersonInformation
{
    String firstname,lastname,age,gender,passport_id,dateBirthday,exp_date,issue_date;

    public JoinPersonInformation(String firstname, String lastname, String age, String gender, String passport_id, String dateBirthday, String exp_date, String issue_date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.passport_id = passport_id;
        this.dateBirthday = dateBirthday;
        this.exp_date = exp_date;
        this.issue_date = issue_date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassport_id() {
        return passport_id;
    }

    public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
    }

    public String getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(String dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }
}
