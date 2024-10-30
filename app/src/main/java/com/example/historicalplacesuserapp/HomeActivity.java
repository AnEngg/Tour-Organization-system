package com.example.historicalplacesuserapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.HistoricalPlacesCategoryActivity;
import com.example.historicalplacesuserapp.CitywiseHistoricalPlaces.CitywiseHistoricalPlacesActivity;
import com.example.historicalplacesuserapp.MyProfile.MyProfileActivity;
import com.example.historicalplacesuserapp.ViewMyBooking.ViewMyBookingActivity;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CardView cardview11,cardview22,cardview33,cardview44,cardView55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        cardview11 = findViewById(R.id.cardview11);
        cardview22 = findViewById(R.id.cardview22);
        cardview33 = findViewById(R.id.cardview33);
        cardview44 = findViewById(R.id.cardview44);
        cardView55 = findViewById(R.id.cardview55);


        cardview11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, HistoricalPlacesCategoryActivity.class));
            }
        });

        cardview22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, CitywiseHistoricalPlacesActivity.class));
            }
        });

        cardview33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, MyProfileActivity.class));
            }
        });

        cardview44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });

        cardView55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewMyBookingActivity.class));
            }
        });



        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firsttime = sharedPreferences.getBoolean("firsttime",true);

        if (firsttime)
        {
            welcome();
        }
    }

    private void welcome() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Tour Organization App");
        ad.setIcon(R.drawable.logo);
        ad.setMessage("Welcome to User App of Tour Organization App");
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firsttime",false);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    public void logout()
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Logout");
        ad.setMessage("Are You Want To Logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("islogin",false).commit();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
            }
        });

        Dialog dialog = ad.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}