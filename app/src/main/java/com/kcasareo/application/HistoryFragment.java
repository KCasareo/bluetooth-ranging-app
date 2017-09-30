package com.kcasareo.application;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcasareo.beaconService.beacons.bluetooth.History;
import com.kcasareo.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 */

public class HistoryFragment extends Fragment {
    private History history;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history, container, false);
    }
}
