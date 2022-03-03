package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.Buyaway_User.Common.Utils;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.ProductListModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.DetailsPage;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.example.Buyaway_User.mainFrame.SearchPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Category_Horizontal_Adapter extends RecyclerView.Adapter<Category_Horizontal_Adapter.ViewHolder> {

    ArrayList<CatModelList> arrayList ;
     String page_ref;
    HomePage homePage;
    SearchPage searchPage;


    public Category_Horizontal_Adapter(ArrayList<CatModelList> arrayList    , SearchPage searchPage1 , String page_ref) {
        this.arrayList = arrayList;
         this.searchPage = searchPage1;
        this.page_ref = page_ref;
    }

    public Category_Horizontal_Adapter(ArrayList<CatModelList> arrayList    ,  HomePage homePage ,String page_ref) {
        this.arrayList = arrayList;
         this.homePage = homePage;
        this.page_ref = page_ref;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatModelList app = arrayList.get(position);
        String  url = "http://webbuyaway.buyaway.in/"+app.getIcon();
        String name = app.getName();
           final  Context context ;
           final  Intent intent  ;
        if (page_ref.equals("HOME")){
            context = homePage.getApplicationContext();
             intent = new Intent(homePage.getApplicationContext(), DetailsPage.class);
            intent.putExtra("title",app.getName().toString());

        }else {
            context = searchPage.getApplicationContext();
             intent = new Intent(searchPage.getApplicationContext(), DetailsPage.class);
            intent.putExtra("title",app.getName().toString());
        }

           //val compressedImageFile = Compressor.compress(context, actualImageFile)

            load_text(holder.txt, name);
            Utils.fetchSvg(context.getApplicationContext(), url, holder.img);
            holder.touch_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   intent.putExtra("page_type","Category");
                   intent.putExtra("id",String.valueOf( app.getID() ));
                    if (page_ref.equals("HOME")){
                        homePage.startActivity(intent);
                    }else {
                        searchPage.startActivity(intent);
                    }
                }
            });

         //Compressor compressedImageFile = new Compressor().compress(context.getApplicationContext(), )

    }

    @Override
    public int getItemCount() {
         return  arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
             TextView txt;
             RelativeLayout touch_bg ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_img);
            txt = (TextView) itemView.findViewById(R.id.item_txt);
            touch_bg = (RelativeLayout) itemView.findViewById(R.id.touch_bg);
        }
    }

    private void  load_text(TextView txt,String name){
        if (name.length() >= 10){
            String separated[] = name.split(" ");
            txt.setText(separated[0]+"\n"+separated[1]);
        }else {
            txt.setText(name);
        }
    }

    public void filterList_wf(ArrayList<CatModelList> filteredList, TextView txt) {
        arrayList = filteredList;
        if (arrayList.size() == 0 ){
            txt.setVisibility(View.VISIBLE);
        }else {
            txt.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

}
