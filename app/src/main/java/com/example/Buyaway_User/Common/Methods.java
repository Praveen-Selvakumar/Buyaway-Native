package com.example.Buyaway_User.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Buyaway_User.Adapters.Category_Horizontal_Adapter;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.ConnectLink;
import com.example.Buyaway_User.DOA.TextWatcherLink;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.ProductListModel;
import com.example.Buyaway_User.Model.StoreListModel;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.login_pack.Login_Page;
import com.example.Buyaway_User.login_pack.ProfilePage;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.example.Buyaway_User.mainFrame.OffersPage;
import com.example.Buyaway_User.mainFrame.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Methods {

    Context context;
    Category_Horizontal_Adapter adapter ;
    ArrayList <CatModelList> arrayList_mf,arrayList_wf;
    UserLocalStore userLocalStore ;


    public Methods(Context context, Category_Horizontal_Adapter adapter) {
        this.context = context;
        this.adapter = adapter;
        arrayList_mf = new ArrayList<>();
        arrayList_wf = new ArrayList<>();
    }

    public Methods(Context context){
         this.context = context;
         userLocalStore = new UserLocalStore(context.getApplicationContext());
    }
    CommonRetroAPI_Interface api_interface;

    public void btm_nav(BottomNavigationView btm_nav, Context context){

        btm_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_home:
                        Intent intent = new Intent(context.getApplicationContext(), HomePage.class);
                        context.startActivity(intent);
                        break;

                    case R.id.btm_search:
                        Intent intent1 = new Intent(context.getApplicationContext(), SearchPage.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.btm_offers:
                        Intent intent2 = new Intent(context.getApplicationContext(), OffersPage.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.btm_login:
                        if (!userLocalStore.isUserLogged().getPhone_number().isEmpty()){
                            Intent intent4 = new Intent(context.getApplicationContext(), ProfilePage.class);
                            context.startActivity(intent4);
                        }else {
                       Intent intent3 = new Intent(context.getApplicationContext(), Login_Page.class);
                        context.startActivity(intent3);}
                        break;
                }
                return false;
            }
        });

     }


     public void product_list_array(ArrayList<ProductListModel> arrayList){
        // id,  product_name,  product_des,  stores_name, stores_lat_lng
        arrayList.add(new ProductListModel("1","LP","Flat 20% off on All products","","",
                R.drawable.o_one));
         arrayList.add(new ProductListModel("2","Levi's","Buy for 5999 get 1000 worth apparel",
                 "","",
                R.drawable.o_two));
         arrayList.add(new ProductListModel("3","Adidas","Flat 20% Off","","",
                 R.drawable.o_nine));
         arrayList.add(new ProductListModel("4","Puma","Buy fot 4999 get 750 worth merchandise","",
                 "",R.drawable.o_ten));
         arrayList.add(new ProductListModel("5","Allen Solly","Allen solly shop for 6999 get 1000 Off","",
                 "",R.drawable.po_five));
         arrayList.add(new ProductListModel("6","Jockey","Buy 3 get 20% Off","","",
                 R.drawable.po_six));
         arrayList.add(new ProductListModel("7","Jack&Jones","Buy 3 get  50% off","",
                 "",R.drawable.o_three));
         arrayList.add(new ProductListModel("8","NIKE","Buyaway Exclusive 20% off","","",
                 R.drawable.o_four));
         arrayList.add(new ProductListModel("9","Puma","Shop for 4999 get 750 worth mechandise","","",
                 R.drawable.o_ten));
         arrayList.add(new ProductListModel("10","SKECHERS","SALE Flat 20% off","",
                 "",R.drawable.po_eight));
         arrayList.add(new ProductListModel("11","ASICS","Get 10% off on all purchase","",
                 "",R.drawable.o_fifteen));
         arrayList.add(new ProductListModel("12","Levi's"," ","",
                 "",R.drawable.o_two));
         arrayList.add(new ProductListModel("13","Max","Fashion@499",
                 "","",R.drawable.o_six));
         arrayList.add(new ProductListModel("14","PETER ENGLAND","Buy 4999 get 25%off","",
                 "",R.drawable.o_twelve));
         arrayList.add(new ProductListModel("15","Lee/wrangler","Buy 2 get 1","",
                 "",R.drawable.o_thirteen));

    }
    public void store_list_array(ArrayList<StoreListModel> arrayList) {
        arrayList.add(new StoreListModel("1", "Indiranagar",   "12.9784-77.6408", "50% offer",50 ));
        arrayList.add(new StoreListModel("2", "Ejipura",   "12.9385-77.6308", " ",100 ));
        arrayList.add(new StoreListModel("3", "Madiwala",   "12.9226-77.6174", "75% offer",75 ));
     }

     public void loadAPI( Call<ArrayList<CategoryModel>> call,ConnectLink connectLink){
        call.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                connectLink.onCallResponded(call,response,null);
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                connectLink.onCallResponded(call,null,t);
            }
        });
      }

      public void  loadCategoryList( ArrayList<CatModelList>cat_arrayList ,  RecyclerView  recyclerView, String page_ref){
          api_interface = RetrofitAPIClient.getClient("APIMobileCategory/").create(CommonRetroAPI_Interface.class);
          Call<ArrayList<CatModelList>> call = api_interface.CategoryList("");
           call.enqueue(new Callback<ArrayList<CatModelList>>() {
              @Override
              public void onResponse(Call<ArrayList<CatModelList>> call, Response<ArrayList<CatModelList>> response) {
                  if (response.isSuccessful()) {
                      ArrayList<CatModelList> arrayList = response.body();
                      for (int i = 0; i < arrayList.size(); i++) {
                          CatModelList model = new CatModelList();
                          String item_type = arrayList.get(i).getItem_type().toString();
                          model.setID(arrayList.get(i).getID());
                          model.setName(arrayList.get(i).getName());
                          model.setIcon(arrayList.get(i).getIcon());
                          model.setItem_type(arrayList.get(i).getItem_type());
                          if (page_ref.equals("home")){
                              cat_arrayList.add(model);
                          }else if (item_type.equals(page_ref)){
                              cat_arrayList.add(model);
                          }
                      }
                      //adapter = new Category_Horizontal_Adapter(cat_arrayList, context.getApplicationContext(),page_ref);
                      //recyclerView.setAdapter(adapter);
                    //  recyclerView.setAdapter(new Category_Horizontal_Adapter(cat_arrayList, context.getApplicationContext(),page_ref));
                  }else {
                      Toast.makeText(context.getApplicationContext(),"Something gone wrong try again later .. ", Toast.LENGTH_SHORT).show();
                  }
               }

              @Override
              public void onFailure(Call<ArrayList<CatModelList>> call, Throwable t) {

              }
          });
      }


    private void filter(String text) {
        ArrayList<CatModelList> filteredList_man = new ArrayList<>();
        ArrayList<CatModelList> filteredList_women = new ArrayList<>();

        for (CatModelList item : arrayList_mf) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                filteredList_man.add(item);
                filteredList_women.add(item);

            }
        }
    }


    public void  TextWatcher(EditText ed, TextWatcherLink link){
         ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                   link.Text_Watcher(s.toString());
            }
        });
    }


}
