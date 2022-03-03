package com.example.Buyaway_User.DOA;

import com.example.Buyaway_User.Model.ResponseModel;
import com.example.Buyaway_User.RegisterUserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrationAPI_Interface {


   /*@FormUrlEncoded
    @POST("5")
    Call<ResponseModel> postRegistrationdata(@Body("name") String name,
            @Body("email") String email,
            @Body("contact_number") String contact_number ,
            @Body("gender") String gender,
            @Body("password") String password);*/

    @Headers("Content-Type: application/json")
    @POST("UserRegisteration")
    Call<ResponseModel> getUser(@Body String body);
}
