package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.historicalplacesuserapp.HomeActivity;
import com.example.historicalplacesuserapp.R;

public class Thank_YouSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_sactivity);

        setTitle("Thank You");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Thank_YouSActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }
}