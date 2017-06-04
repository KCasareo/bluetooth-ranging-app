package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;

import com.kcasareo.beaconService.beacons.Beacon;

/**
 * Created by Kevin on 4/06/2017.
 */

public class GattCallback extends BluetoothGattCallback {
    private Beacon beacon;

    // Pass a beacon object that will be modified by this callback.
    public GattCallback(Beacon beacon) {
        super();
        this.beacon = beacon;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        beacon.setSignalStrength(rssi);
    }

}
