package com.kcasareo.beaconService.beacons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcasareo.beaconService.beacons.bluetooth.SignalData;
import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;
import com.kcasareo.ranging.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 7/06/2017.
 */

public class BeaconAdapter extends BaseAdapter {
    private final ArrayList mData;

    public BeaconAdapter(SignalData data) {
        mData = new ArrayList();
        mData.addAll(data.asMap().entrySet());
    }

    public void set(SignalData data) {
        mData.clear();
        mData.addAll(data.asMap().entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, SignalDatum> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        Map.Entry<String,SignalDatum> entry = (Map.Entry) mData.get(position);
        return Long.parseLong(entry.getKey());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.beacon_adapter_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, SignalDatum> item = getItem(position);

        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue().toString());
        return result;
    }
}
