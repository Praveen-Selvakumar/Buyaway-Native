package com.example.Buyaway_User.Common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.Buyaway_User.RegisterUserModel;

public class UserLocalStore {

    Context context;
    SharedPreferences sharedPreferences;
    String pref_name = "USER_LOCAL";

    public   UserLocalStore(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(pref_name,0);
    }

    public void  UserLogged(String number){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_number", number);
        editor.commit();
    }

    public RegisterUserModel  isUserLogged( ){
        String get_number = sharedPreferences.getString("user_number","");
        RegisterUserModel model = new RegisterUserModel(get_number);
        return model;
    }

    public void storeUserData(RegisterUserModel model){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_number", model.getPhone_number());
        editor.putString("user_name",model.getName());
        editor.putString("user_email",model.getMail());
        editor.putString("user_gender",model.getGender());
        editor.putString("user_dob",model.getDate_of_birth());
        editor.putString("user_address",model.getAddress());
        editor.putString("user_image",model.getProfile_image());
         editor.commit();
    }

     public RegisterUserModel getUserModel(){
        String get_number = sharedPreferences.getString("user_number","");
        String get_name = sharedPreferences.getString("user_name","");
        String get_email = sharedPreferences.getString("user_email","");
        String get_gender = sharedPreferences.getString("user_gender","");
        String get_dob = sharedPreferences.getString("user_dob","");
        String get_address = sharedPreferences.getString("user_address","");
        String get_image = sharedPreferences.getString("user_image","");
        RegisterUserModel model = new RegisterUserModel(get_number,get_name,get_email,get_gender,get_dob,get_address,get_image);
        return model;
    }



     public void delete_data(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.clear();
        editor.commit();
     }


}
