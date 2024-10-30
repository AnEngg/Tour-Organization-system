package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.Comman.DashSpan;
import com.example.historicalplacesuserapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PaidMoneyActivity extends AppCompatActivity {

    EditText edt_amount,edt_card_holder_name,edt_card_number,edt_expiry_date,edt_cvv;
    Button btn_payment;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_money);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getSupportActionBar().setTitle("Payments");
        getSupportActionBar().setSubtitle("To Pay "+getIntent().getStringExtra("dish_rate"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.purple_700)));
            getSupportActionBar().getThemedContext();
        }

        edt_amount = findViewById(R.id.edt_amount);
        edt_card_holder_name = findViewById(R.id.edt_card_holder_name);
        edt_card_number = findViewById(R.id.edt_card_number);
        edt_expiry_date = findViewById(R.id.edt_expiry_date);
        edt_cvv = findViewById(R.id.edt_cvv);


        edt_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Object[] paddingSpans = editable.getSpans(0, editable.length(), DashSpan.class);
                for (Object span : paddingSpans) {
                    editable.removeSpan(span);
                }

                addSpans(editable);
            }

            private static final int GROUP_SIZE = 4;

            private void addSpans(Editable editable) {

                final int length = editable.length();
                for (int i = 1; i * (GROUP_SIZE) < length; i++) {
                    int index = i * GROUP_SIZE;
                    editable.setSpan(new DashSpan(), index - 1, index,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        });

        edt_expiry_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len=s.toString().length();

                if (before == 0 && len == 2)
                    edt_expiry_date.append("/");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_payment = findViewById(R.id.btn_payment);
        progress = findViewById(R.id.progress);

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_card_holder_name.getText().toString().isEmpty())
                {

                    edt_card_holder_name.setError("Enter Card  Holder Name");
                }
                else if (edt_amount.getText().toString().isEmpty())
                {
                    edt_amount.setError("Enter Amount for Donation");
                }
                else if (edt_card_number.getText().toString().isEmpty())
                {
                    edt_card_number.setError("Enter Card Number");
                }
                else if (edt_expiry_date.getText().toString().isEmpty())
                {
                    edt_expiry_date.setError("Enter Expiry Date");
                }
                else if (edt_cvv.getText().toString().isEmpty())
                {
                    edt_cvv.setError("Enter Valid CVV");
                }
                else
                {
                    payment();
                }
            }
        });
    }

    private void payment()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",preferences.getString("username",""));
        params.put("amount",edt_amount.getText().toString());
        params.put("place_id",preferences.getString("place_id",""));
        params.put("card_holder_name",edt_card_holder_name.getText().toString());
        params.put("card_no",edt_card_number.getText().toString());
        params.put("card_expiry_date",edt_expiry_date.getText().toString());
        params.put("cvv_no",edt_cvv.getText().toString());

        client.post(Config.url_add_payment, params, new JsonHttpResponseHandler() {

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
                        startActivity(new Intent(PaidMoneyActivity.this,Thank_YouSActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(PaidMoneyActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(PaidMoneyActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

}