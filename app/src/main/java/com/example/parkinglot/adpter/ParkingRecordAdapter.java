package com.example.parkinglot.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.parkinglot.R;
import com.example.parkinglot.pojo.ParkingRecord;

import java.util.List;

public class ParkingRecordAdapter extends ArrayAdapter<ParkingRecord> {
    private int resourceId;

    public ParkingRecordAdapter(@NonNull Context context, int resource, @NonNull List<ParkingRecord> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);


        ParkingRecord record = getItem(position);
        TextView plateNumber = convertView.findViewById(R.id.record_item_plateNumber);
        TextView entry = convertView.findViewById(R.id.record_item_entry);
        TextView out = convertView.findViewById(R.id.record_item_out);
        TextView money = convertView.findViewById(R.id.record_item_money);
        TextView name = convertView.findViewById(R.id.record_item_name);

        plateNumber.setText(record.getPlateNumber());
        name.setText(record.getParkName());
        entry.setText(record.getEntryTime());
        out.setText(record.getOutTime());
        money.setText(record.getMonetary());


        return convertView;
    }
}
