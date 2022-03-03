package com.example.Buyaway_User.Model;

public class StoreListModel {

    String id, store_name, store_lat_lng, offer_hint;
    int per;
    private  boolean expandable ;

    public StoreListModel(String id, String store_name, String store_lat_lng, String offer_hint, int per) {
        this.id = id;
        this.store_name = store_name;
        this.store_lat_lng = store_lat_lng;
        this.offer_hint = offer_hint;
        this.per = per;
        expandable =  false;
    }

    public StoreListModel() {

    }


    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }



    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_lat_lng() {
        return store_lat_lng;
    }

    public void setStore_lat_lng(String store_lat_lng) {
        this.store_lat_lng = store_lat_lng;
    }

    public String getOffer_hint() {
        return offer_hint;
    }

    public void setOffer_hint(String offer_hint) {
        this.offer_hint = offer_hint;
    }
}
