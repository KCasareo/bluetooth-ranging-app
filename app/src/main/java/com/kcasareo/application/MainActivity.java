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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kcasareo.beaconService.BeaconService;
import com.kcasareo.beaconService.IBeaconService;
import com.kcasareo.beaconService.IBeaconServiceCallback;
import com.kcasareo.beaconService.frames.Frame;
import com.kcasareo.beaconService.frames.Frames;
import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.ranging.R;

import java.util.ArrayList;
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
    private Frames frames;
    private ArrayList<String> current = new ArrayList<>();
    private Timer updateTimer;
    private static final long TIME_UPDATE = 500;
    private ListView lv;
    private boolean captured = false;
    private ArrayAdapter<String> arrayAdapter;
    private Intent intent;
    private BluetoothAdapter mBluetoothAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        lv = (ListView) findViewById(R.id.listview_rssi);
        intent = new Intent(this, BeaconService.class);
        startService(intent);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    current);
            lv.setAdapter(arrayAdapter);
        }


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
        @Override
        public void handleResponse(Snapshot snapshot) throws RemoteException {
            if (frames == null) {
                return;
            }
            frames = snapshot.frames();
            captured = true;
            current = frames.getLast().toList();
            // Update the list view;
            arrayAdapter.notifyDataSetChanged();
        }
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
                        mBeaconService.lastSnap(mCallback);

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