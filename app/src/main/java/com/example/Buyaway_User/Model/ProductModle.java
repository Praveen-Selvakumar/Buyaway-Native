package com.example.Buyaway_User.Model;

public class ProductModle {

    String carry_name ;
    String product_name;

    public ProductModle(String carry_name) {
        this.carry_name = carry_name;
    }

    public ProductModle() {
    }


    public String getCarry_name() {
        return carry_name;
    }

    public void setCarry_name(String carry_name) {
        this.carry_name = carry_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
