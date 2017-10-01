package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
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
    private final long POLL_TIME  = 1000;
    private HashMap<String, Pair<Beacon, BluetoothGatt>> beacons;
    private final ArrayList<String> filter = new ArrayList<>();
    private TimerTask pollTask;
    private Timer pollTimer;

    public Beacons() {
        beacons = new HashMap<>();
        pollTask = new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<String, Pair<Beacon, BluetoothGatt>> entry : beacons.entrySet() ) {
                    // Fire off polling asynchronously.
                    new Thread(entry.getValue().first.task()).start();
                }
            }
        };

        pollTimer = new Timer(true);
        pollTimer.schedule(pollTask, 0, POLL_TIME);
        /* To do: dynamically add filter elements to array list */
        filter.add("B0:91:22:EA:3A:05");
        filter.add("B0:B4:48:D7:5D:02");
        filter.add("B0:91:22:F6:A0:87");
    }

    public void add(Beacon beacon, BluetoothGatt gatt) {
        if(!beacons.containsKey(beacon.address())) {
            beacons.put(beacon.address(), new Pair(beacon, gatt));
        }
    }

    public Beacon findBeacon(String id) {
        return beacons.get(id).first;
    }

    public SignalData getSignalData() {
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
