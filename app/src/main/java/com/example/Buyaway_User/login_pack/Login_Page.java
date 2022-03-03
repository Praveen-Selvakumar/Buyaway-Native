package com.example.Buyaway_User.login_pack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Page extends AppCompatActivity {
    @BindView(R.id.login_page_content)RelativeLayout login_lt;
    @BindView(R.id.user_name_ed)  EditText username_ed;
    @BindView(R.id.password_ed) EditText password_ed;
    String isPasswordChecked = "";
    @BindView(R.id.forgot_password_btn)TextView forgot_password_btn;
    @BindView(R.id.register_btn)TextView register_btn;
    @BindView(R.id.sign_in_btn)TextView sign_in_btn;
    @BindView(R.id.user_name_lt) TextInputLayout user_name_lt;
    @BindView(R.id.password_lt)TextInputLayout password_lt;
    @BindView(R.id.skip_now_btn)TextView skip_now_btn;
    UserLocalStore userLocalStore;
    CommonRetroAPI_Interface api_interface ;
    ProgressDialog progressDialog;
        RelativeLayout progress_lt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);
        ButterKnife.bind(Login_Page.this);
        progress_lt = (RelativeLayout)findViewById(R.id.progress_lt);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while Loading....");
        userLocalStore = new UserLocalStore(this);
        RelativeLayout overall_icons_lt = (RelativeLayout)findViewById(R.id.overall_icons_lt);
        overall_icons_lt.setVisibility(View.VISIBLE);

        login_lt.setVisibility(View.VISIBLE);

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_name = username_ed.getText().toString();
                String get_password = password_ed.getText().toString();
                validateLoginCredentials(get_name,get_password);
            }
        });

        username_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name_lt.setErrorEnabled(false);
                password_lt.setErrorEnabled(false);
            }
        });

        password_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name_lt.setErrorEnabled(false);
                password_lt.setErrorEnabled(false);
            }
        });

        skip_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this, HomePage.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_Page.class);
                startActivity(intent);
            }
        });


        forgot_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Forgot_Password_Page.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private  void  validateLoginCredentials(String username, String password){

        if (username.isEmpty() && password.isEmpty()) {
            validate_firm(user_name_lt,"Enter User name" );
           validate_firm(password_lt,"Enter Password" );
        }else if (username.isEmpty()){
            validate_firm(user_name_lt,"Enter  User name" );
        }else if (password.isEmpty()){
            validate_firm(password_lt,"Enter Password" );
        }else {
            //progressDialog.show();
            progress_lt.setVisibility(View.VISIBLE);
            load_login(username,password);
        }

    }

    private void load_login(String username, String password) {
           api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
           Call<UserModel>  call =  api_interface.login_user(username,password);
           call.enqueue(new Callback<UserModel>() {
               @Override
               public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                               // progressDialog.cancel();
                   progress_lt.setVisibility(View.GONE);
                               UserModel res = response.body();
                   if (isResponse_Successful(response)){
                       userLocalStore.UserLogged(response.body().getContact_number());
                         onSuccess_(res);
                   }else {
                       Toast.makeText(Login_Page.this, "Something Gone Wrong", Toast.LENGTH_SHORT).show();
                   }
                   username_ed.setText(""); password_ed.setText("");
               }
               @Override
               public void onFailure(Call<UserModel> call, Throwable t) {

               }
           });

     }

     private  void onSuccess_(UserModel userModel){
        Intent  intent = new Intent(getApplicationContext(), HomePage.class);
         intent.putExtra("number",userModel.getContact_number());
         Toast.makeText(this,"Logged in Successfully..!", Toast.LENGTH_SHORT).show();
         startActivity(intent);
     }



    public static boolean isResponse_Successful(Response<UserModel> response ){

         UserModel  res = response.body();
         return   (response.isSuccessful() && res.getUser_check().equals("1")  ?  true  :  false);

     }





    private  void  validate_firm(TextInputLayout  lt,String  error){
        lt.setErrorEnabled(true);
        lt.setError(error);
    }




}