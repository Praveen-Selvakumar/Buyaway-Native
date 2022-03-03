package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Buyaway_User.R;
import com.squareup.picasso.Picasso;

public class NewArrival extends AppCompatActivity {
    ImageView img_view , back_btn ;
    TextView brand_name_txt, brand_des_txt,   alert_txt, product_title;
    CardView cd, cd_two ;
    String status = " " ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_arrival);

        img_view = (ImageView)findViewById(R.id.pr_img);
        back_btn = (ImageView)findViewById(R.id.back_bg);
        brand_name_txt = (TextView) findViewById(R.id.brand_name_txt);

        brand_des_txt = (TextView) findViewById(R.id.brand_des_txt);
        alert_txt = (TextView) findViewById(R.id.alert_txt);
         product_title = (TextView) findViewById(R.id.product_title);
        cd = (CardView) findViewById(R.id.cd);
        cd_two = (CardView) findViewById(R.id.cd_two);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        String get_name  =  getIntent().getStringExtra("name");
        String get_price  =  getIntent().getStringExtra("price");
        String get_description  =  getIntent().getStringExtra("description");
        String get_image  =  getIntent().getStringExtra("image");
        String get_title  =  getIntent().getStringExtra("title");
        String get_id=  getIntent().getStringExtra("id");

        brand_name_txt.setText(get_name);
        brand_des_txt.setText(get_description);
        Picasso.with(this).load("http://webbuyaway.buyaway.in/"+get_image).fit().into(img_view);
     //   Glide.with(NewArrival.this).load("http://webbuyaway.buyaway.in/"+get_image).fitCenter().into(img_view);


        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals(" ")) {
                    if (get_price == null){
                        alert_txt.setText("Price is not updated");
                    }else {
                        alert_txt.setText(get_price);
                    }
                    //creating an animation
                    final Animation slide_down = AnimationUtils.loadAnimation(NewArrival.this, R.anim.slide_down);
                    final Animation slide_down_t = AnimationUtils.loadAnimation(NewArrival.this, R.anim.slide_down_t);

                    //toggling visibility
                    cd_two.setVisibility(View.VISIBLE);
                    setMargins(brand_name_txt, 30, 180, 0, 0);
                    setMargins(brand_des_txt, 30, 240, 0, 0);

                    brand_name_txt.setAnimation(slide_down_t);
                    brand_des_txt.setAnimation(slide_down_t);

                    //adding sliding effect
                    cd_two.startAnimation(slide_down);
                   status = "clicked";
                }else {
                  //  cd.setVisibility(View.GONE);
                    cd_two.setVisibility(View.GONE);
                    status = " ";
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent  intent = new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }



}