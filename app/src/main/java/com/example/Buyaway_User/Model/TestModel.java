package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class TestModel {
    //TotalProducts List
    @SerializedName("category_id") String category_id;
    @SerializedName("brand_id") String brand_id;
    @SerializedName("brand_name") String brand_name;
    @SerializedName("title") String title;
    @SerializedName("product_image") String product_image;
    @SerializedName("brand_item_id") String brand_item_id;
     boolean expanded;

    public TestModel() {
    }

    public String getBrand_item_id() {
        return brand_item_id;
    }

    public void setBrand_item_id(String brand_item_id) {
        this.brand_item_id = brand_item_id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
