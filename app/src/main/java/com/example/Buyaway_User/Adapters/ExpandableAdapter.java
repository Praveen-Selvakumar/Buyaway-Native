package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Buyaway_User.Model.StoreListModel;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.GoogleMapActivity;
import com.example.Buyaway_User.mainFrame.OffersPage;

import java.util.ArrayList;

public class ExpandableAdapter extends RecyclerView.Adapter<ExpandableAdapter.ViewHolder> {

    ArrayList<TotalModel.StoreandStock> arrayList;
    Context context;

    public ExpandableAdapter(ArrayList<TotalModel.StoreandStock> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_store_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TotalModel.StoreandStock app = arrayList.get(position);

         if (!app.getStore_name().equals("null")){
             holder.store_name.setText(app.getStore_name());
             int percentage = Integer.valueOf(app.getAvailability());

             if (percentage <= 25){
                 setColorFilter(holder.progressBar, R.color.pb_three);
             }else if (percentage > 25 && percentage <= 50){
                 setColorFilter(holder.progressBar, R.color.pb_two);
             }else if (percentage > 50){
                 setColorFilter(holder.progressBar, R.color.pb_one);
             }
             holder.progressBar.setProgress(percentage);

             if (!app.getOffer_tag().isEmpty() && app.getOffer_tag() !=  null){
                 holder.store_des.setText(app.getOffer_tag());
             }

         }else {
             holder.alert_txt.setVisibility(View.VISIBLE);
             holder.store_name.setVisibility(View.GONE);
             holder.store_des.setVisibility(View.GONE);
             holder.cd_progress.setVisibility(View.GONE);
             holder.pointer_icon.setVisibility(View.GONE);
         }




        holder.pointer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent  intent = new Intent(context.getApplicationContext(), GoogleMapActivity.class);
                intent.putExtra("store_name",app.getStore_name());
                intent.putExtra("lat",app.getLatitude());
                intent.putExtra("lng",app.getLongitude());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/

                String urlAddress = "http://maps.google.com/maps?q="+ Double.valueOf(app.getLatitude())  +"," + Double.valueOf(app.getLongitude()) +"("+ "Some " + ")&iwloc=A&hl=es";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

               /* Uri gmmIntentUri = Uri.parse("geo:"+Double.valueOf(app.getLatitude())+","+Double.valueOf(app.getLongitude()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mapIntent);*/
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView store_name, store_des, alert_txt ;
        ImageView pointer_icon;
        ProgressBar progressBar;
        CardView cd_progress ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.pb_horizontal);
            cd_progress = (CardView) itemView.findViewById(R.id.cd_progress);
            pointer_icon = (ImageView) itemView.findViewById(R.id.pointer_icon);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            store_des = (TextView) itemView.findViewById(R.id.store_des);
            alert_txt = (TextView) itemView.findViewById(R.id.alert_txt);


        }
    }

    public void setColorFilter(ProgressBar progressBar,int color){
        progressBar.getProgressDrawable().setColorFilter(
                context.getResources().getColor(color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

}
