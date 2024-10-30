package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategorywiseHistoricalPlacesAdapter extends BaseAdapter {
    List<POJOCategorywiseHistoricalPlaces> list;
    Activity activity;
    TextView tv_no_record;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public CategorywiseHistoricalPlacesAdapter(List<POJOCategorywiseHistoricalPlaces> list, Activity activity, TextView tv_no_record) {
        this.list = list;
        this.activity = activity;
        this.tv_no_record = tv_no_record;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final CategorywiseHistoricalPlacesAdapter.ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            holder = new CategorywiseHistoricalPlacesAdapter.ViewHolder();
            view = inflater.inflate(R.layout.lv_categorywise_historical_places, null);

            holder.img_place_image = view.findViewById(R.id.lv_img_historical_places_profile);
            holder.tv_name = view.findViewById(R.id.rv_tv_historical_places_name);
            holder.tv_category = view.findViewById(R.id.rv_tv_historical_places_category);
            holder.tv_address = view.findViewById(R.id.rv_tv_historical_places_address);
            holder.parking_available = view.findViewById(R.id.rv_tv_historical_places_parkig_available);
            holder.tv_charges = view.findViewById(R.id.rv_tv_historical_places_charges);
            holder.btn_view_in_details = view.findViewById(R.id.rv_btn_view_in_details);

            view.setTag(holder);
        } else {
            holder = (CategorywiseHistoricalPlacesAdapter.ViewHolder) view.getTag();
        }

        final POJOCategorywiseHistoricalPlaces obj = list.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_category.setText(obj.getCategory());
        holder.tv_address.setText(obj.getAddress());
        holder.parking_available.setText(obj.getParking_available());
        holder.tv_charges.setText("\u20A8 "+obj.getCharges());



        Picasso.with(activity).load(Config.OnlineImageAddress + ""+obj.getImage()).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(holder.img_place_image);

        holder.btn_view_in_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ViewPlacesInDetailsActivity.class);
                editor.putString("id", obj.getId()).commit();
                editor.putString("image", obj.getImage()).commit();
                editor.putString("name", obj.getName()).commit();
                editor.putString("category", obj.getCategory()).commit();
                editor.putString("address", obj.getAddress()).commit();
                editor.putString("latitude", obj.getLatitude()).commit();
                editor.putString("longitude", obj.getLongitude()).commit();
                editor.putString("features", obj.getFeatures()).commit();
                editor.putString("description", obj.getDescription()).commit();
                editor.putString("video_url", obj.getVideo_url()).commit();
                editor.putString("parking_available", obj.getParking_available()).commit();
                editor.putString("charges", obj.getCharges()).commit();
                editor.putString("special_rules", obj.getSpecial_rules()).commit();
                editor.putString("city", obj.getCity()).commit();
                editor.putString("account_no", obj.getAccount_no()).commit();
                editor.putString("ifsc_code", obj.getIfsc_code()).commit();
                editor.putString("branch_code", obj.getBranch_code()).commit();
                activity.startActivity(intent);
            }
        });


        return view;
    }

    class ViewHolder {
        ImageView img_place_image;
        TextView tv_name,tv_category,tv_address,parking_available,tv_charges;
        Button btn_view_in_details;
    }
}
