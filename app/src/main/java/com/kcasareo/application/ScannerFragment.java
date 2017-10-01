package com.kcasareo.application;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcasareo.beaconService.beacons.BeaconAdapter;
import com.kcasareo.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 * Attach this to main activity
 */

public class ScannerFragment extends Fragment {
    private BeaconAdapter mBeaconAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scanner, container, false);
    }

    public static ScannerFragment getInstance() {
        ScannerFragment fragment = new ScannerFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public void setmBeaconAdapter(BeaconAdapter beaconAdapter) {
        this.mBeaconAdapter = beaconAdapter;
    }
}


