package com.kcasareo.application.adapter;

import android.content.Context;
import android.util.Log;
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
    private final String TAG = "BeaconAdapter";
    private final ArrayList<HashMap.Entry<String,SignalDatum>> mData;
    //private LayoutInflater mInflater;
    public BeaconAdapter(SignalData data) {
        this();
        refresh(data);
    }
/*
    public BeaconAdapter(Context context, SignalData data) {
        this(data);
        mInflater = LayoutInflater.from(context);
    }
*/
    public BeaconAdapter() {
        mData = new ArrayList();
    }

    public void refresh(SignalData data) {
        mData.clear();
        mData.addAll(data.asMap().entrySet());
    }

    public void add(SignalData data) {
        // Empty the list, add new values, notify.
        // If data returned is null or the collection is empty, skip setting new values.
        HashMap<String, SignalDatum> temp = data.asMap();

        Log.i(TAG, "Set next data");
        Log.d(TAG, "Data size " + temp.size());
        if(temp.size() <= 0 )
            return;
        Log.i(TAG, "Clearing");
        for (HashMap.Entry<String, SignalDatum> entry : temp.entrySet()) {
            // With the SignalDatum override, this should ensure that a matching address is the method of evaluation.
            if(mData.contains(entry)) {
                // More equals abuse.
                mData.set(mData.indexOf(entry), entry);
            } else {
                // Add the entry if not found.
                mData.add(entry);
            }
        }
        Log.d(TAG, "Data Content:" + data.toString());
    }

    @Override
    public int getCount() {
        //Log.d(TAG, "Size: " + mData.size());
        return mData.size();
    }

    @Override
    public Map.Entry<String, SignalDatum> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i(TAG, "GetView");

        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.beacon_adapter_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, SignalDatum> item = getItem(position);
        //Log.d(TAG, "Item Contents: " + item.getKey());
        ((TextView) result.findViewById(R.id.signalAddress)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.signalData)).setText(item.getValue().toString());
        return result;
    }
}
