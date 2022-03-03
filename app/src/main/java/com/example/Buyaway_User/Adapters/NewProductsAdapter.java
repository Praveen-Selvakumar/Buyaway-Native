package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.DetailsPage;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.example.Buyaway_User.mainFrame.NewArrival;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewProductsAdapter  extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder>{


    ArrayList<NewProductModel> arrayList;
    HomePage homePage;
    String type ;


    public NewProductsAdapter(ArrayList<NewProductModel> arrayList, HomePage homePage1, String type_) {
        this.arrayList = arrayList;
        this.homePage = homePage1;
        this.type = type_;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_offers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewProductModel app = arrayList.get(position);

        if (type.equals("offers")){
            holder.offer_lt.setVisibility(View.VISIBLE) ; holder.brand_lt.setVisibility(View.GONE);
            holder.item_txt.setText(app.getTitle());
            holder.item_offer_hint.setText(app.getDescription());
            load_img(app.getProduct_image(),holder.item_img);
        }else if (type.equals("brands")){
            Glide.with(homePage.getApplicationContext()).load("http://webbuyaway.buyaway.in/"+app.getLogo()).into(holder.brand_img);
            // load_img(app.getLogo(),holder.brand_img);
            Log.d("img_name","http://webbuyaway.buyaway.in/"+app.getLogo());
            String getDetails = "<font color='#808080'>" +app.getDetails() + "</font>";
             if (app.getTag_line() != null){
                holder.brand_name.setText(Html.fromHtml(app.getName()+"<br>"+getDetails+"<br>"+app.getTag_line()));
            }else {
                holder.brand_name.setText(Html.fromHtml(app.getName()+"<br>"+getDetails));
            }
        }

        holder.offer_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.getApplicationContext(),  NewArrival.class);
                intent.putExtra("name",app.getTitle());
                intent.putExtra("price",app.getPrice());
                intent.putExtra("description",app.getDescription());
                intent.putExtra("image",app.getProduct_image());
                intent.putExtra("title","New Arrivals");
                intent.putExtra("id",app.getBrand_id());
                homePage.startActivity(intent);
            }
        });

        holder.brand_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.getApplicationContext(), DetailsPage.class);
                intent.putExtra("page_type","Brand");
                 if (type.equals("brands")){
                    intent.putExtra("id",String.valueOf( app.getId() ));
                    intent.putExtra("title","Popular Brands");
                }else {
                    intent.putExtra("id",String.valueOf( app.getBrand_id() ));
                     intent.putExtra("title","Popular Brands");
                 }
                  homePage.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img, brand_img;
        TextView item_txt, item_offer_hint;
        TextView brand_name ;

        RelativeLayout  offer_lt ,  brand_lt ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_img = (ImageView)itemView.findViewById(R.id.item_img);
            brand_img = (ImageView)itemView.findViewById(R.id.brand_img);
            item_txt = (TextView)itemView.findViewById(R.id.item_txt);
            item_offer_hint = (TextView)itemView.findViewById(R.id.item_offer_hint);
            brand_name = (TextView)itemView.findViewById(R.id.brand_txt);

            brand_lt = (RelativeLayout)itemView.findViewById(R.id.brand_lt);
            offer_lt = (RelativeLayout)itemView.findViewById(R.id.offer_lt);
        }

    }
    public void filterList(ArrayList<NewProductModel> filteredList, TextView txt) {
        arrayList = filteredList;
        if (arrayList.size() == 0 ){
            txt.setVisibility(View.VISIBLE);
        }else {
            txt.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

    private void load_img(String img_name,ImageView img){
        Picasso.with(homePage.getApplicationContext())
                .load("http://webbuyaway.buyaway.in/"+img_name)
                .fit()
                .into(img);

    }

}
