package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Adapters.Category_Horizontal_Adapter;
import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.TextWatcherLink;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPage extends AppCompatActivity {

       RecyclerView recyclerView_mf,recyclerView_wf ;
       ArrayList <CatModelList> arrayList_mf,arrayList_wf;
       Methods methods;
       EditText search_ed ;
       CommonRetroAPI_Interface api_interface;
       Category_Horizontal_Adapter adapter_mf, adapter_wf ;
       TextView mf_alert , wf_alert ;
       RelativeLayout progress_layout ;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        ButterKnife.bind(SearchPage.this);
         Intializifields();
         progress_layout.setVisibility(View.VISIBLE);
         load_Categories ();


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
         new Methods(this).btm_nav(btm_nav,SearchPage.this);
         btm_nav.getMenu().findItem(R.id.btm_search).setChecked(true);
    }

      private void filter(String text ) {
        ArrayList<CatModelList> filteredList = new ArrayList<>();
        ArrayList<CatModelList> filteredList_ = new ArrayList<>();

        for (CatModelList item :  arrayList_wf) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        for (CatModelList item :  arrayList_mf){
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList_.add(item);
            }
        }

         adapter_wf.filterList_wf(filteredList,wf_alert);
         adapter_mf.filterList_wf(filteredList_,mf_alert);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext()  ,  HomePage.class));
     }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public  void   load_Categories (){
        api_interface  = RetrofitAPIClient.getClient("APIMobileCategory/").create(CommonRetroAPI_Interface.class);
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
                        if ( item_type.equals("MAN") ){
                            arrayList_mf.add(model);
                        }else  {
                            arrayList_wf.add(model);
                        }
                    }
                    adapter_mf = new Category_Horizontal_Adapter(arrayList_mf ,SearchPage.this,"MAN");
                    recyclerView_mf.setAdapter(adapter_mf);

                    adapter_wf = new Category_Horizontal_Adapter(arrayList_wf ,SearchPage.this,"WOMEN");
                    recyclerView_wf.setAdapter(adapter_wf);

                 }else {
                    Toast.makeText(getApplicationContext(),"Something gone wrong try again later .. ", Toast.LENGTH_SHORT).show();
                }
                progress_layout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<CatModelList>> call, Throwable t) {

            }
        });

    }

    private void  Intializifields() {
          methods = new Methods(SearchPage.this);
        search_ed = (EditText) findViewById(R.id.search_ed);
        arrayList_wf = new ArrayList<>();
        arrayList_mf = new ArrayList<>();
        mf_alert = (TextView)findViewById(R.id.mf_alert);
        wf_alert = (TextView)findViewById(R.id.wf_alert);
        progress_layout = (RelativeLayout)findViewById(R.id.progress_layout);

        //for mens fashion
         recyclerView_mf = (RecyclerView)findViewById(R.id.recyclerView_mf);
         recyclerView_mf.setLayoutManager(new GridLayoutManager(this,4));
         recyclerView_wf = (RecyclerView)findViewById(R.id.recyclerView_wf);

        recyclerView_wf.setLayoutManager(new GridLayoutManager(this,4));


    }


}