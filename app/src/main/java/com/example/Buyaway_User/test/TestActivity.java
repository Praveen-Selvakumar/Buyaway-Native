package com.example.Buyaway_User.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Buyaway_User.Adapters.Category_Horizontal_Adapter;
import com.example.Buyaway_User.Adapters.NewProductsAdapter;
import com.example.Buyaway_User.Adapters.SlidingImage_Adapter;
import com.example.Buyaway_User.Common.RetrofitAPIClient;
import com.example.Buyaway_User.Common.UserLocalStore;
import com.example.Buyaway_User.Common.Utils;
import com.example.Buyaway_User.DOA.CommonRetroAPI_Interface;
import com.example.Buyaway_User.DOA.TextWatchers;
import com.example.Buyaway_User.Model.BannerModel;
import com.example.Buyaway_User.Model.CatModelList;
import com.example.Buyaway_User.Model.NewProductModel;
import com.example.Buyaway_User.Model.UserModel;
import com.example.Buyaway_User.R;
import com.example.Buyaway_User.login_pack.ProfilePage;
import com.example.Buyaway_User.login_pack.Register_Page;
import com.example.Buyaway_User.mainFrame.EditProfileImage;
import com.example.Buyaway_User.mainFrame.HomePage;
import com.fxn.pix.Pix;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

}