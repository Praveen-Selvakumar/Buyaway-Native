package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class BannerModel {


     @SerializedName("id") int id;
      @SerializedName("name") String name;
      @SerializedName("logo") String logo;
      @SerializedName("added_by") String added_by;
      @SerializedName("added_date") String added_date;
      @SerializedName("updated_date") String updated_date;
      @SerializedName("updated_by") String updated_by;
      @SerializedName("type") String type;
      @SerializedName("prebook_id") String prebook_id;

    public BannerModel(int id, String name, String logo, String added_by, String added_date, String updated_date, String updated_by, String type, String prebook_id) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.added_by = added_by;
        this.added_date = added_date;
        this.updated_date = updated_date;
        this.updated_by = updated_by;
        this.type = type;
        this.prebook_id = prebook_id;
    }

    public BannerModel( ) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrebook_id() {
        return prebook_id;
    }

    public void setPrebook_id(String prebook_id) {
        this.prebook_id = prebook_id;
    }
}
