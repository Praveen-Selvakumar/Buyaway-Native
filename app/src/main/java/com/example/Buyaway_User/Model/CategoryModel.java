package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    String id ,cat_name ;
     int cat_img;

    @SerializedName("id") int ID;
    @SerializedName("name")String name;
    @SerializedName("item_type")String item_type;
    @SerializedName("icon")String icon;
    @SerializedName("added_by")String added_by;
    @SerializedName("added_date")String added_date;


    public CategoryModel(int ID, String name, String item_type, String icon, String added_by, String added_date) {
        this.ID = ID;
        this.name = name;
        this.item_type = item_type;
        this.icon = icon;
        this.added_by = added_by;
        this.added_date = added_date;
    }

    public CategoryModel(String id, int cat_img, String cat_name) {
        this.id = id;
        this.cat_img = cat_img;
        this.cat_name = cat_name;
    }

    public CategoryModel() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCat_img() {
        return cat_img;
    }

    public void setCat_img(int cat_img) {
        this.cat_img = cat_img;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
