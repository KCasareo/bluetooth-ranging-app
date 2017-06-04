package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.util.Pair;
import android.util.SparseArray;

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
    private final long POLL_TIME  = 500;
    private HashMap<String, Pair<Beacon, BluetoothGatt>> beacons;
    private TimerTask pollTask;
    private Timer pollTimer;

    public Beacons() {
        beacons = new HashMap<>();
        pollTask = new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<String, Pair<Beacon, BluetoothGatt>> entry : beacons.entrySet() ) {
                    entry.getValue().first.poll();
                }
            }
        };

        pollTimer = new Timer(true);
        pollTimer.schedule(pollTask, 0, POLL_TIME);
    }

    public void add(Beacon beacon, BluetoothGatt gatt) {
        if(!beacons.containsKey(beacon.id()))
            beacons.put(beacon.id(), new Pair(beacon, gatt));
    }

    public Beacon findBeacon(String id) {
        return beacons.get(id).first;
    }

}
