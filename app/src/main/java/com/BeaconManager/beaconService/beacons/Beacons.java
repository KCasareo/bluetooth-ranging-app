package com.BeaconManager.beaconService.beacons;

import android.bluetooth.BluetoothGatt;
import android.util.Log;
import android.util.Pair;

import com.BeaconManager.beaconService.beacons.bluetooth.SignalData;
import com.BeaconManager.beaconService.location.Localise;
import com.BeaconManager.beaconService.location.Localiser;
import com.BeaconManager.beaconService.location.LocaliserFactory;
import com.BeaconManager.beaconService.location.MODE;
import com.BeaconManager.beaconService.location.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private Localiser localiser;
    //private MODE mode = MODE.DIM_2;

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
        setMode(MODE.DIM_2);
        /* To do: dynamically add filter elements to array list */
        //filter.add("B0:91:22:EA:3A:05");
        //filter.add("B0:B4:48:D7:5D:02");
        //filter.add("B0:91:22:F6:A0:87");
    }

    public void add(Beacon beacon, BluetoothGatt gatt) {
        if(!beacons.containsKey(beacon.address())) {
            beacons.put(beacon.address(), new Pair(beacon, gatt));
            beacon.setLocaliser(localiser);
        }
    }

    public long getDistanceZero() {
        if (localiser == null)
            setMode(MODE.DIM_2);
        return localiser.distanceZero();
    }

    public double getPathLossFactor() {
        if (localiser == null)
            setMode(MODE.DIM_2);
        return localiser.pathLossFactor();
    }

    public void setPathLoss(double pathLoss) {
        localiser.setPathLossFactor(pathLoss);
    }

    public void setStrengthDistanceZero(long strengthDistanceZero) {
        localiser.setStrengthDistanceZero(strengthDistanceZero);
    }

    public Localiser getLocaliser() {
        if (localiser == null)
            setMode(MODE.DIM_2);
        return localiser;
    }

    public void setMode(MODE mode) {
        localiser = LocaliserFactory.create(mode);
    }

    public Position localise() {
        ArrayList<Position> positions = new ArrayList<>();
        Iterator<Map.Entry<String, Pair<Beacon, BluetoothGatt>>> it = beacons.entrySet().iterator();
        while(it.hasNext()) {
            positions.add(it.next().getValue().first.position);
        }
        return localiser.localise(positions);
    }

    public void filter(String address) {
        filter.add(address);
    }

    public Beacon findBeacon(String id) {
        Log.d(TAG, "Finding Beacon");
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
