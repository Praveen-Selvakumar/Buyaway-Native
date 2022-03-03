package com.example.Buyaway_User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.login_pack.Login_Page;
import com.example.Buyaway_User.mainFrame.HomePage;

public class SplshScreen  extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 2000;
    UserLocalStore userLocalStore;
    TextView app_name,app_moto  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        app_name = (TextView)findViewById(R.id.app_name);
        app_moto = (TextView)findViewById(R.id.app_moto);
        app_moto.setVisibility(View.GONE);
        app_moto.setVisibility(View.GONE);

        userLocalStore = new UserLocalStore(this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                 if (!userLocalStore.isUserLogged().getPhone_number().isEmpty()){
                    Intent mainIntent = new Intent(SplshScreen.this, HomePage.class);
                    startActivity(mainIntent);
                     finish();
                }else {
                    Intent mainIntent = new Intent(SplshScreen.this, Login_Page.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        },  SPLASH_DISPLAY_LENGTH  );
    }

}
