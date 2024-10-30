package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.PermissionActivity;
import com.example.historicalplacesuserapp.R;
import com.squareup.picasso.Picasso;

public class ViewPlacesInDetailsActivity extends AppCompatActivity {


    ImageView img_place_image;
    TextView tv_name,tv_category,tv_address,tv_features,tv_description,tv_parking_available,tv_charges,tv_special_rules,
            tv_city;

    Button btn_view_place_in_map, btn_book_tour;
    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String place_id,image,name,category,address,latitude,longitude,features,description,parking_available,charges,special_rules,city;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_places_in_details);

        preferences = PreferenceManager.getDefaultSharedPreferences(ViewPlacesInDetailsActivity.this);
        editor = preferences.edit();

        img_place_image = findViewById(R.id.img_view_place_in_details_profile);
        tv_name = findViewById(R.id.tv_view_place_in_details_name);
        tv_category = findViewById(R.id.tv_view_place_in_details_place_category);
        tv_address = findViewById(R.id.tv_view_place_in_details_place_address);
        tv_features = findViewById(R.id.tv_view_place_in_details_place_features);
        tv_description = findViewById(R.id.tv_view_place_in_details_place_description);
        tv_parking_available = findViewById(R.id.tv_view_place_in_details_place_parking_available);
        tv_charges = findViewById(R.id.tv_view_place_in_details_place_parking_charges);
        tv_special_rules = findViewById(R.id.tv_view_place_in_details_place_special_rules);
        tv_city = findViewById(R.id.tv_view_place_in_details_place_city);
        
        place_id = preferences.getString("id","");
        image = preferences.getString("image","");
        name = preferences.getString("name","");
        category = preferences.getString("category","");
        address = preferences.getString("address","");
        longitude = preferences.getString("latitude","");
        latitude = preferences.getString("longitude","");
        features = preferences.getString("features","");
        description = preferences.getString("description","");
        parking_available = preferences.getString("parking_available","");
        charges = preferences.getString("charges","");
        special_rules = preferences.getString("special_rules","");
        city = preferences.getString("city","");

        Picasso.with(ViewPlacesInDetailsActivity.this).load(Config.OnlineImageAddress + "" + image).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(img_place_image);

        setTitle("" + name);

        tv_name.setText(name);
        tv_category.setText(category);
        tv_address.setText(address);
        tv_features.setText(features);
        tv_description.setText(description);
        tv_parking_available.setText(parking_available);
        tv_charges.setText("\u20A8 "+charges);
        tv_special_rules.setText(special_rules);
        tv_city.setText(city);

        btn_view_place_in_map = findViewById(R.id.btn_view_place_in_details_in_google_map);
        btn_book_tour = findViewById(R.id.btn_view_place_in_details_book_tour_);
        progress = findViewById(R.id.progress);

        btn_view_place_in_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                Intent i = new Intent(ViewPlacesInDetailsActivity.this, PermissionActivity.class);
                editor.putString("category",category).commit();
                editor.putString("address",address).commit();
                editor.putString("latitude",latitude).commit();
                editor.putString("longitude",longitude).commit();
                startActivity(i);
                finish();
            }
        });

        btn_book_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPlacesInDetailsActivity.this, BookTourActivity.class);
                editor.putString("place_id",place_id).commit();
                editor.putString("place_name",name).commit();
                startActivity(i);
                finish();
            }
        });

    }
}