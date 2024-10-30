package com.example.historicalplacesuserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.CategorywiseHistoricalPlacesListActivity;
import com.example.historicalplacesuserapp.Comman.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    EditText edt_name,edt_mobile_no,edt_email_id,edt_address,edt_username, edt_password;
    Button btn_sign_up;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        edt_name = findViewById(R.id.edt_register_username);
        edt_mobile_no = findViewById(R.id.edt_register_mobile_no);
        edt_email_id = findViewById(R.id.edt_register_email_id);
        edt_address = findViewById(R.id.edt_register_address);
        edt_username = findViewById(R.id.edt_register_username);
        edt_password = findViewById(R.id.edt_register_password);
        btn_sign_up = findViewById(R.id.btn_register_signup);
        progress = findViewById(R.id.progress);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_name.getText().toString().isEmpty())
                {
                    edt_name.setError("Please Enter Valid Name");
                }
                if (edt_mobile_no.getText().toString().isEmpty())
                {
                    edt_mobile_no.setError("Please Enter Valid Mobile Number");
                }
                if (edt_mobile_no.getText().toString().length()!= 10)
                {
                    edt_mobile_no.setError("Please Enter 10 Digit Mobile No");
                }
                if (edt_email_id.getText().toString().isEmpty())
                {
                    edt_email_id.setError("Please Enter Valid Email Id");
                }
                if (!edt_email_id.getText().toString().contains("@") || !edt_email_id.getText().toString().contains(".com"))
                {
                    edt_email_id.setError("Please Enter Valid Email Id");
                }
                if (edt_address.getText().toString().isEmpty())
                {
                    edt_address.setError("Please Enter Valid Address");
                }
                if (edt_username.getText().toString().isEmpty())
                {
                    edt_username.setError("Please Enter Valid Username");
                }
                else if (edt_password.getText().toString().isEmpty())
                {
                    edt_password.setError("Please Enter Valid Password");
                }
                else
                {
                    registration();
                }
            }
        });

    }

    private void registration()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",edt_name.getText().toString());
        params.put("mobile_no",edt_mobile_no.getText().toString());
        params.put("email_id",edt_email_id.getText().toString());
        params.put("address",edt_address.getText().toString());
        params.put("username",edt_username.getText().toString());
        params.put("password",edt_password.getText().toString());

        client.post(Config.url_register,params,new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progress.setVisibility(View.GONE);

                try {
                    String aa = response.getString("success");
                    if (aa.equals("1"))
                    {
                        editor.putBoolean("islogin",true).commit();
                        editor.putString("username",edt_username.getText().toString()).commit();
                        Toast.makeText(RegistrationActivity.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(RegistrationActivity.this, "Colud Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new  Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}