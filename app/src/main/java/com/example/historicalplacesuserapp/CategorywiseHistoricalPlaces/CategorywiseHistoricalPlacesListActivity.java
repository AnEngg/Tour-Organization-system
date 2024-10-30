package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CategorywiseHistoricalPlacesListActivity extends AppCompatActivity {

    List<POJOCategorywiseHistoricalPlaces> list;
    CategorywiseHistoricalPlacesAdapter adapter;
    SearchView searchView_cateorywise_places;
    TextView tv_no_record;
    ListView lv_categorywise_historical_places;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String category_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorywise_historical_places_list);
        preferences = PreferenceManager.getDefaultSharedPreferences(CategorywiseHistoricalPlacesListActivity.this);
        editor = preferences.edit();

        list = new ArrayList<>();
        searchView_cateorywise_places = findViewById(R.id.searchview_place_by_name_or_city);
        tv_no_record = findViewById(R.id.tv_no_record);
        lv_categorywise_historical_places = findViewById(R.id.lv_all_categorywise_historical_places);
        progress = findViewById(R.id.progress);

        Intent intent = getIntent();
        category_name = intent.getStringExtra("category_name");

        searchView_cateorywise_places.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCategorywisePlaces(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCategorywisePlaces(newText);
                return false;
            }
        });

        getCategorywiseHistoricalPlaces();
    }

    private void searchCategorywisePlaces(String query) {

        List<POJOCategorywiseHistoricalPlaces> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (POJOCategorywiseHistoricalPlaces d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new CategorywiseHistoricalPlacesAdapter(tempcenterlist, CategorywiseHistoricalPlacesListActivity.this, tv_no_record);
        lv_categorywise_historical_places.setAdapter(adapter);
    }

    private void getCategorywiseHistoricalPlaces() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("category",category_name);
        client.post(Config.urlGetCategorywiseHistoricalPlaces, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getCategorywiseHistoricalPlaces");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String image = jsonObject.getString("image");
                        String name = jsonObject.getString("name");
                        String category = jsonObject.getString("category");
                        String address = jsonObject.getString("address");
                        String latitude = jsonObject.getString("latitude");
                        String longitude = jsonObject.getString("longitude");
                        String features = jsonObject.getString("features");
                        String description = jsonObject.getString("description");
                        String video_url = jsonObject.getString("video_url");
                        String parking_available = jsonObject.getString("parking_available");
                        String charges = jsonObject.getString("charges");
                        String special_rules = jsonObject.getString("special_rules");
                        String city = jsonObject.getString("city");
                        String account_no = jsonObject.getString("account_no");
                        String ifsc_code = jsonObject.getString("ifsc_code");
                        String branch_code = jsonObject.getString("branch_code");

                        list.add(new POJOCategorywiseHistoricalPlaces(id,image,name,category,address,latitude,longitude,features,description,video_url,parking_available,
                                charges,special_rules,city,account_no,ifsc_code,branch_code));
                    }
                    adapter = new CategorywiseHistoricalPlacesAdapter(list, CategorywiseHistoricalPlacesListActivity.this, tv_no_record);
                    lv_categorywise_historical_places.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CategorywiseHistoricalPlacesListActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}