
package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 5/05/2017.
 */

public class Beacons implements Parcelable {

    private HashMap<String, Beacon> beacons;

    // This class handles factory dependencies
    public Beacons() {
        beacons = new HashMap<>();
        // Threads are unnecessary now.
        //threads = new HashMap<>();
    }

    protected Beacons(Parcel in) {

    }

    public static final Creator<Beacons> CREATOR = new Creator<Beacons>() {
        @Override
        public Beacons createFromParcel(Parcel in) {
            return new Beacons(in);
        }

        @Override
        public Beacons[] newArray(int size) {
            return new Beacons[size];
        }
    };

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
    public Map<String, Integer> signalStrengthAsMap() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle beaconsBundle = new Bundle();
        for (Map.Entry<String, Beacon> beacon : beacons.entrySet()) {

        }
    }




    protected Beacons(HashMap<String, Beacon> beacons) {
        this.beacons = beacons;
    }
}
