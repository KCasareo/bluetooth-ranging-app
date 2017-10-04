package com.kcasareo.application;

//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kcasareo.beaconService.beacons.bluetooth.History;
import com.kcasareo.location.Position;
import com.kcasareo.ranging.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 29/09/2017.
 */

public class HistoryFragment extends ListFragment implements History.OnDataChangedListener{
    private final String TAG = "HisFrag";
    private int index;
    HistoryListener mCallback;
    private String selectedAddress;
    private HashMap<String, Position> changeMap = new HashMap<>();
    TextView indexText;
    private View view;

    @Override
    public void onIndexDataChanged(int index) {
        this.index = index;
        indexText.setText(Integer.toString(index));

    }

    public void notifyText() {

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
        //this.getListView().setItemsCanFocus(true);
        Button buttonNext = (Button) view.findViewById(R.id.history_button_next);
        Button buttonPrev = (Button) view.findViewById(R.id.history_button_previous);
        Button buttonLatest = (Button) view.findViewById(R.id.history_button_latest);
        Button buttonUpdate = (Button) view.findViewById(R.id.history_button_update);
        EditText editTextX = (EditText) view.findViewById(R.id.history_edit_x);
        EditText editTextY = (EditText) view.findViewById(R.id.history_edit_y);
        indexText = (TextView) view.findViewById(R.id.history_text_index);

        indexText.setText(Integer.toString(index));

        editTextY.addTextChangedListener(new TextWatcher() {
            //Assign the current index for this listener
            //private final int index = index();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double d = Double.parseDouble(s.toString());
                    if(!changeMap.containsKey(selectedAddress)) {
                        changeMap.put(selectedAddress, new Position(0.0, d));
                    } else {
                        changeMap.get(selectedAddress).update_y(d);
                    }

                } catch (NumberFormatException e) {
                    Log.d(TAG, "Edit Y Wrong double");
                    // Ignore input.
                    return;
                }

            }
        });

        editTextX.addTextChangedListener(new TextWatcher() {
            //Assign the current index for this listener
            //private final int index = index();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double d = Double.parseDouble(s.toString());
                    if(!changeMap.containsKey(selectedAddress)) {
                        changeMap.put(selectedAddress, new Position(d, 0.0));
                    } else {
                        changeMap.get(selectedAddress).update_y(d);
                    }

                } catch (NumberFormatException e) {
                    Log.d(TAG, "Edit Y Wrong double");
                    // Ignore input.
                    return;
                }

            }
        });





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
                if (changeMap.isEmpty())
                    return;
                for (Map.Entry<String, Position> entry : changeMap.entrySet()) {
                    double x = entry.getValue().x();
                    double y = entry.getValue().y();
                    Log.d(TAG, "Changing:" + entry.getKey() + " X: " + x + " Y: " +y);

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        selectedAddress = mHistory.getCurrentAddress(position);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "Attaching");
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

    public void setHistoryListener(HistoryListener listener) {
        mCallback = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "Attaching");
        super.onAttach(activity);

        try {
            mCallback = (HistoryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HistoryListener");
        }
    }









}
