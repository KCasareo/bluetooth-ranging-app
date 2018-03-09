package com.BeaconManager.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;

import com.BeaconManager.beaconService.beacons.BEACON_TYPE;
import com.BeaconManager.beaconService.beacons.Beacon;
import com.BeaconManager.beaconService.beacons.BeaconFactory;
import com.BeaconManager.beaconService.beacons.BeaconFactoryParam;
import com.BeaconManager.beaconService.location.Position;

/**
 * Created by Kevin on 29/07/2017.
 */

public class BluetoothFactory implements BeaconFactory {
    // Ensure that no new instance can be created.

    public static Bluetooth create(BluetoothDevice device) {
        // Build bluetooth creation here.
        return new Bluetooth(device);
    }

    public BluetoothFactory() {}

    /* Returns null if invalid type param */
    public Beacon create(BeaconFactoryParam beaconFactoryParam) {
        // Ensure that this is the correct type.
        if (beaconFactoryParam.type() != BEACON_TYPE.BLUETOOTH) {
            return null;
        }

        String name = ((BluetoothFactoryParam) beaconFactoryParam).name;
        String address = ((BluetoothFactoryParam) beaconFactoryParam).address;
        int identifier = ((BluetoothFactoryParam) beaconFactoryParam).identifier;
        Position position = ((BluetoothFactoryParam) beaconFactoryParam).position;
        return new Bluetooth(identifier, name, address, position);
    }
}
