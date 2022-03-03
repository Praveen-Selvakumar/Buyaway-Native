package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.Buyaway_User.Adapters.OffersAdapter;
import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.TextWatcherLink;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersPage extends AppCompatActivity   {

    RecyclerView  recyclerView;
     Methods methods;
    public static Handler handler;
     EditText search_ed;
    OffersAdapter adapter;
    CommonRetroAPI_Interface api_interface  ;
     ArrayList<TotalModel> arrayList_overall;
     RelativeLayout progress_profile_lt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_page);
        ButterKnife.bind(OffersPage.this);
        arrayList_overall = new ArrayList<>();
        InitializeFields();
       progress_profile_lt.setVisibility(View.VISIBLE);
        load_offers();


         methods.TextWatcher(search_ed, new TextWatcherLink() {
            @Override
            public void Text_Watcher(String s) {
                String get_String = s.toString();
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    if (!get_String.isEmpty() && get_String != null)
                        filter(s.toString());
                } else {
                    // In portrait
                    if (!get_String.isEmpty() && get_String != null)
                        filter(s.toString());
                }
            }
        });



       BottomNavigationView btm_nav = (BottomNavigationView)findViewById(R.id.btm_nav);
       new Methods(this).btm_nav(btm_nav,OffersPage.this);
       btm_nav.getMenu().findItem(R.id.btm_offers).setChecked(true);

      }


    private void filter(String text) {
        ArrayList<TotalModel> filteredList = new ArrayList<>();

        for (TotalModel item : arrayList_overall) {
            if (item.getBrand_name().toLowerCase().contains(text.toLowerCase())) {
                 filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext()  ,  HomePage.class));
    }

    private  void load_offers(){
        api_interface = RetrofitAPIClient.getClient("APIMobileStoreList/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<TotalModel>> call = api_interface.get_overall_List("nothing");
        call.enqueue(new Callback<ArrayList<TotalModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TotalModel>> call, Response<ArrayList<TotalModel>> response) {
              if (response.isSuccessful()){
                  ArrayList<TotalModel> arrayList = response.body();
                  for (int i=0;  i< arrayList.size(); i++){
                      TotalModel model = new TotalModel() ;
                      model.setBrand_id(arrayList.get(i).getBrand_id());
                      model.setBrand_name(arrayList.get(i).getBrand_name());
                      model.setCategory_id(arrayList.get(i).getCategory_id());
                      model.setTitle(arrayList.get(i).getTitle());
                      model.setProduct_image(arrayList.get(i).getProduct_image());
                      model.setArrayList(arrayList.get(i).getArrayList());
                      arrayList_overall.add(model);
                  }
                  adapter =   new OffersAdapter(arrayList,OffersPage.this,"offers");
                  recyclerView.setAdapter(adapter);
                  progress_profile_lt.setVisibility(View.GONE);
              }

             }

            @Override
            public void onFailure(Call<ArrayList<TotalModel>> call, Throwable t) {

            }
        });
     }



    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void InitializeFields() {
        progress_profile_lt = (RelativeLayout)findViewById(R.id.progress_profile_lt);
        handler = new  Handler(Looper.getMainLooper());
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        search_ed = (EditText)findViewById(R.id.search_ed_off);
         recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        methods = new Methods(this);

    //   methods.product_list_array(arrayList);
      //   recyclerView.setAdapter(new ProductListAdapter(OffersPage.this,arrayList));
      //  adapter = new ProductListAdapter(OffersPage.this,arrayList,recyclerView);
      }
}