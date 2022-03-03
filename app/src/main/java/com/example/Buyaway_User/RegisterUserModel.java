package com.example.Buyaway_User;

public class RegisterUserModel {

    public String name,mail,phone_number,gender,pwd,cf_pwd,date_of_birth,Address,profile_image;



    public RegisterUserModel(String name, String mail, String phone_number, String gender,  String date_of_birth, String address,String profile_image) {
        this.name = name;
        this.mail = mail;
        this.phone_number = phone_number;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.Address = address;
        this.profile_image = profile_image;
    }

    public RegisterUserModel(String phone_number) {
        this.phone_number = phone_number;
    }

    public RegisterUserModel( ) {
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCf_pwd() {
        return cf_pwd;
    }

    public void setCf_pwd(String cf_pwd) {
        this.cf_pwd = cf_pwd;
    }
}
