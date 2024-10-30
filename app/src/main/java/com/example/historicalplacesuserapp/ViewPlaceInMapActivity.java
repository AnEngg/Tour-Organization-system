package com.example.historicalplacesuserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.CategorywiseHistoricalPlacesListActivity;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.ViewPlacesInDetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewPlaceInMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private GoogleMap map;
    private final float DEFAULT_ZOOM = 16;

    private MarkerOptions hostel_marker;

    String address;
    double latitude,longitude;

    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place_in_map);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        setTitle(""+preferences.getString("address","")+" Location");
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        address = preferences.getString("address","");
        latitude = Double.parseDouble(preferences.getString("latitude",""));
        longitude = Double.parseDouble(preferences.getString("longitude",""));
        Toast.makeText(this, "Latitude is "+latitude+"Longitude is " +longitude, Toast.LENGTH_SHORT).show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hostel_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().isCompassEnabled();
        map.getUiSettings().isZoomGesturesEnabled();
        map.getUiSettings().isZoomControlsEnabled();



        //Initialize Google Play Serviceso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                map.setMyLocationEnabled(true);
            }

            LatLng latLng = new LatLng(latitude,longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
            hostel_marker = new MarkerOptions().position(latLng).title(String.valueOf("Place Address: "+address));
            hostel_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.place_location_icon));
            map.addMarker(hostel_marker);
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new  Intent(ViewPlaceInMapActivity.this, CategorywiseHistoricalPlacesListActivity.class);
        editor.putString("category",category);
        finish();
    }
}