package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TotalModel {

    //TotalProducts List
    public String category_id , brand_id, brand_name ,title, product_image,description,price ;
    boolean expanded;


    public TotalModel() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
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


    @SerializedName("StoreandStock")
    ArrayList<StoreandStock> arrayList = new ArrayList<StoreandStock>();

    public ArrayList<StoreandStock> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<StoreandStock> arrayList) {
        this.arrayList = arrayList;
    }

    public class StoreandStock{
        @SerializedName("id") int id;
        @SerializedName("stock_id") String stock_id;
        @SerializedName("store_id") String store_id;
        @SerializedName("store_name") String store_name;
        @SerializedName("availability") String availability ;
        @SerializedName("status") String status;
        @SerializedName("added_by") String added_by;
        @SerializedName("added_date") String added_date;
        @SerializedName("updated_date") String updated_date ;
        @SerializedName("offer_tag") String offer_tag ;
        @SerializedName("latitude") String latitude ;
        @SerializedName("longitude") String longitude ;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStock_id() {
            return stock_id;
        }

        public void setStock_id(String stock_id) {
            this.stock_id = stock_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getUpdated_date() {
            return updated_date;
        }

        public void setUpdated_date(String updated_date) {
            this.updated_date = updated_date;
        }

        public String getOffer_tag() {
            return offer_tag;
        }

        public void setOffer_tag(String offer_tag) {
            this.offer_tag = offer_tag;
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
    }

}
