package com.kcasareo.application;

//import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kcasareo.application.adapter.BeaconAdapter;
import com.kcasareo.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 * Attach this to main activity
 */

public class ScannerFragment extends ListFragment {
    /*
    OnScannerSelectedListener mCallback;

    public interface OnScannerSelectedListener {
        public void onDataUpdate(SignalData data);
    }*/

    private BeaconAdapter mBeaconAdapter;
    private ListView lv = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scanner, container, false);
        return view;
    }

    public void setmBeaconAdapter(BeaconAdapter mBeaconAdapter) {
        this.mBeaconAdapter = mBeaconAdapter;
        setListAdapter(this.mBeaconAdapter);
    }
/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) view.findViewById(R.id.listview_rssi);
    }*/
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnScannerSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnScannerSelected Listener");
        }
    }
*/

    public static ScannerFragment getInstance() {
        ScannerFragment fragment = new ScannerFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

}


