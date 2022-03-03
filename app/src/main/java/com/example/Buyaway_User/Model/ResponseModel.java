package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    @SerializedName("user_exist")
    String user_exist;


    public ResponseModel(String status, String message, String user_exist) {
        this.status = status;
        this.message = message;
        this.user_exist = user_exist;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_exist() {
        return user_exist;
    }

    public void setUser_exist(String user_exist) {
        this.user_exist = user_exist;
    }
}
