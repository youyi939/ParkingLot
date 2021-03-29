package com.example.parkinglot.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.parkinglot.R;
import com.example.parkinglot.pojo.Parking;

import java.util.List;

public class ParkingListViewAdapter extends ArrayAdapter<Parking> {
    private int resourceId;

    public ParkingListViewAdapter(@NonNull Context context, int resource, @NonNull List<Parking> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        Parking parking = getItem(position);
        TextView name = convertView.findViewById(R.id.parking_name);
        TextView address = convertView.findViewById(R.id.parking_address);
        TextView vacancy = convertView.findViewById(R.id.parking_vacancy);
        TextView rates = convertView.findViewById(R.id.parking_rates);


        name.setText(parking.getParkName());
        address.setText("地址："+parking.getAddress());
        vacancy.setText("空位："+parking.getVacancy());
        rates.setText("费率："+parking.getRates());

        return convertView;
    }


}
