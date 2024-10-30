package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
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

public class HistoricalPlacesCategoryActivity extends AppCompatActivity {

    List<POJOHistoricalPlacesCategory> list;
    HistoricalPlacesCategoryAdapter adapter;
    SearchView searchView_cateory;
    TextView tv_no_record;
    ListView lv_categorywise_historical_places;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_places_category);
        preferences = PreferenceManager.getDefaultSharedPreferences(HistoricalPlacesCategoryActivity.this);
        editor = preferences.edit();

        list = new ArrayList<>();
        searchView_cateory = findViewById(R.id.searchview_category);
        tv_no_record = findViewById(R.id.tv_no_record);
        lv_categorywise_historical_places = findViewById(R.id.lv_all_historical_place_category);
        progress = findViewById(R.id.progress);

        searchView_cateory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCategory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCategory(newText);
                return false;
            }
        });

        getHistoricalPlacesCategory();
    }

    private void searchCategory(String query) {

        List<POJOHistoricalPlacesCategory> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (POJOHistoricalPlacesCategory d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new HistoricalPlacesCategoryAdapter(tempcenterlist, HistoricalPlacesCategoryActivity.this, tv_no_record);
        lv_categorywise_historical_places.setAdapter(adapter);
    }

    private void getHistoricalPlacesCategory() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Config.urlGetHistoricalPlacesCategory, params, new JsonHttpResponseHandler() {

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
                    JSONArray jsonArray = response.getJSONArray("getHistoricalPlacesCategory");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String category_image = jsonObject.getString("category_image");
                        String category_name = jsonObject.getString("category_name");

                        list.add(new POJOHistoricalPlacesCategory(id, category_image,category_name));
                    }
                    adapter = new HistoricalPlacesCategoryAdapter(list, HistoricalPlacesCategoryActivity.this, tv_no_record);
                    lv_categorywise_historical_places.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HistoricalPlacesCategoryActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}