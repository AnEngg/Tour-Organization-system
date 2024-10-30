package com.example.historicalplacesuserapp.CitywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.CategorywiseHistoricalPlacesAdapter;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.POJOCategorywiseHistoricalPlaces;
import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class CitywiseHistoricalPlacesActivity extends AppCompatActivity {

    List<POJOCategorywiseHistoricalPlaces> list;
    CategorywiseHistoricalPlacesAdapter adapter;
    SearchView searchView_cateorywise_places;
    TextView tv_no_record;
    ListView lv_categorywise_historical_places;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<String> arrayIdList, arrayCityList;
    Spinner spinner_city_list;

    int checkstate = 0;
    String user_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citywise_historical_places);
        preferences = PreferenceManager.getDefaultSharedPreferences(CitywiseHistoricalPlacesActivity.this);
        editor = preferences.edit();

        setTitle("Citywise Places");

        list = new ArrayList<>();
        spinner_city_list = findViewById(R.id.spinner_select_city);
        searchView_cateorywise_places = findViewById(R.id.searchview_place_by_name_or_cateogry_or_city);
        tv_no_record = findViewById(R.id.tv_no_record);
        lv_categorywise_historical_places = findViewById(R.id.lv_all_places);
        progress = findViewById(R.id.progress);

        arrayIdList = new ArrayList<>();
        arrayCityList = new ArrayList<>();
        getCity();

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
            if (d.getName().toUpperCase().contains(query.toUpperCase()) || d.getCategory().toUpperCase().contains(query.toUpperCase()) ||
                    d.getCity().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new CategorywiseHistoricalPlacesAdapter(tempcenterlist, CitywiseHistoricalPlacesActivity.this, tv_no_record);
        lv_categorywise_historical_places.setAdapter(adapter);
    }

    private void getCity() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.urlGetCity,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("getCity");
                            arrayCityList.clear();
                            arrayIdList.add("-1");
                            arrayCityList.add("Select Your City");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject getlistObject = jsonArray.getJSONObject(i);

                                String city = getlistObject.getString("city");
                                arrayCityList.add(city);

                            }
                            @SuppressLint("UseRequireInsteadOfGet") ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(CitywiseHistoricalPlacesActivity.this), android.R.layout.simple_spinner_item, arrayCityList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_city_list.setAdapter(adapter);
                            spinner_city_list.setVisibility(View.VISIBLE);

                            spinner_city_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (checkstate++ > 0) {
                                        try {
                                            list.clear();
                                            getCitywisetPlaces(arrayCityList.get(position));
                                            Toast.makeText(CitywiseHistoricalPlacesActivity.this, ""+arrayCityList.get(position), Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(CitywiseHistoricalPlacesActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                                            Log.i("Error in State Spinner", e.toString());
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CitywiseHistoricalPlacesActivity.this, "Places  Exception" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CitywiseHistoricalPlacesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(CitywiseHistoricalPlacesActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getCitywisetPlaces(String city) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("city",city);
        client.post(Config.urlGetCitywiseHistoricalPlaces, params, new JsonHttpResponseHandler() {

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
                    JSONArray jsonArray = response.getJSONArray("getCitywiseHistoricalPlaces");
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
                    adapter = new CategorywiseHistoricalPlacesAdapter(list, CitywiseHistoricalPlacesActivity.this, tv_no_record);
                    lv_categorywise_historical_places.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CitywiseHistoricalPlacesActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategorywiseHistoricalPlaces() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Config.urlGetAllHistoricalPlaces, params, new JsonHttpResponseHandler() {

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
                    JSONArray jsonArray = response.getJSONArray("getAllHistoricalPlaces");
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
                    adapter = new CategorywiseHistoricalPlacesAdapter(list, CitywiseHistoricalPlacesActivity.this, tv_no_record);
                    lv_categorywise_historical_places.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CitywiseHistoricalPlacesActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}