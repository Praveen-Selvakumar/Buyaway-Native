package com.example.Buyaway_User.mainFrame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.login_pack.ProfilePage;
import com.fxn.pix.Pix;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileImage extends AppCompatActivity {

    ImageView profile_img;
    //Bottom Sheet
    CardView btm_sheet ;
    BottomSheetBehavior bottomSheetBehavior;
    TextView profile_txt;
    String clicked = "";
    CommonRetroAPI_Interface api_interface ;
    RelativeLayout delete_lt, upload_lt, cancel_lt;
    Bitmap bitmap ;
    String get_name = " ", get_email = " " , get_contact_number = " " ,get_gender = " " , get_address = " " , get_date_of_birth = " ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_image);
         InitializeFields();

         String url = getIntent().getStringExtra("img");

         if ( url !=  null && !url.isEmpty() ){
             Picasso.with(EditProfileImage.this).load(url).fit().error(R.drawable.user_icon).into(profile_img);
         }


    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void InitializeFields() {
        api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);

        profile_img = (ImageView)findViewById(R.id.profile_img);
        //Bottom Sheet
        //Bottom Sheet
        btm_sheet = (CardView)findViewById(R.id.btm_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(btm_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        profile_txt = (TextView)findViewById(R.id.profile_txt);

        delete_lt = (RelativeLayout)btm_sheet.findViewById(R.id.delete_lt);
        upload_lt = (RelativeLayout)btm_sheet.findViewById(R.id.upload_lt);
        cancel_lt = (RelativeLayout)btm_sheet.findViewById(R.id.cancel_lt);

        //GET PROFILE DETAILS
        get_name = getIntent().getStringExtra("name");
        get_email = getIntent().getStringExtra("email");
        get_contact_number = getIntent().getStringExtra("number");
        get_gender = getIntent().getStringExtra("gender");
        get_address = getIntent().getStringExtra("address");
        get_date_of_birth = getIntent().getStringExtra("dob");



        profile_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              load_bottomSheet();
            }
        });

        //TO UPLOAD PROFILE IMAGE
        upload_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestpermission();
            }
        });

        cancel_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                clicked = "";
            }
        });


    }

    private void load_bottomSheet(){
        if (clicked.equals("")){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            clicked = "clicked";
        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            clicked = "";
        }

    }

    public void requestpermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        String rationale = "Please provide permissions so that you can Use Our App";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Note")
                .setSettingsDialogTitle("Warning");

        Permissions.check(EditProfileImage.this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Pix.start(EditProfileImage.this, 19);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 19) {
            ArrayList<String> returnValue;
            returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            Uri uri = Uri.parse(returnValue.get(0));
            String get_uri = uri.getPath().toString();
            Log.d("bitmap", get_uri);
            bitmap = BitmapFactory.decodeFile(uri.getPath());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            clicked = "";
            profile_img.setImageBitmap(bitmap);
            Log.d("base64", ProfilePage.imageToString(bitmap));
            String get_profile_image = ProfilePage.imageToString(bitmap);
            update_profile(get_name, get_email, get_contact_number, get_gender, get_address, get_date_of_birth, get_profile_image);

        }
    }


    private void update_profile(String name,String email, String  contact_number,String gender,
                                String address, String date_of_birth ,String profile_image ){
        Call<UserModel> call = api_interface.edit_profile(name,email,contact_number,gender,address,
                date_of_birth,profile_image);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                Log.d("response",response.body().toString());
                UserModel app = response.body();
                if (response.isSuccessful()){
                    if (app.getStatus().equals("Success")){
                        Toast.makeText(EditProfileImage.this,"Profile Image Updated Successfully..!", Toast.LENGTH_SHORT).show();
                     }
                }else {
                    Toast.makeText(EditProfileImage.this,"Something gone wrong , Try again later ..!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }




}