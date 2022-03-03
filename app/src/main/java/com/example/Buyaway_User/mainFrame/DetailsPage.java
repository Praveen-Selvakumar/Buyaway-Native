package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Adapters.NewProductsAdapter;
import com.example.Buyaway_User.Adapters.OffersAdapter;
import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.TextWatcherLink;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPage extends AppCompatActivity {

    RecyclerView recyclerView ;
    OffersAdapter offersAdapter ;
    ArrayList<TotalModel> totalModelArrayList ;
    CommonRetroAPI_Interface api_interface ;
    RelativeLayout progress_profile_lt ;
    String page_type = " " ,get_id =" " , get_title = " " ;
    TextView product_alert,title_txt  ;
    Call<ArrayList<TotalModel>> call  ;
    ImageView product_alert_img , back_btn ;
    EditText search_ed_off ;
    Methods methods ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
       InitializeFields();

      // if (page_type.equals("Category") || page_type.equals("Brand"))
            progress_profile_lt.setVisibility(View.VISIBLE);
            if (page_type.equals("new_arrivals")){
                load_new_arrivals();
            }else {
                load_Categories(get_id);
            }
              methods.TextWatcher(search_ed_off, new TextWatcherLink() {
                  @Override
                  public void Text_Watcher(String s) {
                      filter(s.toString());
                  }
              });


      }

    private void filter(String text) {
        ArrayList<TotalModel> filteredList = new ArrayList<>();

        for (TotalModel item : totalModelArrayList) {
            if (item.getBrand_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

       offersAdapter.filterList(filteredList);
    }



    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    private  void  load_new_arrivals(){
             api_interface = RetrofitAPIClient.getClient("APIMobileNewProducts/").create(CommonRetroAPI_Interface.class);
            Call<ArrayList<NewProductModel>> call = api_interface.getAll_product();
            call.enqueue(new Callback<ArrayList<NewProductModel>>() {
                @Override
                public void onResponse(Call<ArrayList<NewProductModel>> call, Response<ArrayList<NewProductModel>> response) {
                    ArrayList<NewProductModel> arrayList = response.body();

                    for (int i=0; i  < arrayList.size(); i++){
                        TotalModel model = new TotalModel();
                        model.setBrand_id(arrayList.get(i).getBrand_id());
                        model.setTitle(arrayList.get(i).getTitle());
                        model.setDescription(arrayList.get(i).getDescription());
                        model.setProduct_image(arrayList.get(i).getProduct_image());
                        model.setPrice(arrayList.get(i).getPrice());
                        totalModelArrayList.add(model);
                    }
                    offersAdapter = new OffersAdapter(totalModelArrayList,DetailsPage.this,"new_arrivals");
                    recyclerView.setAdapter(offersAdapter);
                    progress_profile_lt.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<NewProductModel>> call, Throwable t) {

                }
            });

    }

    private void load_Categories(String id) {
        api_interface = RetrofitAPIClient.getClient("APIMobileStoreList/").create(CommonRetroAPI_Interface.class);
        if (page_type.equals("Category"))
        {
            call = api_interface.Filter_Category_List(id);
        }else {
            call = api_interface.Filter_brand_List(id);
        }
             call.enqueue(new Callback<ArrayList<TotalModel>>() {
                @Override
                public void onResponse(Call<ArrayList<TotalModel>> call, Response<ArrayList<TotalModel>> response) {
                    if (response.isSuccessful()){
                        ArrayList<TotalModel> arrayList = response.body();
                        if (arrayList.isEmpty()){
                            load_alert();
                         }
                        for (int i=0;  i< arrayList.size(); i++){
                            TotalModel model = new TotalModel() ;
                            model.setBrand_id(arrayList.get(i).getBrand_id());
                            model.setBrand_name(arrayList.get(i).getBrand_name());
                            model.setCategory_id(arrayList.get(i).getCategory_id());
                            model.setTitle(arrayList.get(i).getTitle());
                            model.setProduct_image(arrayList.get(i).getProduct_image());
                            model.setArrayList(arrayList.get(i).getArrayList());
                            totalModelArrayList.add(model);
                        }
                        offersAdapter =   new OffersAdapter(totalModelArrayList,DetailsPage.this,"offers");
                        recyclerView.setAdapter(offersAdapter);
                        progress_profile_lt.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<TotalModel>> call, Throwable t) {

                }
            });
        }



    private void InitializeFields() {
        recyclerView  = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        totalModelArrayList = new ArrayList<TotalModel>();
        progress_profile_lt = (RelativeLayout)findViewById(R.id.progress_layout);
        page_type = getIntent().getStringExtra("page_type");
        get_id = getIntent().getStringExtra("id");
        get_title = getIntent().getStringExtra("title");
        product_alert = (TextView)findViewById(R.id.product_alert);
        title_txt = (TextView)findViewById(R.id.product_name);
        product_alert_img = (ImageView)findViewById(R.id.product_alert_img);
        back_btn = (ImageView)findViewById(R.id.back_btn);
        title_txt.setText(get_title);
        search_ed_off = (EditText)findViewById(R.id.search_ed_off);
        methods = new Methods(DetailsPage.this);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsPage.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

     private void load_alert(){
        product_alert.setVisibility(View.VISIBLE);
        product_alert_img.setVisibility(View.VISIBLE);
     }

}