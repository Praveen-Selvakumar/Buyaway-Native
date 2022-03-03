package com.example.Buyaway_User.DOA;

import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public interface ConnectLink {



    public void onCallResponded(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response, Throwable t);

    public void onCategoryCallResponded(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response, Throwable t);
}
