package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Kevin on 29/07/2017.
 */

public class BluetoothFactory {
    // Ensure that no new instance can be created.
    private BluetoothFactory() {};

    public static Bluetooth create(BluetoothDevice device) {
        // Build bluetooth creation here.
        return new Bluetooth(device);
    }
}
