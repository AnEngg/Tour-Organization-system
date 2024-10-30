package com.example.historicalplacesuserapp.ViewMyBooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.HistoricalPlacesCategoryActivity;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.HistoricalPlacesCategoryAdapter;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.POJOHistoricalPlacesCategory;
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

public class ViewMyBookingActivity extends AppCompatActivity {

    List<POJOViewMyBooking> list;
    ViewBookingAdapter adapter;
    SearchView searchView_booking;
    TextView tv_no_record;
    ListView lv_view_booking;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_booking);
        preferences = PreferenceManager.getDefaultSharedPreferences(ViewMyBookingActivity.this);
        editor = preferences.edit();

        setTitle("View Booking and Receipt");
        list = new ArrayList<>();
        searchView_booking = findViewById(R.id.searchview_view_booking);
        tv_no_record = findViewById(R.id.tv_no_record);
        lv_view_booking = findViewById(R.id.lv_view_booking_tour);
        progress = findViewById(R.id.progress);

        searchView_booking.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooking(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBooking(newText);
                return false;
            }
        });

        getAllBooking();
    }

    private void searchBooking(String query) {

        List<POJOViewMyBooking> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (POJOViewMyBooking d : list) {
            if (d.getPackage_title().toUpperCase().contains(query.toUpperCase()) ||
                    d.getAmount().toUpperCase().contains(query.toUpperCase()) ||
                    d.getDate_time().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new ViewBookingAdapter(tempcenterlist, ViewMyBookingActivity.this, tv_no_record);
        lv_view_booking.setAdapter(adapter);
    }

    private void getAllBooking() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",preferences.getString("username",""));
        client.post(Config.urlGetBookingandReciepttourorganizationapp, params, new JsonHttpResponseHandler() {

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
                    JSONArray jsonArray = response.getJSONArray("getAllBooking");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String amount = jsonObject.getString("amount");
                        String card_holder_name = jsonObject.getString("card_holder_name");
                        String package_title = jsonObject.getString("package_title");
                        String package_description = jsonObject.getString("package_description");
                        String package_feature = jsonObject.getString("package_feature");
                        String package_cost = jsonObject.getString("package_cost");
                        String date_time = jsonObject.getString("date_time");

                        list.add(new POJOViewMyBooking(id, amount,card_holder_name,package_title,package_description,
                                package_feature,package_cost,date_time));
                    }
                    adapter = new ViewBookingAdapter(list, ViewMyBookingActivity.this, tv_no_record);
                    lv_view_booking.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ViewMyBookingActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}