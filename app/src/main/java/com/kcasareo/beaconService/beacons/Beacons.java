package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
<<<<<<< HEAD
import android.provider.Telephony;
=======
>>>>>>> BeaconRestructure
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;

import com.kcasareo.beaconService.beacons.bluetooth.SignalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 4/06/2017.
 * Contains beacon collection and manages all devices.
 */

public class Beacons {

    private final String TAG = this.getClass().getSimpleName();
    private final long POLL_TIME  = 2500;
    private final long POLL_DELAY = 2500;
    private HashMap<String, Pair<Beacon, BluetoothGatt>> beacons;
<<<<<<< HEAD
    private HashMap<String, Thread> threads;
=======
    private final ArrayList<String> filter = new ArrayList<>();
>>>>>>> BeaconRestructure
    private TimerTask pollTask;
    private Timer pollTimer;

    public Beacons() {
        beacons = new HashMap<>();
        ///*
        threads = new HashMap<>();
        pollTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Poll Task");
                for (Map.Entry<String, Pair<Beacon, BluetoothGatt>> entry : beacons.entrySet() ) {
                    // Fire off polling asynchronously.
                    // Stop firing if > 6 callbacks.
                    entry.getValue().first.poll();
                    //threads.put(entry.getKey(), thread);
                    //thread.start();
                }
            }
        };

        pollTimer = new Timer(true);
<<<<<<< HEAD
        pollTimer.schedule(pollTask, POLL_DELAY, POLL_TIME);//*/
=======
        pollTimer.schedule(pollTask, 0, POLL_TIME);
        /* To do: dynamically add filter elements to array list */
        filter.add("B0:91:22:EA:3A:05");
        filter.add("B0:B4:48:D7:5D:02");
        filter.add("B0:91:22:F6:A0:87");
>>>>>>> BeaconRestructure
    }

    public void add(Beacon beacon, BluetoothGatt gatt) {
        if(!beacons.containsKey(beacon.address())) {
            beacons.put(beacon.address(), new Pair(beacon, gatt));
        }
    }

    public Beacon findBeacon(String id) {
        Log.d(TAG, "Finding Beacon");
        return beacons.get(id).first;
    }

    public SignalData getSignalData() {
        Log.i(TAG, "Get Signa Data");
        SignalData data = new SignalData();
        for (HashMap.Entry<String, Pair<Beacon, BluetoothGatt>> entry : beacons.entrySet() ) {
            Beacon beacon = entry.getValue().first;
            data.add(beacon.datum());
        }
        return data;
    }

    public boolean matches(String address) {
        return filter.contains(address);
    }

    public boolean contains(String id) {
        return beacons.get(id) != null;
    }

}
