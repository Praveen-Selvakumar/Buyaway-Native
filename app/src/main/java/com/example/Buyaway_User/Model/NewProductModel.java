package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class NewProductModel {

    int id ;

     String brand_id, title,  price, product_image, added_by, added_date, description, logo, name,
             details , tag_line     ;


    public NewProductModel(int id, String brand_id, String title, String price, String product_image, String added_by, String added_date, String description) {
        this.id = id;
        this.brand_id = brand_id;
        this.title = title;
        this.price = price;
        this.product_image = product_image;
        this.added_by = added_by;
        this.added_date = added_date;
        this.description = description;
    }

    public NewProductModel(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTag_line() {
        return tag_line;
    }

    public void setTag_line(String tag_line) {
        this.tag_line = tag_line;
    }

    public NewProductModel() {
     }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
