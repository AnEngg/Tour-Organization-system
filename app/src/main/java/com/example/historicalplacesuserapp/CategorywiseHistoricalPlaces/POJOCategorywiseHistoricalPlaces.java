package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

public class POJOCategorywiseHistoricalPlaces {

    String id,image,name,category,address,latitude,longitude,features,description,video_url,parking_available,
    charges,special_rules,city,account_no,ifsc_code,branch_code;

    public POJOCategorywiseHistoricalPlaces(String id, String image, String name, String category, String address,
                                            String latitude, String longitude, String features, String description,
                                            String video_url, String parking_available, String charges,
                                            String special_rules, String city, String account_no, String ifsc_code,
                                            String branch_code) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.features = features;
        this.description = description;
        this.video_url = video_url;
        this.parking_available = parking_available;
        this.charges = charges;
        this.special_rules = special_rules;
        this.city = city;
        this.account_no = account_no;
        this.ifsc_code = ifsc_code;
        this.branch_code = branch_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getParking_available() {
        return parking_available;
    }

    public void setParking_available(String parking_available) {
        this.parking_available = parking_available;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getSpecial_rules() {
        return special_rules;
    }

    public void setSpecial_rules(String special_rules) {
        this.special_rules = special_rules;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }
}
