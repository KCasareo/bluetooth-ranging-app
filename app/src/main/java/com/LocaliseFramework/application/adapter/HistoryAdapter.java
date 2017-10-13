package com.LocaliseFramework.application.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.LocaliseFramework.beaconService.beacons.bluetooth.SignalData;
import com.LocaliseFramework.beaconService.beacons.bluetooth.SignalDatum;
import com.LocaliseFramework.beaconService.location.Position;
import com.LocaliseFramework.ranging.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 28/09/2017.
 * HistoryAdapter object to be constructed at the view side.
 */

public class HistoryAdapter extends BaseAdapter {
    private final String TAG = "HistoryAdapter Adapter";
    private ArrayList<SignalData> signalDataHistory;
    private HashMap<String, Position> changeMap = new HashMap<>();
    private Position localised = new Position(0, 0, 0);
    private boolean localise;

    public interface OnDataChangedListener {
        public void onIndexDataChanged(int index);
    }

    OnDataChangedListener onDataChangedListener;

    public void setmOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public void updateLocal(Position position) {
        localised = position;
    }

    //private ArrayAdapter<SignalData> signalDataArrayAdapter;
    private int index;

    public HistoryAdapter() {
        signalDataHistory = new ArrayList<>();
        index = 0;
    }

    public void next() {
        if (index < signalDataHistory.size() - 1) {
            index++;
            onDataChangedListener.onIndexDataChanged(index);
        }

    }

    /* Update the position of the beacon */
    public HashMap<String, Position> transaction() {
        // Copy the old changes to safely clear the old one
        HashMap<String, Position> temp = new HashMap<>();
        temp.putAll(changeMap);
        changeMap.clear();
        return temp;
    }

    // Called
    public void prev() {
        if (index > 0) {
            index--;
            onDataChangedListener.onIndexDataChanged(index);
        }


    }

    public void last() {
        index = signalDataHistory.size() - 1;
        onDataChangedListener.onIndexDataChanged(index);
    }

    public int index() {
        return index;
    }



    // Add history
    // The latest history is retrieved using the the main activity's service connection.
    public void update(SignalData data) {
        signalDataHistory.add(data);
        signalDataHistory.get(signalDataHistory.size() -1).sort();
        //signalDataArrayAdapter.clear();
        //signalDataArrayAdapter.addAll(signalDataHistory);
    }

    public void refresh(SignalData data) {
        // Update
        update(data);
        // Set to last index
        last();
    }

    /* Get the data at frame X */
    //public SignalData read(int at) { return signalDataHistory.get(at).as; }

    //public SignalData showCurrent() { return read(index); }

    public String getCurrentAddress(int position) {
        return signalDataHistory.get(index).asArray().get(position).address();
    }

    //public void update(SignalData)

    @Override
    public int getCount() {
        return signalDataHistory.get(index).size();
    }

    @Override
    public SignalData getItem(int position) {
        return signalDataHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

        /*
    private final int minDelta = 300;
    private long focusTime = 0;
    private View focusTarget =null;

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            long t = System.currentTimeMillis();
            long delta = t - focusTime;
            if(hasFocus && delta > minDelta) {
                focusTime = t;
                focusTarget = v;
            } else {
                if (delta <= minDelta && v == focusTarget) {
                    focusTarget.post(new Runnable() {
                        @Override
                        public void run() {
                            focusTarget.requestFocus();
                        }
                    });
                }
            }
        }
    };*/
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        Log.d(TAG, "Get View");

        if(convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter_item, parent, false);
        } else {
            result = convertView;
        }
        result.clearFocus();

        final SignalDatum item = signalDataHistory.get(index).asArray().get(position);
        Log.d(TAG, "Text Edit Address: " + item.address());
        ((TextView) result.findViewById(R.id.history_item_address)).setText(item.address());
        Log.d(TAG, "Text Edit Distance: " + item.distance());
        ((TextView) result.findViewById(R.id.history_item_distance)).setText(String.format("%.2fm", item.distance()));
        ((TextView) result.findViewById(R.id.history_item_db)).setText(String.format("%ddb", item.rssi()));
        ((TextView) result.findViewById(R.id.history_item_read_x)).setText(String.format("%.1f", item.x()));
        ((TextView) result.findViewById(R.id.history_item_read_y)).setText(String.format("%.1f", item.y()));


         return result;
    }




/*
    public ArrayAdapter<SignalData> adapter() {
        return signalDataArrayAdapter;
    }*/

}
