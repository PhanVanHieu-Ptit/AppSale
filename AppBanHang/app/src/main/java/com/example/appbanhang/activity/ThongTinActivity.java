package com.example.appbanhang.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.appbanhang.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.example.appbanhang.activity.databinding.ActivityThongTinBinding;

public class ThongTinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar toolbarThongtin;
//    private ActivityThongTinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        toolbarThongtin = findViewById(R.id.toolbarthongtin);
        ActionBar();

//        binding = ActivityThongTinBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarThongtin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
//        LatLng cuaHangPhanVanHieu = new LatLng(10.775685306151038, 106.65752817201003);
//        mMap.addMarker(new MarkerOptions().position(cuaHangPhanVanHieu).title("Trung tam dien thoai Phan Van Hieu").snippet("997 Âu Cơ, Tân Thạnh, Tân Phú, Thành phố Hồ Chí Minh, Việt Nam")).setIcon(BitmapDescriptorFactory.defaultMarker());
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(cuaHangPhanVanHieu).zoom(90).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap = googleMap;
        LatLng sun = new LatLng(16.080439, 108.220402);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sun,13));
        mMap.addMarker(new MarkerOptions()
                .title("Sun*")
                .snippet("Sun* vô đối")
                .position(sun));
    }
}