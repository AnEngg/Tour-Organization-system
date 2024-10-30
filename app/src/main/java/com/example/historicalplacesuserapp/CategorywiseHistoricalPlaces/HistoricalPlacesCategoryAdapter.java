package com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoricalPlacesCategoryAdapter extends BaseAdapter {
    List<POJOHistoricalPlacesCategory> list;
    Activity activity;
    TextView tv_no_record;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public HistoricalPlacesCategoryAdapter(List<POJOHistoricalPlacesCategory> list, Activity activity, TextView tv_no_record) {
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
        final ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lv_historical_places_category, null);

            holder.img_category = view.findViewById(R.id.lv_img_category_image);
            holder.tv_name = view.findViewById(R.id.lv_tv_category_name);
            holder.cv_categorywise_historical_places = view.findViewById(R.id.lv_cv_categorywise_historical_places);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final POJOHistoricalPlacesCategory obj = list.get(position);
        holder.tv_name.setText(obj.getName());


        Picasso.with(activity).load(Config.OnlineImageAddress + ""+obj.getImage()).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(holder.img_category);

        holder.cv_categorywise_historical_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, CategorywiseHistoricalPlacesListActivity.class);
                intent.putExtra("category_name", obj.getName());
                activity.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView img_category;
        TextView tv_name;
        CardView cv_categorywise_historical_places;
    }
}