package com.example.Buyaway_User.login_pack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Common.Methods;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.RegisterUserModel;
import com.example.Buyaway_User.mainFrame.EditProfileImage;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.example.Buyaway_User.test.TestActivity;
import com.fxn.pix.Pix;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePage extends AppCompatActivity {

    TextInputEditText ed_name, ed_phone, ed_address, ed_mailid, ed_gender, ed_DOB,
            current_pwd_ed, new_pwd_ed ;
    CommonRetroAPI_Interface api_interface;
    String url = "";
    RelativeLayout progress_lt ;
    //Bottom Sheet
    CardView btm_sheet ;
    BottomSheetBehavior bottomSheetBehavior;
    RelativeLayout  edit_profile_image,  delete_lt, upload_lt, cancel_lt,
            edit_profile_lt , change_pwd_lt ;
    Bitmap bitmap;
    String clicked = "";
    CircleImageView profile_img;
    MaterialButton submit_btn , change_pwd_btn ;
    TextView change_pwd_btn_move,  log_out_btn;
    ImageView back_btn_cp,back_btn ;
    UserLocalStore userLocalStore ;
    String base64 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        IntializeFields();

        //Bottom Navigation
        BottomNavigationView btm_nav = (BottomNavigationView) findViewById(R.id.btm_nav_lt);
        new Methods(this).btm_nav(btm_nav, ProfilePage.this);
        btm_nav.getMenu().findItem(R.id.btm_login).setChecked(true);


        //LOAD_USER_DATA
        /*if (userLocalStore.getUserModel().getPhone_number().isEmpty()){
            progress_lt.setVisibility(View.VISIBLE);
            load_user_details();
        }else {
            loadFromLocal();
        }*/

        load_user_details();


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        //TO OPEN BOTTOM SHEET
        edit_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        back_btn_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile_lt.setVisibility(View.VISIBLE);
                change_pwd_lt.setVisibility(View.GONE);
            }
        });

        change_pwd_btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile_lt.setVisibility(View.GONE);
                change_pwd_lt.setVisibility(View.VISIBLE);
            }
        });

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.delete_data();
                startActivity(new Intent(getApplicationContext(), Login_Page.class));
            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_lt.setVisibility(View.VISIBLE);
                load_editProfile();
            }
        });

        change_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_password();
            }
        });



        //TO UPLOAD PROFILE IMAGE
        upload_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestpermission();
            }
        });

        delete_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UPDATE PROFILE
                String get_name = ed_name.getText().toString();
                String get_email = ed_mailid.getText().toString();
                String get_contact_number = ed_phone.getText().toString();
                String get_gender = ed_gender.getText().toString();
                String get_address = ed_address.getText().toString();
                String get_date_of_birth = ed_DOB.getText().toString();
                Bitmap get_img =   null;
                progress_lt.setVisibility(View.VISIBLE);
                update_profile(get_name, get_email, get_contact_number, get_gender, get_address, get_date_of_birth, imageToString(get_img));
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        cancel_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                //   clicked = "";
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }




    private  void change_password(){
        String  current_pwd =  current_pwd_ed.getText().toString();
        String  new_pwd = new_pwd_ed.getText().toString();
        Call<UserModel> call = api_interface.ChangePassword(   userLocalStore.isUserLogged().getPhone_number() ,current_pwd,new_pwd);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("Success")){
                        Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        current_pwd_ed.setText(" ");  new_pwd_ed.setText(" ");
                    }
                }else {
                    Toast.makeText(ProfilePage.this, "Something  gone wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });


    }

    public void requestpermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        String rationale = "Please provide permissions so that you can Use Our App";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Note")
                .setSettingsDialogTitle("Warning");

        Permissions.check(ProfilePage.this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Pix.start(ProfilePage.this, 19);
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
            profile_img.setVisibility(View.VISIBLE);
            base64 = imageToString(bitmap);
            Log.d("base64", imageToString(bitmap));

            //UPDATE PROFILE
            String get_name = ed_name.getText().toString();
            String get_email = ed_mailid.getText().toString();
            String get_contact_number = ed_phone.getText().toString();
            String get_gender = ed_gender.getText().toString();
            String get_address = ed_address.getText().toString();
            String get_date_of_birth = ed_DOB.getText().toString();
            Bitmap get_img = convertImageViewToBitmap(profile_img);

            //progress_lt.setVisibility(View.VISIBLE);
           // update_profile(get_name, get_email, get_contact_number, get_gender, get_address, get_date_of_birth, imageToString(get_img));


        }
    }

    private Bitmap convertImageViewToBitmap(CircleImageView v){

        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();

        return bm;
    }


    public static String imageToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imgebytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgebytes, Base64.DEFAULT);
        }
        return "";
    }


    private void IntializeFields() {
        progress_lt = (RelativeLayout)findViewById(R.id.progress_profile_lt);
        ed_name = (TextInputEditText)findViewById(R.id.name_ed);
        ed_mailid = (TextInputEditText)findViewById(R.id.ed_mail_id);
        ed_phone = (TextInputEditText)findViewById(R.id.ed_phone);
        ed_gender= (TextInputEditText)findViewById(R.id.ed_gender);
        ed_address = (TextInputEditText)findViewById(R.id.ed_add);
        ed_DOB = (TextInputEditText)findViewById(R.id.ed_DOB);
        api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
        //Bottom Sheet
        btm_sheet = (CardView)findViewById(R.id.btm_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(btm_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        delete_lt = (RelativeLayout)btm_sheet.findViewById(R.id.delete_lt);
        upload_lt = (RelativeLayout)btm_sheet.findViewById(R.id.upload_lt);
        cancel_lt = (RelativeLayout)btm_sheet.findViewById(R.id.cancel_lt);
        edit_profile_lt = (RelativeLayout)findViewById(R.id.edit_profile_lt);
        change_pwd_lt = (RelativeLayout)findViewById(R.id.change_pwd_lt);
        edit_profile_image = (RelativeLayout)findViewById(R.id.edit_profile_image);
        profile_img = (CircleImageView) findViewById(R.id.profile_img);
        submit_btn = (MaterialButton)findViewById(R.id.submit_btn);
        change_pwd_btn_move = (TextView)findViewById(R.id.change_pwd_btn_move);
        log_out_btn = (TextView)findViewById(R.id.log_out_btn);
        userLocalStore = new UserLocalStore(ProfilePage.this);
        //Change Password btn
        new_pwd_ed = (TextInputEditText)findViewById(R.id.new_pwd_ed);
        current_pwd_ed = (TextInputEditText)findViewById(R.id.current_pwd_ed);
        change_pwd_btn = (MaterialButton)findViewById(R.id.change_pwd_btn);
        back_btn_cp = (ImageView)findViewById(R.id.back_btn_cp);
        back_btn = (ImageView)findViewById(R.id.back_btn);
    }


    private void update_profile(String name,String email, String  contact_number,String gender,
                                String address, String date_of_birth ,String profile_image){

        Call<UserModel> call = api_interface.edit_profile(name,email,contact_number,gender,address,
                date_of_birth,profile_image);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                UserModel app = response.body() ;
                 if (response.isSuccessful()){
                    if (app.getStatus().equals("Success")){
                        Toast.makeText(ProfilePage.this,"Profile Updated Successfully..!", Toast.LENGTH_SHORT).show();
                        load_user_details();
                    }
                }else {
                    Toast.makeText(ProfilePage.this,"Something gone wrong , Try again later ..!", Toast.LENGTH_SHORT).show();
                        progress_lt.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }




    //LOAD AND GET USER DETIALS
    private void load_user_details(){
        Call<UserModel> call = api_interface.get_profile_detail(userLocalStore.isUserLogged().getPhone_number());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    UserModel app = response.body();
                    String get_name = app.getName();   String getContact_number = app.getContact_number();
                    String getEmail = app.getEmail();    String getGender = app.getGender();
                    String getDate_of_birth = app.getDate_of_birth();  String getAddress = app.getAddress();
                    base64 =  app.getProfile_image();

                    ed_name.setText(get_name !=null && !get_name.isEmpty() ? get_name : "" );
                    ed_mailid.setText(getEmail);
                    ed_phone.setText(getContact_number);
                    ed_gender.setText(getGender !=null && !getGender.isEmpty() ? getGender : "" );
                    if (app.getDate_of_birth() != null){
                        ed_DOB.setText(getDate_of_birth);
                    }
                    if ( app.getProfile_image() != null &&  !app.getProfile_image().isEmpty() ){
                        byte[]  imageBytes = Base64.decode( base64  , Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        profile_img.setImageBitmap(decodedImage);
                        profile_img.setVisibility(View.VISIBLE);
                    }else {
                        profile_img.setVisibility(View.GONE);
                    }

                    if ( app.getAddress() != null){
                        ed_address.setText(getAddress);
                    }
                     userLocalStore.storeUserData(new RegisterUserModel(get_name, getEmail,  getContact_number,
                            getGender, getDate_of_birth, getAddress,url));
                }

                 progress_lt.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }


    private void load_editProfile() {
        //UPDATE PROFILE
        String get_name = ed_name.getText().toString();
        String get_email = ed_mailid.getText().toString();
        String get_contact_number = ed_phone.getText().toString();
        String get_gender = ed_gender.getText().toString();
        String get_address = ed_address.getText().toString();
        String get_date_of_birth = ed_DOB.getText().toString();
        progress_lt.setVisibility(View.VISIBLE);
        update_profile(get_name, get_email, get_contact_number, get_gender, get_address, get_date_of_birth,  base64 );
    }

}