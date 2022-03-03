package com.example.Buyaway_User.mainFrame;

import androidx.lifecycle.ViewModel;

import com.example.Buyaway_User.Model.ProductModle;

public class MyViewModel extends ViewModel {

    String name ;

    public MyViewModel(String name) {
        this.name = name;
        new ProductModle(name);
    }

    public String get_name(){
        String name_ = new ProductModle().getCarry_name();
        return name_;
    }




}
