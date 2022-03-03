package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.Model.ProductListModel;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.OffersPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OffersAdapter  extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    ArrayList<TotalModel> arrayList;
    Context context;
    private static int currentPosition = 0;
    ArrayList<TotalModel.StoreandStock> totalModelArrayList = new ArrayList<TotalModel.StoreandStock>() ;
    String type ;


    public OffersAdapter(ArrayList<TotalModel> arrayList, Context context,String type) {
        this.arrayList = arrayList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TotalModel app = arrayList.get(position);

        Picasso.with(context).load("http://webbuyaway.buyaway.in/"+app.getProduct_image()).fit().into(holder.img_view);

        //Glide.with(context).load("http://webbuyaway.buyaway.in/"+app.getProduct_image()).into(holder.img_view);


        if (type.equals("offers")) {
            holder.brand_name_txt.setText(app.getBrand_name());
            holder.brand_des_txt.setText(app.getTitle());
        }else {
            holder.brand_name_txt.setText(app.getTitle());
            holder.brand_des_txt.setText(app.getDescription());
         }


        boolean isExpandable  = arrayList.get(position).isExpanded();
        holder.cd_two.setVisibility(isExpandable? View.VISIBLE : View.GONE);


            //if the position is equals to the item position which is to be expanded
            if (currentPosition == position) {
                totalModelArrayList = app.getArrayList();
                if (type.equals("offers")) {
                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), RecyclerView.VERTICAL, false));
                    holder.recyclerView.setAdapter(new ExpandableAdapter(totalModelArrayList, context.getApplicationContext()));
                }else {
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.alert_txt.setVisibility(View.VISIBLE);
                    holder.alert_txt.setText(app.getPrice());
                }


                //creating an animation
                final Animation slide_down = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down);
                final Animation slide_down_t = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down_t);

                //toggling visibility
                holder.cd_two.setVisibility(View.VISIBLE);
                setMargins(holder.brand_name_txt, 30, 180, 0, 0);
                setMargins(holder.brand_des_txt, 30, 240, 0, 0);

                holder.brand_name_txt.setAnimation(slide_down_t);
                holder.brand_des_txt.setAnimation(slide_down_t);

                //adding sliding effect
                holder.cd_two.startAnimation(slide_down);
            }


            holder.cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //getting the position of the item to expand it
                    currentPosition = position;

                    totalModelArrayList = app.getArrayList();
                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), RecyclerView.VERTICAL, false));
                    holder.recyclerView.setAdapter(new ExpandableAdapter(totalModelArrayList, context.getApplicationContext()));

                    notifyDataSetChanged();
                }
            });


}



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView  recyclerView ;
        ImageView  img_view ;
        TextView brand_name_txt, brand_des_txt,   alert_txt;
        CardView cd, cd_two ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerView_Pr);
            img_view = (ImageView)itemView.findViewById(R.id.pr_img);
            brand_name_txt = (TextView) itemView.findViewById(R.id.brand_name_txt);

            brand_des_txt = (TextView) itemView.findViewById(R.id.brand_des_txt);
            alert_txt = (TextView) itemView.findViewById(R.id.alert_txt);
            cd = (CardView) itemView.findViewById(R.id.cd);
            cd_two = (CardView) itemView.findViewById(R.id.cd_two);

        }

    }
    public void filterList(ArrayList<TotalModel> filteredList) {
        arrayList = filteredList;
        notifyDataSetChanged();
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


}

