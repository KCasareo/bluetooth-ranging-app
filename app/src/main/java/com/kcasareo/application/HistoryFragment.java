package com.kcasareo.application;

//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kcasareo.beaconService.beacons.bluetooth.History;
import com.kcasareo.location.Position;
import com.kcasareo.ranging.R;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by Kevin on 29/09/2017.
 */

public class HistoryFragment extends ListFragment implements History.OnDataChangedListener{
    private final String TAG = "HisFrag";
    private int index;
    HistoryListener mCallback;

    private View view;

    @Override
    public void onIndexDataChanged(int index) {
        this.index = index;
    }

    public interface HistoryListener {
        // Tell the main activity bound to service to update the position of a beacon
        public void onPositionUpdate(String address, double x, double y) throws RemoteException;
    }

    private History mHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);

        Button buttonNext = (Button) view.findViewById(R.id.history_button_next);
        Button buttonPrev = (Button) view.findViewById(R.id.history_button_previous);
        Button buttonLatest = (Button) view.findViewById(R.id.history_button_latest);
        Button buttonUpdate = (Button) view.findViewById(R.id.history_button_update);
        TextView indexText = (TextView) view.findViewById(R.id.history_text_index);

        indexText.setText(Integer.toString(index));



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistory.next();
                Log.d(TAG, "Next Click " + mHistory.index());
                //v.notify();

            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistory.prev();
                Log.d(TAG, "Previous Click " + mHistory.index());
                //v.notify();
            }
        });

        buttonLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistory.last();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Map.Entry<String, Position> entry : mHistory.transaction ().entrySet()) {
                    double x = entry.getValue().x();
                    double y = entry.getValue().y();
                    try {
                        mCallback.onPositionUpdate(entry.getKey(), x, y);
                    } catch (RemoteException e) {
                        return;
                    }
                }
            }
        });

        return view;
    }

    public static HistoryFragment getInstance() {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public void setHistory(History history) {
        mHistory = history;
        setListAdapter(mHistory);
        mHistory.setmOnDataChangedListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            return;
        }
        try {
            mCallback = (HistoryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HistoryListener");
        }
    }









}
