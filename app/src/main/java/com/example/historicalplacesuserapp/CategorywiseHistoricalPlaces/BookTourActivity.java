package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookTourActivity extends AppCompatActivity {

    TextView tv_book_tour_package_name,tv_book_tour_overview,tv_book_tour_features,tv_book_tour_package_cost,tv_no_record;
    Button btn_view_place_in_details_book_tour_package;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tour);


        preferences = PreferenceManager.getDefaultSharedPreferences(BookTourActivity.this);
        editor = preferences.edit();
        setTitle(preferences.getString("place_name",""));
        tv_book_tour_package_name = findViewById(R.id.tv_book_tour_package_name);
        tv_book_tour_overview = findViewById(R.id.tv_book_tour_overview);
        tv_book_tour_features = findViewById(R.id.tv_book_tour_features);
        tv_book_tour_package_cost = findViewById(R.id.tv_book_tour_package_cost);
        tv_no_record = findViewById(R.id.tv_no_record);
        btn_view_place_in_details_book_tour_package = findViewById(R.id.btn_view_place_in_details_book_tour_package);

        progressDialog = new ProgressDialog(BookTourActivity.this);
        progressDialog.setTitle("Tour Package Loading");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        getPackageTourInformation();

    }

    private void getPackageTourInformation() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("place_id",preferences.getString("place_id",""));
        client.post(Config.urlGetPlacePackage, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("getPlacePackage");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String place_id = jsonObject.getString("place_id");
                        String package_title  = jsonObject.getString("package_title");
                        String package_description  = jsonObject.getString("package_description");
                        String package_features  = jsonObject.getString("package_features");
                        String packages_cost  = jsonObject.getString("packages_cost");

                        tv_book_tour_package_name.setText(package_title);
                        tv_book_tour_overview.setText(package_description);
                        tv_book_tour_features.setText(package_features);
                        tv_book_tour_package_cost.setText(packages_cost);

                        btn_view_place_in_details_book_tour_package.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(BookTourActivity.this, PaidMoneyActivity.class);
                                editor.putString("place_id",place_id).commit();
                                startActivity(i);
                                finish();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(BookTourActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}