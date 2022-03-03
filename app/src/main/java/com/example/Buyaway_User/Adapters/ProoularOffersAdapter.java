package com.example.Buyaway_User.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Buyaway_User.Model.CategoryModel;
import com.example.Buyaway_User.Model.ProductModle;
import com.example.Buyaway_User.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProoularOffersAdapter  extends RecyclerView.Adapter<ProoularOffersAdapter.ViewHolder> {

    ArrayList<CategoryModel> arrayList;
    Context context;

    public ProoularOffersAdapter(ArrayList<CategoryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_po,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel app = arrayList.get(position);

        holder.item_txt.setText(app.getCat_name());

        Picasso.with(context.getApplicationContext())
                .load(app.getCat_img())
                .into(holder.item_img);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_img = (ImageView)itemView.findViewById(R.id.item_img);
            item_txt = (TextView)itemView.findViewById(R.id.item_txt);
        }

    }
}
