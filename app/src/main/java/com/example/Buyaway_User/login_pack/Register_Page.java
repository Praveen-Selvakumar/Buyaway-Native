package com.example.Buyaway_User.login_pack;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.UserHandle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.RegistrationAPI_Interface;
import com.example.Buyaway_User.Model.ResponseModel;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.RegisterUserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;

public class Register_Page  extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.register_page_content) RelativeLayout register_page_content;

    @BindView(R.id.return_to_login_btn)    TextView sign_in_btn;
    @BindView(R.id.sign_up_btn_reg)    TextView sign_up_btn;

    String gender_list[] = {"Male", "female"};
    private boolean isUserNameValid = false, isMailValid = false, isNumberValid = false, isGenderVali = false,
            isPwdValid = false, isConfPwdValid = false, isTotel = false;

    @BindView(R.id.gender_spinner_ed_reg)    MaterialBetterSpinner gender_spinner;

    @BindView(R.id.confirm_password_ed_reg)    TextInputEditText cofirm_pwd_ed;
    @BindView(R.id.password_ed_reg)    TextInputEditText pwd_ed;
    @BindView(R.id.user_mobile_number_ed_reg)    TextInputEditText phone_number_ed;
    @BindView(R.id.user_name_ed_reg)    TextInputEditText user_name_ed;
    @BindView(R.id.user_email_ed_reg)    TextInputEditText  user_email_ed;

    @BindView(R.id.user_name_lt_reg)    TextInputLayout user_name_lt;
    @BindView(R.id.user_mail_lt_reg)    TextInputLayout user_mail_lt;
    @BindView(R.id.user_mobile_number_lt)    TextInputLayout user_mobile_num_lt;
    @BindView(R.id.user_gender_lt)    TextInputLayout user_gender_lt;
    @BindView(R.id.user_pwd_lt)    TextInputLayout user_pwd_lt;
    @BindView(R.id.user_cf_pwd_lt)    TextInputLayout user_cf_pwd_lt;
    CommonRetroAPI_Interface apiInterface;
    TextInputLayout promo_code_lt ;
    TextInputEditText promo_code_ed ;
    RelativeLayout progress_lt ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);
        ButterKnife.bind(Register_Page.this);
        initilaizeFileds();


        register_page_content.setVisibility(View.VISIBLE);

        ArrayAdapter<String> number_adapter = new ArrayAdapter<String>(Register_Page.this,
                android.R.layout.simple_dropdown_item_1line, gender_list);
        gender_spinner.setAdapter(number_adapter);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_pwd = pwd_ed.getText().toString();
                String get_cnf_pwd = cofirm_pwd_ed.getText().toString();
                String get_name = user_name_ed.getText().toString();
                String get_mail = user_email_ed.getText().toString();
                String get_mobile = phone_number_ed.getText().toString();
                String get_gender = gender_spinner.getText().toString();

                if (   validate_firm(user_email_ed, "Enter Invoice Mail", "mail", user_mail_lt) |
                        validate_firm(user_name_ed, "Enter user name", "name", user_name_lt) |
                        validate_firm(phone_number_ed,"Enter Your mobile number","phone",user_mobile_num_lt) |
                        validate_firm(pwd_ed,"Enter Your Password","pwd",user_pwd_lt) |
                        validate_firm(promo_code_ed, "Enter your Promo Code","promo_code",promo_code_lt)) {
                         
                             register(get_mobile, get_mail, get_name, get_gender, get_pwd);


                    return;
                }
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Page.class);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private boolean validateUser_gender() {
        String get_gender = gender_spinner.getText().toString();
        if (get_gender.isEmpty()) {
            user_gender_lt.setErrorEnabled(true);
            user_gender_lt.setError("Enter Your Gender");
            return false;
        } else {
            user_gender_lt.setErrorEnabled(false);
            user_gender_lt.setError(null);
            return true;
        }
    }




    private void  register(String contact_number,String email,String name,String gender,String password){
        if (validate_promo_code(promo_code_ed)){
            progress_lt.setVisibility(View.VISIBLE);
            apiInterface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
            Call<UserModel> call = apiInterface.register_user(contact_number, email, name, gender, password);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()){
                         if (response.body().getMessage().equals("Success")){
                            Toast.makeText(Register_Page.this,response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                             progress_lt.setVisibility(View.GONE);
                          }else {
                             Toast.makeText(Register_Page.this,response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                             progress_lt.setVisibility(View.GONE);
                         }
                        startActivity(new Intent(Register_Page.this, Login_Page.class));
                    }else {
                        Toast.makeText(Register_Page.this,"Something gone  wrong try again later..! ", Toast.LENGTH_SHORT).show();
                        progress_lt.setVisibility(View.GONE);
                    }
                    progress_lt.setVisibility(View.GONE);

                }
                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });
         }else {
            promo_code_lt.setErrorEnabled(true);
            promo_code_lt.setError (" You're currently not on the invite list !");
        }


    }

    private void initilaizeFileds() {
        user_name_ed.setOnClickListener(Register_Page.this);
        user_email_ed.setOnClickListener(Register_Page.this);
        phone_number_ed.setOnClickListener(Register_Page.this);
        pwd_ed.setOnClickListener(Register_Page.this);
        cofirm_pwd_ed.setOnClickListener(Register_Page.this);
        promo_code_lt = (TextInputLayout)findViewById(R.id.promo_code_lt);
        promo_code_ed = (TextInputEditText) findViewById(R.id.promo_code_ed);
        progress_lt = (RelativeLayout)findViewById(R.id.progress_lt);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_name_ed_reg:
                user_name_lt.setErrorEnabled(false);
            case R.id.user_email_ed_reg:
                user_mail_lt.setErrorEnabled(false);
            case R.id.user_mobile_number_ed_reg:
                user_mobile_num_lt.setErrorEnabled(false);
            case R.id.password_ed_reg:
                user_pwd_lt.setErrorEnabled(false);
            case R.id.confirm_password_ed_reg:
                 user_cf_pwd_lt.setErrorEnabled(false);
        }
    }

    private boolean validate_firm(TextInputEditText ed, String status, String type,
                                  TextInputLayout lt) {
        String get = ed.getText().toString();
       if (type.equals("mail")) {
                if (!Patterns.EMAIL_ADDRESS.matcher(get).matches()) {
                lt.setErrorEnabled(true);
                lt.setError("Enter Valid E-mail");
            }
        }
        else if (get.isEmpty()) {
            lt.setErrorEnabled(true);
            lt.setError(status);
        } else {
            lt.setErrorEnabled(false);
            lt.setError(null);
        }
        return  get.isEmpty()?  false : true ;
     }

    private boolean validate_promo_code(TextInputEditText promo_code_ed)
    {         String get_code = promo_code_ed.getText().toString();
        boolean statement  = false;
        ArrayList<String>  list1 =  new ArrayList<>();
         list1.add("NWUSR10");
         list1.add("NCODR10");
         list1.add("NEUSR10");
         list1.add("NSWER10");
         list1.add("BBUYR10");
         list1.add("nwusr10");
         list1.add("ncodr10");
         list1.add("neusr10");
         list1.add("nswer10");
         list1.add("bbuyr10");

         if (!get_code.isEmpty()){
             statement = true;
             if (list1.contains(get_code)){
                 statement = true;
             }else {
                 statement = false;
             }
         }else {
             statement = false;
         }

        return statement;
    }


}
