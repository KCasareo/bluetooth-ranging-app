package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.kcasareo.beaconService.beacons.Beacon;

import java.util.Timer;
import java.util.TimerTask;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;

/**
 * Created by Kevin on 4/06/2017.
 * This takes a reference to the beacon it is bound to so it can modify the RSSI value.
 */

public class GattCallback extends BluetoothGattCallback {
    private Beacon beacon;
    private Timer mRssiTimer;
    private final String TAG = "Gatt Callback " + this.hashCode();
    public GattCallback(Bluetooth bluetooth) {
        super();
        Log.i(TAG, "Callback built");
        // Need a reference for the beacon object to call back.
        this.beacon = bluetooth;
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Log.d(TAG, "Read Remote RSSI");
        //super.onReadRemoteRssi(gatt, rssi, status);
        Log.d(TAG, "RSSI : " + rssi);

        // When this object calls back, set the received signal strength value.
        this.beacon.setSignalStrength((long)rssi);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if(status == GATT_SUCCESS) {
            Log.i(TAG, "Successful connection");
        }
        /* //This crashes things
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    beacon.poll();
                }
            };

            mRssiTimer = new Timer();
            mRssiTimer.schedule(task, 1000, 2000);
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            mRssiTimer.cancel();
        }
        //*/
    }
}
