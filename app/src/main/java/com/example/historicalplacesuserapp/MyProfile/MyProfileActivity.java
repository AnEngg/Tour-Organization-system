package com.example.historicalplacesuserapp.MyProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
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

public class MyProfileActivity extends AppCompatActivity {

    ImageView img_my_profile, img_edit;
    TextView tv_my_profile_name, tv_my_profile_address,
            tv_my_profile_email_id, tv_my_profile_mobile_no, tv_my_profile_username;

    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setTitle("My Profile");
        preferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        editor = preferences.edit();
        Toast.makeText(MyProfileActivity.this, ""+preferences.getString("username",""), Toast.LENGTH_SHORT).show();

        img_my_profile = findViewById(R.id.img_my_profile_my_profile);
        img_edit = findViewById(R.id.img_my_profile_edit);
        tv_my_profile_name = findViewById(R.id.tv_my_profile_name);
        tv_my_profile_address = findViewById(R.id.tv_my_profile_address);
        tv_my_profile_email_id = findViewById(R.id.tv_my_profile_email_id);
        tv_my_profile_mobile_no = findViewById(R.id.tv_my_profile_mobile_no);
        tv_my_profile_username = findViewById(R.id.tv_my_profile_username);

        progressDialog = new ProgressDialog(MyProfileActivity.this);
        progressDialog.setTitle("User Information Loading....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        getMyProfile();
    }

    private void getMyProfile() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", preferences.getString("username", null));
        client.post(Config.urlGetMyProfile, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("getMyProfile");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String mobile_no = jsonObject.getString("mobile_no");
                                String email_id = jsonObject.getString("email_id");
                                String address = jsonObject.getString("address");
                                String username = jsonObject.getString("username");

                                Toast.makeText(MyProfileActivity.this, "Name is " + name, Toast.LENGTH_SHORT).show();

                                tv_my_profile_name.setText(name);
                                tv_my_profile_mobile_no.setText(mobile_no);
                                tv_my_profile_email_id.setText(email_id);
                                tv_my_profile_address.setText(address);
                                tv_my_profile_username.setText(username);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(MyProfileActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
                    }
                }

        );

    }

}