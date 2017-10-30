package com.BeaconManager.application;

//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.BeaconManager.application.adapter.HistoryAdapter;
import com.BeaconManager.beaconService.location.Position;
import com.BeaconManager.ranging.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 29/09/2017.
 */

public class HistoryFragment extends ListFragment implements HistoryAdapter.OnDataChangedListener{
    private final String TAG = "HisFrag";
    private int index;
    HistoryListener mCallback;
    private String selectedAddress;
    private HashMap<String, Position> changeMap = new HashMap<>();

    private View view;
    private boolean autoState = false;
    private boolean localise = false;
    private Position localPosition;
    private TextView textLocaliseX;
    private TextView textLocaliseY;
    private TextView indexText;

    public boolean localiseState() { return localise; }
    public boolean autoState() { return autoState; }

    protected void setAutoState(boolean autoState) {
        this.autoState = autoState;
    }

    protected void setLocaliseState(boolean localise) {
        this.localise = localise;
    }

    public void setLocalPosition(Position position) {
        this.localPosition = position;
    }

    @Override
    public void onIndexDataChanged(int index) {
        this.index = index;
    }



    public interface HistoryListener {
        // Tell the main activity bound to service to update the position of a beacon
        public void onPositionUpdate(String address, double x, double y) throws RemoteException;
    }

    private HistoryAdapter mHistoryAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);
        //this.getListView().setItemsCanFocus(true);
        Button buttonNext = (Button) view.findViewById(R.id.history_button_next);
        Button buttonPrev = (Button) view.findViewById(R.id.history_button_previous);
        Button buttonLatest = (Button) view.findViewById(R.id.history_button_latest);
        Button buttonUpdate = (Button) view.findViewById(R.id.history_button_update);
        ToggleButton toggleAuto = (ToggleButton) view.findViewById(R.id.history_toggle_auto);
        ToggleButton toggleLocal = (ToggleButton) view.findViewById(R.id.history_toggle_localise);
        EditText editTextX = (EditText) view.findViewById(R.id.history_edit_x);
        EditText editTextY = (EditText) view.findViewById(R.id.history_edit_y);

        this.textLocaliseX = (TextView) view.findViewById(R.id.history_text_pos_x);
        this.textLocaliseY = (TextView) view.findViewById(R.id.history_text_pos_y);
        //textLocaliseX.setText(String.format("%.1f", 0));
        //textLocaliseY.setText(String.format("%.1f", 0));

        this.textLocaliseX.setText("0.0");
        this.textLocaliseY.setText("0.0");

        toggleAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoState = isChecked;
            }
        });

        toggleLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                localise = isChecked;
            }
        });

        this.indexText = (TextView) view.findViewById(R.id.history_text_index);




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
                        changeMap.get(selectedAddress).update_x(d);
                    }

                } catch (NumberFormatException e) {
                    Log.d(TAG, "Edit Y Wrong double");
                    // Ignore input.
                    return;
                }

            }
        });

        toggleAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAutoState(isChecked);
            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.next();
                Log.d(TAG, "Next Click " + mHistoryAdapter.index());
                //v.notify();

            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.prev();
                Log.d(TAG, "Previous Click " + mHistoryAdapter.index());
                //v.notify();
            }
        });

        buttonLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.last();
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

    public void updateTextViews() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (autoState) {
                    indexText.setText(Integer.toString(index));
                }

                if(localPosition != null && localise) {
                    Log.i(TAG, "localPosition exists");
                    textLocaliseX.setText(String.format("%.2f", localPosition.x()));
                    textLocaliseY.setText(String.format("%.2f", localPosition.y()));
                } else {
                    Log.i(TAG, "localPosition != exist");
                    textLocaliseX.setText("0.0");
                    textLocaliseY.setText("0.0");
                }
            }
        });

    }

    public static HistoryFragment getInstance() {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public void setHistory(HistoryAdapter historyAdapter) {
        mHistoryAdapter = historyAdapter;
        setListAdapter(mHistoryAdapter);
        mHistoryAdapter.setmOnDataChangedListener(this);
    }

    //View previous;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /*
        if (previous != null)
            previous.setSelected(false);
        v.setSelected(true); */
        selectedAddress = mHistoryAdapter.getCurrentAddress(position);
        //previous=v;
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
