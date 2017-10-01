package com.kcasareo.application;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kcasareo.beaconService.beacons.BeaconAdapter;
import com.kcasareo.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 * Attach this to main activity
 */

public class ScannerFragment extends Fragment {
    private BeaconAdapter mBeaconAdapter;
    private ListView lv = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scanner, container, false);

        lv = (ListView) view.findViewById(R.id.listview_rssi);

        if (lv == null && mBeaconAdapter != null)
            lv.setAdapter(mBeaconAdapter);
        return view;
    }

    public static ScannerFragment getInstance() {
        ScannerFragment fragment = new ScannerFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public void setmBeaconAdapter(BeaconAdapter beaconAdapter) {
        this.mBeaconAdapter = beaconAdapter;
        if(lv != null)
            lv.setAdapter(mBeaconAdapter);
    }
}


