package com.example.Buyaway_User.DOA;

import com.example.Buyaway_User.Model.BannerModel;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.Model.TestModel;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.Model.UserModel;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface CommonRetroAPI_Interface {

    @GET("MobileBrand")
    Call<ArrayList<NewProductModel>> getAll_brands();

    @GET("NewProducts")
    Call<ArrayList<NewProductModel>> getAll_product();

    @GET("MobileBanner")
    Call<ArrayList<NewProductModel>> getBanners();

    @GET("MobileBanner")
    Call<ArrayList<BannerModel>> getBanners_test();



    @FormUrlEncoded
    @POST("UserRegistration")
    Call<UserModel>  register_user(@Field("contact_number") String   contact_number,
                                   @Field("email") String email,
                                   @Field("name") String name,
                                   @Field("gender") String gender,
                                   @Field("password") String password);


    @FormUrlEncoded
    @POST("UserLogin")
    Call<UserModel>  login_user(@Field("contact_number") String contact_number,
                                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("ForgotPassword")
    Call<UserModel>  forgot_password(@Field("contact_number") String contact_number);


@FormUrlEncoded
    @POST("ChangePassword")
    Call<UserModel>  ChangePassword(
            @Field("contact_number") String contact_number,
            @Field("password") String password,
            @Field("newpassword") String newpassword );


    @FormUrlEncoded
    @POST("CategoryList")
    Call<ArrayList<CatModelList>> CategoryList(@Field("contact_number") String contact_number);

    @FormUrlEncoded
    @POST("ViewProfile")
    Call<UserModel> get_profile_detail(@Field("contact_number") String contact_number);


    //,,,,,,
    @FormUrlEncoded
    @POST("EditProfileBase64")
    Call<UserModel> edit_profile(@Field("name") String name ,
                                 @Field("email") String email,
                                 @Field("contact_number") String contact_number,
                                 @Field("gender") String gender,
                                 @Field("address") String address,
                                 @Field("date_of_birth") String date_of_birth,
                                 @Field("profile_image") String profile_image
                                 );

//OFFERS PAGE LIST
    @FormUrlEncoded
    @POST("MobileStoreList")
    Call<ArrayList<TotalModel>> get_overall_List(@Field("nothing")String nothing);

  @FormUrlEncoded
    @POST("MobileStoreList")
    Call<ArrayList<TotalModel>> Filter_Category_List(@Field("category_id")String category_id);

  @FormUrlEncoded
    @POST("MobileStoreList")
    Call<ArrayList<TotalModel>> Filter_brand_List(@Field("brand_id")String brand_id);

 }
