package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 06/08/17.
 */

public class NewGroup {
    private String title,description,image,origin,destination,galleryImage,startDate,endDate;
    private boolean inappPrivacy,searchPrivacy,seniors,disabled,children,pleasure,religion
            ,business,medical,seminar,hebrew,english,french,russian;

    public NewGroup(){

    }
    public NewGroup(String title, String description, String image, String origin
            , String destination, String galleryImage, String startDate, String endDate
            , boolean inappPrivacy, boolean searchPrivacy, boolean seniors, boolean disabled,
             boolean children, boolean pleasure, boolean religion, boolean business, boolean medical
            , boolean seminar, boolean hebrew, boolean english, boolean french, boolean russian) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.origin = origin;
        this.destination = destination;
        this.galleryImage = galleryImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inappPrivacy = inappPrivacy;
        this.searchPrivacy = searchPrivacy;
        this.seniors = seniors;
        this.disabled = disabled;
        this.children = children;
        this.pleasure = pleasure;
        this.religion = religion;
        this.business = business;
        this.medical = medical;
        this.seminar = seminar;
        this.hebrew = hebrew;
        this.english = english;
        this.french = french;
        this.russian = russian;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(String galleryImage) {
        this.galleryImage = galleryImage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isInappPrivacy() {
        return inappPrivacy;
    }

    public void setInappPrivacy(boolean inappPrivacy) {
        this.inappPrivacy = inappPrivacy;
    }

    public boolean isSearchPrivacy() {
        return searchPrivacy;
    }

    public void setSearchPrivacy(boolean searchPrivacy) {
        this.searchPrivacy = searchPrivacy;
    }

    public boolean isSeniors() {
        return seniors;
    }

    public void setSeniors(boolean seniors) {
        this.seniors = seniors;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public boolean isPleasure() {
        return pleasure;
    }

    public void setPleasure(boolean pleasure) {
        this.pleasure = pleasure;
    }

    public boolean isReligion() {
        return religion;
    }

    public void setReligion(boolean religion) {
        this.religion = religion;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public boolean isMedical() {
        return medical;
    }

    public void setMedical(boolean medical) {
        this.medical = medical;
    }

    public boolean isSeminar() {
        return seminar;
    }

    public void setSeminar(boolean seminar) {
        this.seminar = seminar;
    }

    public boolean isHebrew() {
        return hebrew;
    }

    public void setHebrew(boolean hebrew) {
        this.hebrew = hebrew;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isFrench() {
        return french;
    }

    public void setFrench(boolean french) {
        this.french = french;
    }

    public boolean isRussian() {
        return russian;
    }

    public void setRussian(boolean russian) {
        this.russian = russian;
    }
}
