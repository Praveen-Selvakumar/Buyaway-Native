package com.example.Buyaway_User.Common;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//*SingleTon Pattern*/
public class RetrofitAPIClient {

    private static  Retrofit retrofit =  null;

    public static  Retrofit getClient(String url){
        // show.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);

        //NSA191463
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://webbuyaway.buyaway.in/api/"+url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }



}
