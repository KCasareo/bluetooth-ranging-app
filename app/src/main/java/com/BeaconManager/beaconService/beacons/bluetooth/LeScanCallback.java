package com.BeaconManager.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.util.Log;

import com.BeaconManager.beaconService.BeaconService;
import com.BeaconManager.beaconService.beacons.Beacon;
import com.BeaconManager.beaconService.beacons.Beacons;

/**
 * Created by Kevin on 21/01/2018.
 */

public class LeScanCallback extends ScanCallback {
    private Beacons beacons;
    private BeaconService beaconService;

    public LeScanCallback(Beacons beacons, BeaconService beaconService) {
        this.beacons = beacons;
        this.beaconService = beaconService;
    }

    final String TAG = "LE Scan Callback";

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        BluetoothDevice device = result.getDevice();
        int rssi = result.getRssi();
        Log.i(TAG, "New LE Device: " + device.getAddress() + " @ " + rssi);
        // Will request a static factory next time.

        if (!beacons.matches(device.getAddress()))
            return;
        if (beacons.contains(device.getAddress())) {
            Log.i(TAG, "Found existing.");
            Beacon beacon = beacons.findBeacon(device.getAddress());
            //beacon.poll();

            // This one is getting called, not onreadremote
            if (result.getRssi() != 0 && result.getRssi() != beacon.signalStrength()) {
                beacon.setSignalStrength(result.getRssi());

            }/* else
                    beacon.poll();
                //*/
            return;
        }

        Log.i(TAG, "Building for " + device.getAddress());
        Bluetooth bluetooth = BluetoothFactory.create(device);
        BluetoothGattCallback callback = new GattCallback(bluetooth);
        BluetoothGatt gatt = device.connectGatt(beaconService, true, callback);
        bluetooth.setProfile(gatt);
        beacons.add(bluetooth, gatt);


    }
}