package com.kcasareo.application;

//import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
//import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
        import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
        import android.os.Bundle;
import android.os.IBinder;
        import android.os.RemoteException;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
import android.view.View;
        import android.widget.Button;

import com.kcasareo.application.adapter.HistoryAdapter;
import com.kcasareo.beaconService.BeaconService;
import com.kcasareo.beaconService.IBeaconService;
import com.kcasareo.beaconService.IBeaconServiceCallback;
//import com.kcasareo.beaconService.frames.Frame;
//import com.kcasareo.beaconService.frames.Frames;
//import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.application.adapter.BeaconAdapter;
import com.kcasareo.beaconService.beacons.bluetooth.SignalData;
        import com.kcasareo.ranging.R;

        import java.util.HashMap;
        import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */
public class MainActivity extends AppCompatActivity implements HistoryFragment.HistoryListener {
    private BeaconService beaconService;
    private IBeaconService mBeaconService = null;
    private SignalData signalData;
    private Timer updateTimer;
    private static final long TIME_UPDATE = 500;
    //private ListView lv;
    //private ViewGroup layout;
    private Intent intent;
    private BeaconAdapter beaconAdapter = null;
    private HistoryAdapter historyAdapter = null;
    private FragmentManager fragmentManager;
    private GraphFragment graphFragment;
    private HistoryFragment historyFragment;
    private ScannerFragment scannerFragment;
    private HashMap<String, Boolean> state = new HashMap<>();
    private Boolean refreshFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, BeaconService.class);
        startService(intent);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);
        //beaconAdapter = new BeaconAdapter();
        //lv.setAdapter(beaconAdapter);

        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter();
        }

        fragmentManager = getFragmentManager();


        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null)
                return;

            //graphFragment = GraphFragment.getInstance();
            historyFragment = HistoryFragment.getInstance();
            historyFragment.setHistory(historyAdapter);
            scannerFragment = ScannerFragment.getInstance();


            //graphFragment.setArguments(getIntent().getExtras());
            historyFragment.setArguments(getIntent().getExtras());
            scannerFragment.setArguments(getIntent().getExtras());

            fragmentManager.beginTransaction().add(R.id.fragment_container, scannerFragment).commit();

        }

        Button buttonScanner = (Button) findViewById(R.id.button_scanner);
        Button buttonHistory = (Button) findViewById(R.id.button_history);
        //Button buttonGraph

        buttonScanner.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (scannerFragment == null) {
                    scannerFragment = ScannerFragment.getInstance();
                    scannerFragment.setArguments(getIntent().getExtras());
                }



                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, scannerFragment);
                transaction.commit();
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (historyFragment == null) {
                    historyFragment = HistoryFragment.getInstance();
                    historyFragment.setHistory(historyAdapter);
                    historyFragment.setArguments(getIntent().getExtras());

                }



                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, historyFragment);
                transaction.commit();
                historyFragment.setHistoryListener((HistoryFragment.HistoryListener) getParent());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (beaconAdapter != null)
            beaconAdapter.notifyDataSetChanged();
        /*
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    current);
            lv.setAdapter(arrayAdapter);
        }*/


    }

    @Override
    protected void onStop() {
        super.onStop();
        /*
        if (mConnectedGatt != null) {
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
        } //*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(beaconServiceConnection);
    }



    private IBeaconServiceCallback mCallback = new IBeaconServiceCallback.Stub() {
        final String TAG = "MainActivity/bscb";
        @Override
        public void signalsResponse(final SignalData data) throws RemoteException {
            MainActivity.this.signalData = data;
            Log.i(TAG, "Signals Response.");
            Log.i(TAG, "SignalData Hash: " + data.hashCode());
            // Create a new beaconadapter and have the listview bind to it.
            if( beaconAdapter == null) {
                beaconAdapter = new BeaconAdapter(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scannerFragment.setListAdapter(beaconAdapter);
                        //beaconAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                if (refreshFlag)
                    beaconAdapter.refresh(data);
                // Modify the entire dataset
                else
                    beaconAdapter.add(data);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beaconAdapter.notifyDataSetChanged();
                    }
                });
            }
            // Add latest to historyAdapter.

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(refreshFlag)
                        historyAdapter.refresh(data);
                    else
                        historyAdapter.update(data);
                    historyAdapter.notifyDataSetChanged();
                }
            });
            refreshFlag = false;
        }

        /*
        @Override
        public void handleResponse(Snapshot snapshot) throws RemoteException {
            /*
            if (frames == null) {
                return;
            }
            frames = snapshot.frames();
            captured = true;
            current = frames.getLast().toList();
            // Update the list view;
            arrayAdapter.notifyDataSetChanged();

        }*/


    };



    private ServiceConnection beaconServiceConnection = new ServiceConnection() {
        private String TAG = "BSC";
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "OnServiceConnected");
            mBeaconService = IBeaconService.Stub.asInterface(iBinder);
            updateTimer = new Timer();
            // Every 500 ms, call last snap
            updateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
//                intent.setAction(IBeaconService.class.)
                    try {
                        Log.i(TAG, "Dispatch signalStrength");
                        mBeaconService.signalsStrength(mCallback);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Remote does not exist");
                    }
                }
            }, TIME_UPDATE, TIME_UPDATE) ;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // Stop all timers if the service disconnects.
            updateTimer.purge();
        }
    };

    @Override
    public void onPositionUpdate(String address, double x, double y) throws RemoteException {
        mBeaconService.updatePosition(address, x, y);
        refreshFlag = true;
    }
}

/*
* Combine Drone Odometry, Beacon Distances to determine location.
*
* */