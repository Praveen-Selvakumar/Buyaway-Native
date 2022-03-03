package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.media.Image;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Model.ProductListModel;
import com.example.Buyaway_User.Model.StoreListModel;
import com.example.Buyaway_User.Model.TotalModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.OffersPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    OffersPage context;
    ArrayList<ProductListModel>  arrayList;
    String status = null ;
    Methods methods;
    RecyclerView recyclerView_ ;
    private static int currentPosition = 0;
    View view_;


    public ProductListAdapter(OffersPage context, ArrayList<ProductListModel> arrayList,RecyclerView recyclerView1) {
        this.context = context;
        this.arrayList = arrayList;
        methods = new Methods(context.getApplicationContext());
       this.recyclerView_ = recyclerView1;
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
        ProductListModel app = arrayList.get(position);

        holder.brand_name_txt.setText(app.getProduct_name());
        holder.brand_des_txt.setText(app.getProduct_des());

        Picasso.with(context.getApplicationContext())
                .load(app.getStore_img())
                .fit().into(holder.img_view);

    //    ArrayList<StoreListModel> storeListModelArrayList  = new ArrayList<>();
        //methods.store_list_array(storeListModelArrayList);
      //  holder.recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), RecyclerView.VERTICAL,false));
       // holder.recyclerView.setAdapter(new ExpandableAdapter(TotalModel.AvailableStocksList ,  context.getApplicationContext()));


        boolean isExpandable  = arrayList.get(position).isExpanded();
        holder.cd_two.setVisibility(isExpandable? View.VISIBLE : View.GONE);


        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {


            //creating an animation
             final Animation slide_down = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down);
            final Animation slide_down_t = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down_t);

            //toggling visibility
             holder.cd_two.setVisibility(View.VISIBLE);
            setMargins(holder.brand_name_txt,30,180,0,0);
            setMargins(holder.brand_des_txt,30,240,0,0);

            holder.brand_name_txt.setAnimation(slide_down_t); holder.brand_des_txt.setAnimation(slide_down_t);

            //adding sliding effect
            holder.cd_two.startAnimation(slide_down);

         }

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;


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
        TextView brand_name_txt, brand_des_txt;
        CardView cd_two,cd;
        View view_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerView_Pr);
            img_view = (ImageView)itemView.findViewById(R.id.pr_img);
            cd_two = (CardView) itemView.findViewById(R.id.cd_two);
            cd = (CardView) itemView.findViewById(R.id.cd);
            brand_name_txt = (TextView) itemView.findViewById(R.id.brand_name_txt);
            brand_des_txt = (TextView) itemView.findViewById(R.id.brand_des_txt);



         /*  cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == null ){
                        final Animation slide_down_t = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down_t);
                        final Animation slide_down = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down);
                        setMargins(brand_name_txt,30,220,0,0);
                        setMargins(brand_des_txt,30,280,0,0);
                        brand_name_txt.setAnimation(slide_down_t); brand_des_txt.setAnimation(slide_down_t);
                        //holder.cd_two.getChildAt(get_position).setAnimation(slide_down);
                        //holder.cd_two.getChildAt(get_position).setVisibility(View.VISIBLE);
                        cd_two.getChildAt(0).setAnimation(slide_down);
                        cd_two.setVisibility(View.VISIBLE);
                        status = "opened";
                    }else {
                        view_ = recyclerView_.findViewHolderForAdapterPosition(getAdapterPosition()+1).itemView;
                        RelativeLayout  rel_top =  (RelativeLayout) view_.findViewById(R.id.rel_top);
                        setMargins( rel_top,0,-30,0,0);

                        final Animation slide_up = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_up);
                        final Animation slide_up_t = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_up_t);
                        setMargins( brand_name_txt,30,180,0,0);
                        setMargins(brand_des_txt,30,240,0,0);

                        // cd_.setAnimation(slide_up);
                       brand_name_txt.setAnimation(slide_up_t);  brand_des_txt.setAnimation(slide_up_t);

                           cd_two .setAnimation(slide_up);

                        rel_top.setAnimation(slide_up);
                     //   cd_two.setVisibility(View.GONE);

                        // holder.recyclerView.setVisibility(View.GONE);
                        context.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                 notifyDataSetChanged();
                                //holder.cd_two.setVisibility(View.GONE);
                            }
                        },280);
                        status = null;
                    }
                }
            });*/

        }
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public void filterList(ArrayList<ProductListModel> filteredList) {
        arrayList = filteredList;
        notifyDataSetChanged();
    }

}
