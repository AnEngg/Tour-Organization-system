package com.example.historicalplacesuserapp.ViewMyBooking;

public class POJOViewMyBooking {

    String id,amount,card_holder_name,package_title,package_description,package_features,packages_cost,date_time;

    public POJOViewMyBooking(String id, String amount, String card_holder_name, String package_title, String package_description, String package_features, String packages_cost, String date_time) {
        this.id = id;
        this.amount = amount;
        this.card_holder_name = card_holder_name;
        this.package_title = package_title;
        this.package_description = package_description;
        this.package_features = package_features;
        this.packages_cost = packages_cost;
        this.date_time = date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getPackage_title() {
        return package_title;
    }

    public void setPackage_title(String package_title) {
        this.package_title = package_title;
    }

    public String getPackage_description() {
        return package_description;
    }

    public void setPackage_description(String package_description) {
        this.package_description = package_description;
    }

    public String getPackage_features() {
        return package_features;
    }

    public void setPackage_features(String package_features) {
        this.package_features = package_features;
    }

    public String getPackages_cost() {
        return packages_cost;
    }

    public void setPackages_cost(String packages_cost) {
        this.packages_cost = packages_cost;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
