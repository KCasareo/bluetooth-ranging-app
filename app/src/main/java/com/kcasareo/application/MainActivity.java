package com.kcasareo.application;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kcasareo.beaconService.BeaconService;
import com.kcasareo.beaconService.IBeaconService;
import com.kcasareo.beaconService.IBeaconServiceCallback;
import com.kcasareo.beaconService.frames.Frames;
import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.ranging.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */
public class MainActivity extends AppCompatActivity {
    private BeaconService beaconService;
    private IBeaconService mBeaconService = null;
    private Frames frames;
    private Timer updateTimer;
    private static final long TIME_UPDATE = 500;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview_rssi);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BeaconService.class);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);
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
    protected void onStop() {
        super.onStop();
    }


    private IBeaconServiceCallback mCallback = new IBeaconServiceCallback.Stub() {
        @Override
        public void handleResponse(Snapshot snapshot) throws RemoteException {
            frames = snapshot.frames();
            frames.getList();
        }
    };



    private ServiceConnection beaconServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBeaconService = IBeaconService.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}

/*
* Combine Drone Odometry, Beacon Distances to determine location.
*
* */