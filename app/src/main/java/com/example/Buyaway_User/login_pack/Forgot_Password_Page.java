package com.example.Buyaway_User.login_pack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password_Page extends AppCompatActivity {

    @BindView(R.id.forgot_password_lt)
    RelativeLayout forgot_password_lt;
    ProgressDialog progressDialog;

    @BindView(R.id.return_to_login_btn_fp)
    TextView sign_in_btn;
    @BindView(R.id.reset_password_btn)
    TextView reset_password_btn;
    @BindView(R.id.phone_number_ed)
    TextInputEditText phone_number_ed;
    @BindView(R.id.ph_num_lt)
    TextInputLayout phone_number_lt;
    CommonRetroAPI_Interface api_interface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);
        ButterKnife.bind(Forgot_Password_Page.this);
        forgot_password_lt.setVisibility(View.VISIBLE);
        IntializeFields();


        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                 String get_num = phone_number_ed.getText().toString();
                if (get_num.isEmpty()){
                    progressDialog.cancel();
                    phone_number_lt.setErrorEnabled(true);
                    phone_number_lt.setError("Kindly Enter your phone number");
                }else {
                    sendPassword(get_num);
                }
            }
        });


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgot_Password_Page.this,Login_Page.class);
                startActivity(intent);
            }
        });

    }

    private void IntializeFields() {
        progressDialog =  new ProgressDialog(Forgot_Password_Page.this);
        progressDialog.setMessage("Please wait While Loading.....");
        api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void sendPassword(String get_num) {
        Call<UserModel> call =  api_interface.forgot_password(get_num);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                progressDialog.cancel();
                   UserModel res = response.body();
                   if (response.isSuccessful()){
                       Toast.makeText(Forgot_Password_Page.this, "New Password has been sent to your E-mail Address" , Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(Forgot_Password_Page.this,"New Password has been sent to your E-mail Address", Toast.LENGTH_SHORT).show();
                   }
                phone_number_ed.setText("");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private  void onSuccess_(UserModel userModel){
        Intent  intent = new Intent(getApplicationContext(), Login_Page.class);
        intent.putExtra("number",userModel.getContact_number());
        startActivity(intent);
    }

    public static boolean isResponse_Successful(Response<UserModel> response ){

        UserModel  res = response.body();
        return   (response.isSuccessful() && res.getStatus().equals("Sucesss")  ?  true  :  false);

    }
}
