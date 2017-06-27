package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.kcasareo.beaconService.beacons.Beacon;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 4/06/2017.
 * This takes a reference to the beacon it is bound to so it can modify the RSSI value.
 */

public class GattCallback extends BluetoothGattCallback {
    private Beacon beacon;
    private final String TAG = "Gatt Callback " + this.hashCode();
    private Timer rssiTimer;
    public GattCallback(Bluetooth bluetooth) {
        super();
        // Need a reference for the beacon object to call back.
        this.beacon = bluetooth;
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Log.d(TAG, "Read Remote RSSI " + rssi);
        super.onReadRemoteRssi(gatt, rssi, status);
        // When this object calls back, set the received signal strength value.
        if (rssi == 0)
            return;
        this.beacon.setSignalStrength(rssi);
    }
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        String intentAction;
        Log.i(TAG, "Changed");
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "New Polling");
            gatt.discoverServices();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    beacon.poll();
                }
            };
            rssiTimer = new Timer();
            rssiTimer.schedule(task, 500, 500);
        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            Log.i(TAG, "State Connecting");
        }
    }
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "Discovered Services.");
        }
    }
}
