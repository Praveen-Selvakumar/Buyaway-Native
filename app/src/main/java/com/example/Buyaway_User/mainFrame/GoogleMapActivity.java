package com.example.Buyaway_User.mainFrame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Buyaway_User.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment  mapFragment ;
    GoogleMap map;
    Marker  marker;
    CircleImageView back_bg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        mapFragment =  (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
        back_bg = (CircleImageView)findViewById(R.id.back_bg);
        /*
        *        mapFragment = supportFragmentManager.findFragmentById(R.id.mapNearBy)  as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        * */

        back_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveOnBack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       moveOnBack();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map  =  googleMap;

        MarkerOptions markerOptions =  new MarkerOptions();
        Double lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        Double lng = Double.parseDouble(getIntent().getStringExtra("lng"));
        Log.d("lat",getIntent().getStringExtra("lat").toString());
        Log.d("lat",getIntent().getStringExtra("lng").toString());
        LatLng latLng = new LatLng(lat,lng);
        markerOptions.position(latLng);
        markerOptions.title(getIntent().getStringExtra("store_name"));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        marker = map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(10f));
        marker.showInfoWindow();

    }

    private void moveOnBack(){
        Intent intent = new Intent(GoogleMapActivity.this, HomePage.class);
        startActivity(intent);
    }


}