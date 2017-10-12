package com.LocaliseFramework.beaconService.beacons;

import android.bluetooth.BluetoothGatt;
import android.util.Log;
import android.util.Pair;

import com.LocaliseFramework.beaconService.beacons.bluetooth.SignalData;
import com.LocaliseFramework.beaconService.location.Localise;
import com.LocaliseFramework.beaconService.location.MODE;
import com.LocaliseFramework.beaconService.location.Position;

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
    private MODE mode = MODE.TWO;

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
        //filter.add("B0:91:22:EA:3A:05");
        //filter.add("B0:B4:48:D7:5D:02");
        //filter.add("B0:91:22:F6:A0:87");
    }

    public void add(Beacon beacon, BluetoothGatt gatt) {
        if(!beacons.containsKey(beacon.address())) {
            beacons.put(beacon.address(), new Pair(beacon, gatt));
        }
    }

    public void setMode(MODE mode) {
         this.mode = mode;
    }

    public Position localise() {
        ArrayList<Position> positions = new ArrayList<>();
        Iterator<Map.Entry<String, Pair<Beacon, BluetoothGatt>>> it = beacons.entrySet().iterator();
        while(positions.size() < 3) {
            // Stop if there are no more beacons detected
            if (it.hasNext())
                positions.add(it.next().getValue().first.position);
            else
                return new Position(0,0,0);
        }
        return Localise.localise(positions.get(0), positions.get(1), positions.get(2));
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
