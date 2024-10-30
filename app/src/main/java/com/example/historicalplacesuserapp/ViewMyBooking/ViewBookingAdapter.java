package com.example.historicalplacesuserapp.ViewMyBooking;

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
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.CategorywiseHistoricalPlacesListActivity;
import com.example.historicalplacesuserapp.CategorywiseHistoricalPlaces.POJOHistoricalPlacesCategory;
import com.example.historicalplacesuserapp.Comman.Config;
import com.example.historicalplacesuserapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewBookingAdapter extends BaseAdapter {
    List<POJOViewMyBooking> list;
    Activity activity;
    TextView tv_no_record;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ViewBookingAdapter(List<POJOViewMyBooking> list, Activity activity, TextView tv_no_record) {
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
            view = inflater.inflate(R.layout.lv_view_booking_and_receipt, null);

            holder.lv_tv_booking_place_title = view.findViewById(R.id.lv_tv_booking_place_title);
            holder.lv_tv_view_booking_place_description = view.findViewById(R.id.lv_tv_view_booking_place_description);
            holder.lv_view_booking_place_features = view.findViewById(R.id.lv_view_booking_place_features);
            holder.lv_view_booking_place_paid_amount = view.findViewById(R.id.lv_view_booking_place_paid_amount);
            holder.lv_view_booking_place_features = view.findViewById(R.id.lv_view_booking_place_features);
            holder.lv_view_booking_place_date_time = view.findViewById(R.id.lv_view_booking_place_date_time);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final POJOViewMyBooking obj = list.get(position);
        holder.lv_tv_booking_place_title.setText(obj.getPackage_title());
        holder.lv_tv_view_booking_place_description.setText(obj.getPackage_description());
        holder.lv_view_booking_place_features.setText(obj.getPackage_features());
        holder.lv_view_booking_place_paid_amount.setText(obj.getAmount());
        holder.lv_view_booking_place_date_time.setText(obj.getDate_time());

        Toast.makeText(activity, ""+obj.getPackage_title(), Toast.LENGTH_SHORT).show();

        return view;
    }

    class ViewHolder {
        TextView lv_tv_booking_place_title,lv_tv_view_booking_place_description,lv_view_booking_place_features
                ,lv_view_booking_place_paid_amount,lv_view_booking_place_date_time;

    }
}