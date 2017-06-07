package com.kcasareo.application;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kcasareo.beaconService.BeaconService;
import com.kcasareo.beaconService.IBeaconService;
import com.kcasareo.beaconService.IBeaconServiceCallback;
//import com.kcasareo.beaconService.frames.Frame;
//import com.kcasareo.beaconService.frames.Frames;
//import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.beaconService.beacons.BeaconAdapter;
import com.kcasareo.beaconService.beacons.bluetooth.SignalData;
import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;
import com.kcasareo.ranging.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.kcasareo.ranging.R.layout.activity_main;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */
public class MainActivity extends AppCompatActivity {
    private BeaconService beaconService;
    private IBeaconService mBeaconService = null;
    private SignalData signalData;
    private Timer updateTimer;
    private static final long TIME_UPDATE = 500;
    private ListView lv;
    private ViewGroup layout;
    private Intent intent;
    private BeaconAdapter beaconAdapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview_rssi);
        intent = new Intent(this, BeaconService.class);
        startService(intent);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);
        beaconAdapter = new BeaconAdapter();
        lv.setAdapter(beaconAdapter);

        ;


    }

    @Override
    protected void onStart() {
        super.onStart();
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
        public void signalsResponse(SignalData data) throws RemoteException {
            MainActivity.this.signalData = data;
            Log.i(TAG, "Signals Response.");
            Log.d(TAG, "SignalData Hash: " + data.hashCode());
            // Create a new beaconadapter and have the listview bind to it.
            if( beaconAdapter == null) {
                beaconAdapter = new BeaconAdapter(data);
            } else {
                // Modify the entire dataset.
                beaconAdapter.set(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        beaconAdapter.notifyDataSetChanged();
                    }
                });
            }
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
                        mBeaconService.signalsStrength(mCallback);

                    } catch (RemoteException e) {
                    }
                }
            }, 0, TIME_UPDATE) ;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // Stop all tiemrs if the service disconnects.
            updateTimer.purge();
        }
    };
}

/*
* Combine Drone Odometry, Beacon Distances to determine location.
*
* */