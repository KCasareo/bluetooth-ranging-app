package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;

import com.kcasareo.beaconService.beacons.Beacon;

/**
 * Created by Kevin on 4/06/2017.
 * This takes a reference to the beacon it is bound to so it can modify the RSSI value.
 */

public class GattCallback extends BluetoothGattCallback {
    private Beacon beacon;

    public GattCallback(Beacon beacon) {
        super();
        // Need a reference for the beacon object to call back.
        this.beacon = beacon;
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        // When this object calls back, set the received signal strength value.
        this.beacon.setSignalStrength(rssi);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
    }
}
