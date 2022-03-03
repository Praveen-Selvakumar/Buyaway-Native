package com.example.Buyaway_User.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {


 /*
 * {
    "image_status": "Success",
    "image_message": "Profile Image Successfully uploaded",
    "status": "Success",
    "message": "Successfully Updated"
}
 * */

    @SerializedName("contact_number")public String contact_number;
    @SerializedName("email")public String email;
    @SerializedName("name")public String name;
    @SerializedName("gender")public String gender;
    @SerializedName("password")public String password;
    @SerializedName("date_of_birth")public String date_of_birth;
    @SerializedName("Address")public String Address;
    @SerializedName("profile_image")public String profile_image;

    //Edit profile
    @SerializedName("image_status")String image_status ;
    @SerializedName("image_message")String image_message ;


    @SerializedName("status")public String status;
    @SerializedName("message")public String message;
    @SerializedName("user_check")public String user_check;


    public UserModel(String contact_number, String email, String name, String gender, String password) {
        this.contact_number = contact_number;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.password = password;
    }

    public UserModel() {
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public String getImage_message() {
        return image_message;
    }

    public void setImage_message(String image_message) {
        this.image_message = image_message;
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

    public String getUser_check() {
        return user_check;
    }

    public void setUser_check(String user_check) {
        this.user_check = user_check;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
