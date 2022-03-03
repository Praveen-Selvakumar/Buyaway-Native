package com.example.Buyaway_User.Model;

import java.util.ArrayList;

public class ProductListModel {


    String id,  product_name,  product_des,  stores_name, stores_lat_lng;

    int store_img;
    boolean expanded;


    public ProductListModel(String id, String product_name, String product_des, String stores_name,
                            String stores_lat_lng ,int store_img) {
        this.id = id;
        this.product_name = product_name;
        this.product_des = product_des;
        this.stores_name = stores_name;
        this.stores_lat_lng = stores_lat_lng;
        this.store_img = store_img;
        this.expanded =  false;
    }

    public ProductListModel() {
    }


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getStore_img() {
        return store_img;
    }

    public void setStore_img(int store_img) {
        this.store_img = store_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_des() {
        return product_des;
    }

    public void setProduct_des(String product_des) {
        this.product_des = product_des;
    }

    public String getStores_name() {
        return stores_name;
    }

    public void setStores_name(String stores_name) {
        this.stores_name = stores_name;
    }

    public String getStores_lat_lng() {
        return stores_lat_lng;
    }

    public void setStores_lat_lng(String stores_lat_lng) {
        this.stores_lat_lng = stores_lat_lng;
    }
}
