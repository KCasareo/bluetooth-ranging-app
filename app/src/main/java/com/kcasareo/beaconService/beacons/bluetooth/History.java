package com.kcasareo.beaconService.beacons.bluetooth;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kcasareo.location.Position;
import com.kcasareo.ranging.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 28/09/2017.
 * History object to be constructed at the view side.
 */

public class History extends BaseAdapter {
    private final String TAG = "History Adapter";
    private ArrayList<SignalData> signalDataHistory;
    private HashMap<String, Position> changeMap = new HashMap<>();



    //private ArrayAdapter<SignalData> signalDataArrayAdapter;
    private int index;

    public History() {
        signalDataHistory = new ArrayList<>();
        index = 0;
    }

    public void next() {
        if (index < signalDataHistory.size() - 1)
            index++;

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
        if (index > 0)
            index--;

    }

    public void last() {
        index = signalDataHistory.size() - 1;

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

    /* Get the data at frame X */
    //public SignalData read(int at) { return signalDataHistory.get(at).as; }

    //public SignalData showCurrent() { return read(index); }

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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        Log.d(TAG, "Get View");

        if(convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter_item, parent, false);
        } else {
            result = convertView;
        }

        final SignalDatum item = signalDataHistory.get(index).asArray().get(position);
        Log.d(TAG, "Text Edit Address: " + item.address());
        ((TextView) result.findViewById(R.id.history_item_address)).setText(item.address());
        Log.d(TAG, "Text Edit Distance: " + item.distance());
        ((TextView) result.findViewById(R.id.history_item_distance)).setText(String.format("%.2fm", item.distance()));
        ((TextView) result.findViewById(R.id.history_item_db)).setText(String.format("%ddb", item.rssi()));
        /* Add Listener to x_position */
        Log.d(TAG, "Add Listener X");
        ((EditText) result.findViewById(R.id.history_item_edit_x)).addTextChangedListener(new TextWatcher() {
            //Assign the current index for this listener
            //private final int index = index();
            private final String address = item.address();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cache changes, then push on update. Only push the currently selected change.
                    double d = Double.parseDouble(s.toString());
                    if(!changeMap.containsKey(address)) {
                        changeMap.put(address, new Position(d, 0.0));
                    } else {
                        changeMap.get(address).update_x(d);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /* Add Listener to y_position */
        Log.d(TAG, "Add Listener Y");
        ((EditText) result.findViewById(R.id.history_item_edit_y)).addTextChangedListener(new TextWatcher() {
            //Assign the current index for this listener
            //private final int index = index();
            private final String address = item.address();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double d = Double.parseDouble(s.toString());
                if(!changeMap.containsKey(address)) {
                    changeMap.put(address, new Position(0.0, d));
                } else {
                    changeMap.get(address).update_y(d);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return result;
    }




/*
    public ArrayAdapter<SignalData> adapter() {
        return signalDataArrayAdapter;
    }*/

}
