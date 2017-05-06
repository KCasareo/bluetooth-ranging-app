
package com.kcasareo.beaconService.Beacons;

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
        threads = new HashMap<>();
    }

    public void add(BeaconCreateDescription description) {
        Beacon beacon = BeaconFactory.create(description);

        beacons.put(description.id(), beacon);
    }
    // Return a beacon that matches the given id
    public Beacon findBeacon(String id) {
        return beacons.get(id);
    }

    // Return a snapshot of all signal strengths at this moment.
    public Map<String, Integer> signalStrength() {
        Map<String, Integer> signal_strength = new HashMap<>();
        for (Beacon beacon : beacons.values()) {
            // Wait until the beacon is free to read.
            while (!beacon.semaphore.tryAcquire()) { // Wait until the beacon has stopped writing.  }
                signal_strength.put(beacon.id(), beacon.signalStrength);
                beacon.semaphore.release();
            }
        }
        return signal_strength;
    }
}
