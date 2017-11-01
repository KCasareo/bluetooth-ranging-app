package com.BeaconManager.application;

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
import android.util.Log;
import android.view.View;
        import android.widget.Button;

import com.BeaconManager.application.adapter.HistoryAdapter;
import com.BeaconManager.application.rosPublisher.BeaconPublisher;
import com.BeaconManager.beaconService.BeaconService;
import com.BeaconManager.beaconService.IBeaconService;
import com.BeaconManager.beaconService.IBeaconServiceCallback;
//import com.kcasareo.beaconService.frames.Frame;
//import com.kcasareo.beaconService.frames.Frames;
//import com.kcasareo.beaconService.frames.Snapshot;
import com.BeaconManager.application.adapter.BeaconAdapter;
import com.BeaconManager.beaconService.beacons.bluetooth.SignalData;
import com.BeaconManager.beaconService.location.Position;
import com.BeaconManager.ranging.R;

import org.ros.android.RosActivity;
import org.ros.node.NodeMainExecutor;

import java.util.HashMap;
        import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */




public class MainActivity extends RosActivity implements HistoryFragment.HistoryListener {
    private final String TAG = "Main Activity";
    private BeaconPublisher publisher;
    private IBeaconService mBeaconService = null;
    private SignalData signalData;
    private Timer updateTimer;
    private static final long TIME_UPDATE = 500;

    private Intent intent;
    private BeaconAdapter beaconAdapter = null;
    private HistoryAdapter historyAdapter = null;
    private FragmentManager fragmentManager;
    private GraphFragment graphFragment;
    private HistoryFragment historyFragment;
    private ScannerFragment scannerFragment;
    private HashMap<String, Boolean> state = new HashMap<>();
    private Boolean refreshFlag = false;
    //private final String MASTER_URI

    public MainActivity() {
        super("Android Beacon Sensor","Beacon Sensor");
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        //super.init(nodeMainExecutor);
        //publisher = new BeaconPublisher();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, BeaconService.class);
        startService(intent);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);

        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter();
        }

        // Add to whitelist;

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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(beaconServiceConnection);
    }



    private IBeaconServiceCallback mCallback = new IBeaconServiceCallback.Stub() {
        final String TAG = "MainActivity/bscb";

        @Override
        public void strengthDistanceZeroResponse(long strengthDistanceZero) throws RemoteException {
            /* Code to handle response from BeaconService */
        }

        @Override
        public void pathLossFactorResponse(double pathloss) throws RemoteException {
            /* Code to handle response */
        }

        /* signalsResponse
        *  returns the latest batch of SignalData
        *
        * */
        @Override
        public void signalsResponse(final SignalData data) throws RemoteException {
            MainActivity.this.signalData = data;
            Log.i(TAG, "Signals Response.");
            Log.d(TAG, "SignalData Hash: " + data.hashCode());
            // Create a new beaconadapter and have the listview bind to it.
            if( beaconAdapter == null) {
                beaconAdapter = new BeaconAdapter(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scannerFragment.setListAdapter(beaconAdapter);
                    }
                });
            } else {
                refreshFlag = historyFragment.autoState();
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
            if(refreshFlag)
                historyAdapter.refresh(data);
            else
                historyAdapter.update(data);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    historyAdapter.notifyDataSetChanged();
                }
            });
            refreshFlag = false;
        }

        @Override
        public void localiseResponse(final Position position) throws RemoteException {
            if (position == null)
                return;
            Log.i(TAG, "Localise Response:" + position.toString());
            Log.i(TAG, "Setting Local Position");
            historyFragment.setLocalPosition(position);
            historyFragment.updateTextViews();
        }
    };


    /* beaconServiceConnection
    *  Setup polling the beaconservice
    * */
    private ServiceConnection beaconServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "OnServiceConnected");
            mBeaconService = IBeaconService.Stub.asInterface(iBinder);
            updateTimer = new Timer();

            try {
                mBeaconService.whitelistAddress("B0:91:22:EA:3A:05");
                mBeaconService.whitelistAddress("B0:B4:48:D7:5D:02");
                mBeaconService.whitelistAddress("B0:91:22:F6:A0:87");
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
            // Every 500 ms, call last snap
            updateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        mBeaconService.signalsStrength(mCallback);
                        if (historyFragment ==null)
                            return;
                        if (historyFragment.localiseState())
                            mBeaconService.localise(mCallback);
                    } catch (RemoteException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }, 0, TIME_UPDATE) ;

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
    }
}
