package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Adapters.Category_Horizontal_Adapter;
import com.example.Buyaway_User.Adapters.NewProductsAdapter;
import com.example.Buyaway_User.Adapters.OffersAdapter;
import com.example.Buyaway_User.Adapters.ProoularOffersAdapter;
import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.ConnectLink;
import com.example.Buyaway_User.DOA.TextWatcherLink;
import com.example.Buyaway_User.DOA.TextWatchers;
import com.example.Buyaway_User.MainActivity;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.Model.ProductModle;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.login_pack.Login_Page;
import com.example.Buyaway_User.Adapters.SlidingImage_Adapter;
import com.example.Buyaway_User.test.TestActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {

    @BindView(R.id.log_out)
    TextView  more_cat_btn ;
    TextView more_po_btn ,more_off_btn ;
    RecyclerView recyclerView,recyclerView_po,recyclerView_products;
    ImageView banner_img;
    ArrayList<CategoryModel>  po_arrayList ;
    ArrayList<NewProductModel> o_arrayList,brands_list;
    Methods methods;
    ArrayList<CatModelList> cat_arrayList ;
    ProgressDialog  progressDialog ;
    EditText search_ed ;
    //Adapters
    Category_Horizontal_Adapter categoryHorizontalAdapter;
    NewProductsAdapter newProductsAdapter,newProductsAdapter_popular;
    TextView cat_alert, off_alert,po_alert;
    TextWatchers viewModel ;
    RelativeLayout progress_layout ;
    String loadComplete_one=  "",loadComplete_two=  "", loadComplete_three=  "";

    //View Pager
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.vf_one,R.drawable.vf_two,R.drawable.vf_three};
    CommonRetroAPI_Interface  api_interface;
    MyViewModel myViewModel;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ButterKnife.bind(HomePage.this);
         Intializifields();

        progress_layout.setVisibility(View.VISIBLE);
        load_banner();
        load_Categories();
        load_brands();

        methods.TextWatcher(search_ed, new TextWatcherLink() {
            @Override
            public void Text_Watcher(String s) {
               filter(s.toString());
            }
        });

         methods.loadCategoryList(cat_arrayList, recyclerView,"home");

         BottomNavigationView btm_nav = (BottomNavigationView)findViewById(R.id.btm_nav);
         new Methods(this).btm_nav(btm_nav,HomePage.this);
         btm_nav.getMenu().findItem(R.id.btm_home).setChecked(true);

     }





    private void filter(String text ) {
        ArrayList<CatModelList> filteredList = new ArrayList<>();
        ArrayList<NewProductModel> filteredList_ = new ArrayList<>();
        ArrayList<NewProductModel> filteredList_brands = new ArrayList<>();

        for (CatModelList item :  cat_arrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

       for (NewProductModel item :  o_arrayList){
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList_.add(item);
            }
        }

       for (NewProductModel item :  brands_list){
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList_brands.add(item);
            }
        }

            categoryHorizontalAdapter.filterList_wf(filteredList,cat_alert);
          newProductsAdapter.filterList(filteredList_,off_alert);
          newProductsAdapter_popular.filterList(filteredList_brands,po_alert);

     }




    @Override
    public void onBackPressed() {
         show_exit_alert();
    }

    private void show_exit_alert() {
        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(HomePage.this);
        View view1  = getLayoutInflater().inflate(R.layout.exit_alert,null);
        alert.setCancelable(true);
        alert.setView(view1);
        androidx.appcompat.app.AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         TextView exit_btn =  (TextView)view1.findViewById(R.id.exit_btn) ;
         TextView cancel_btn =  (TextView)view1.findViewById(R.id.cancel_btn) ;
         exit_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finishAffinity();
                 alertDialog.dismiss();
             }
         });

         cancel_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 alertDialog.dismiss();
             }
         });
      alertDialog.show();

    }

    private void load_Categories() {
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
                        cat_arrayList.add(model);

                    }
                    categoryHorizontalAdapter = new Category_Horizontal_Adapter(cat_arrayList, HomePage.this,"HOME");
                    recyclerView.setAdapter(categoryHorizontalAdapter);
                    loadComplete_one = "done";

                 }else {
                    Toast.makeText(getApplicationContext(),"Something gone wrong try again later .. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CatModelList>> call, Throwable t) {

            }
        });


    }


    private void Intializifields() {
        more_cat_btn = (TextView)findViewById(R.id.more_cat_btn);
        more_po_btn = (TextView)findViewById(R.id.more_po_btn);
        more_off_btn = (TextView)findViewById(R.id.more_off_btn);
        banner_img = (ImageView) findViewById(R.id.banner_img);
        progressDialog = new ProgressDialog(HomePage.this);
        progressDialog.setMessage("Please wait while loading..... ");
        search_ed  = (EditText)findViewById(R.id.search_ed);
        cat_alert = (TextView)findViewById(R.id.cat_alert);
        off_alert = (TextView)findViewById(R.id.off_alert);
        po_alert = (TextView)findViewById(R.id.po_alert);
        viewModel = ViewModelProviders.of(HomePage.this).get(TextWatchers.class);
        progress_layout = (RelativeLayout)findViewById(R.id.progress_layout);
        cat_alert.setVisibility(View.GONE);  off_alert.setVisibility(View.GONE); po_alert.setVisibility(View.GONE);
        //ARRAY LIST
        cat_arrayList  = new ArrayList<>();
        o_arrayList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView_po = (RecyclerView)findViewById(R.id.recyclerView_po);
         recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this,RecyclerView.HORIZONTAL,false));


        more_cat_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent  intent = new Intent(getApplicationContext(), SearchPage.class);
                  startActivity(intent);
              }
          });
        more_po_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent  intent = new Intent(getApplicationContext(), OffersPage.class);
                  startActivity(intent);
              }
          });
        more_off_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent  intent = new Intent(getApplicationContext(), DetailsPage.class);
                  intent.putExtra("page_type","new_arrivals");
                  intent.putExtra("id","null");
                  intent.putExtra("title","New Arrivals");
                  startActivity(intent);
              }
          });

          //for popular offers
        methods = new Methods(this);
        po_arrayList = new ArrayList<>();
        brands_list = new ArrayList<>();

     //for  offers
        methods = new Methods(this);
        load_offers();

        Picasso.with(HomePage.this)
                .load(R.drawable.center_pic)
                .fit()
                .into(banner_img);

        categoryHorizontalAdapter = new Category_Horizontal_Adapter(cat_arrayList, HomePage.this,"HOME");
        newProductsAdapter = new NewProductsAdapter(o_arrayList,HomePage.this,"offers");
        newProductsAdapter_popular = new NewProductsAdapter(brands_list,HomePage.this,"brands");

    }

    private void load_brands() {
        api_interface = RetrofitAPIClient.getClient("APIMobileBrand/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<NewProductModel>> call = api_interface.getAll_brands();
        call.enqueue(new Callback<ArrayList<NewProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NewProductModel>> call, Response<ArrayList<NewProductModel>> response) {
                ArrayList<NewProductModel> arrayList = response.body();

                for (int i=0; i  < arrayList.size(); i++){
                    NewProductModel model = new NewProductModel();
                    model.setId(arrayList.get(i).getId());
                    model.setName(arrayList.get(i).getName());
                    model.setDetails(arrayList.get(i).getDetails());
                    model.setTag_line(arrayList.get(i).getTag_line());
                    model.setLogo(arrayList.get(i).getLogo());
                    model.setPrice(arrayList.get(i).getPrice());
                     brands_list.add(model);
                }
                    newProductsAdapter_popular = new NewProductsAdapter(brands_list,HomePage.this,"brands");
                    recyclerView_po.setAdapter(newProductsAdapter_popular);
                      progress_layout.setVisibility(View.GONE);
                      loadComplete_two = "done" ;
            }

            @Override
            public void onFailure(Call<ArrayList<NewProductModel>> call, Throwable t) {

            }
        });
    }



    private void load_offers() {
        recyclerView_products = (RecyclerView)findViewById(R.id.recyclerView_products);
        recyclerView_products.setLayoutManager(new GridLayoutManager(HomePage.this,4,GridLayoutManager.HORIZONTAL,false));
        recyclerView_po.setLayoutManager(new GridLayoutManager(HomePage.this,2,GridLayoutManager.HORIZONTAL,false));
        api_interface = RetrofitAPIClient.getClient("APIMobileNewProducts/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<NewProductModel>> call = api_interface.getAll_product();
        call.enqueue(new Callback<ArrayList<NewProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NewProductModel>> call, Response<ArrayList<NewProductModel>> response) {
                ArrayList<NewProductModel> arrayList = response.body();

                for (int i=0; i  < arrayList.size(); i++){
                    NewProductModel model = new NewProductModel();
                    model.setBrand_id(arrayList.get(i).getBrand_id());
                    model.setTitle(arrayList.get(i).getTitle());
                    model.setDescription(arrayList.get(i).getDescription());
                    model.setProduct_image(arrayList.get(i).getProduct_image());
                    model.setPrice(arrayList.get(i).getPrice());
                    o_arrayList.add(model);
                }
                newProductsAdapter = new NewProductsAdapter(o_arrayList,HomePage.this,"offers");
                 recyclerView_products.setAdapter(newProductsAdapter);
                 loadComplete_three = "done";
             }

            @Override
            public void onFailure(Call<ArrayList<NewProductModel>> call, Throwable t) {

            }
        });

     }





    @Override
    protected void onStart() {
         //showOfferAlert();
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        cat_alert.setVisibility(View.GONE);  off_alert.setVisibility(View.GONE); po_alert.setVisibility(View.GONE);
    }

    private void showOfferAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.offers_alert,null);

        CircleImageView close_btn = (CircleImageView)view.findViewById(R.id.close_icon);

        builder.setView(view);
        final AlertDialog show = builder.show();

        show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    private void load_banner(){
        ArrayList<String> IMAGES = new ArrayList<>();
        api_interface = RetrofitAPIClient.getClient("APIMobileBanner/").create(CommonRetroAPI_Interface.class);
   Call<ArrayList<NewProductModel>> call = api_interface.getBanners();
   call.enqueue(new Callback<ArrayList<NewProductModel>>() {
       @Override
       public void onResponse(Call<ArrayList<NewProductModel>> call, Response<ArrayList<NewProductModel>> response) {
           ArrayList<NewProductModel> arrayList = response.body();
         for (int i = 0; i <arrayList.size(); i++){
            IMAGES.add( "http://webbuyaway.buyaway.in/"+arrayList.get(i).getLogo());
         }
         init(IMAGES);

         }

       @Override
       public void onFailure(Call<ArrayList<NewProductModel>> call, Throwable t) {

       }
   });
    }

    private void init(ArrayList<String> IMAGES) {
        ArrayList<String> ImagesArray = new ArrayList<String>();
        for(int i=0;i<IMAGES.size();i++)

         ImagesArray.add(IMAGES.get(i).toString());

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new SlidingImage_Adapter(HomePage.this,ImagesArray));



        final float density = getResources().getDisplayMetrics().density;

        NUM_PAGES =IMAGES.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


    }


}