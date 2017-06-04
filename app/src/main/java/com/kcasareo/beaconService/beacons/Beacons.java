package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.util.Pair;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * Created by Kevin on 4/06/2017.
 * Contains beacon collection and manages all devices.
 */

public class Beacons {
    private final String TAG = this.getClass().getSimpleName();
    private HashMap<String, Pair<Beacon, BluetoothGattCallback>> beacons;

    public void add(Beacon beacon, BluetoothGattCallback callback) {
        if(!beacons.containsKey(beacon.id()))
            beacons.put(beacon.id(), new Pair(beacon, callback));
    }

    public Beacon findBeacon(String id) {
        return beacons.get(id).first;
    }

    public BluetoothGattCallback findCallback(String id) {
        return beacons.get(id).second;
    }
}
