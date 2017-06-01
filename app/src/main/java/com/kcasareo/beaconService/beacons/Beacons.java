
package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 5/05/2017.
 */

public class Beacons {

    private HashMap<String, Beacon> beacons;
    private HashMap<String, Thread> threads;

    // This class handles factory dependencies
    public Beacons() {
        beacons = new HashMap<>();
        // Threads are unnecessary now.
        //threads = new HashMap<>();
    }

    public void add(BeaconCreateDescription description) {
        beacons.put(description.id(), BeaconFactory.create(description));
    }
    // Return a beacon that matches the given id
    public Beacon findBeacon(String id) {
        return beacons.get(id);
    }

    public Beacon findBeacon(BluetoothDevice device) {
        try {
            return beacons.get(device.getAddress());
        } catch (Exception e) {
            return null;
        }
    }

    // Return a copy of all signal strengths at this moment.
    public Map<String, Integer> signalStrength() {
        Map<String, Integer> signalStrength = new HashMap<>();
        for (Beacon beacon : beacons.values()) {
                signalStrength.put(beacon.id(), beacon.getSignalStrength());
                //Log.d("Beacons", "ID:" + beacon.id() + " Strength: " + beacon.getSignalStrength());
        }
        return signalStrength;
    }

    public boolean empty() {
        return beacons.isEmpty();
    }
}
