package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.kcasareo.beaconService.beacons.Beacon;
//import com.kcasareo.beaconService.beacons.Delay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    // Remove sensortag service mapping.
    //Base UUID
    //F000-0000-0451-4000-B000-000000000000
    //
    public static final String SENSORTAG(String mapping) {
        return "F000-" + mapping + "-0451-4000-B000-00000000000";
    }

    public static final String REFERENCE_UUID = SENSORTAG("EA00");
    public static final String REFERENCE_CHAR_1 = SENSORTAG("EA01");
    public static final String REFERENCE_CHAR_2 = SENSORTAG("EA02");

    private Beacon beacon;
    private Timer mRssiTimer;
    private final String TAG = "Gatt Callback " + this.hashCode();
    private Timer rssiTimer;
    //private ArrayList<Delay> delays;
    private Calendar calendar;
    /*
    public GattCallback(Bluetooth bluetooth, ArrayList<Delay> delays){
        super();
        this.beacon = bluetooth;
        this.delays = delays;
    }*/

    public GattCallback(Bluetooth bluetooth) {
        super();
        Log.i(TAG, "Callback built");
        // Need a reference for the beacon object to call back.
        this.beacon = bluetooth;
    }

    /*
    public void addDelay(Delay delay) {
        delays.add(delay);
    }*/

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
<<<<<<< HEAD
        Log.d(TAG, "Read Remote RSSI");
        //super.onReadRemoteRssi(gatt, rssi, status);
        Log.d(TAG, "RSSI : " + rssi);

        // When this object calls back, set the received signal strength value.
        this.beacon.setSignalStrength((long)rssi);
    }
=======
        Log.d(TAG, "Read Remote RSSI " + rssi);
        super.onReadRemoteRssi(gatt, rssi, status);
        /*
        if (delays.size() > 0)
            delays.get(delays.size()-1).endOffset();
        */
        // When this object calls back, set the received signal strength value.
        if (rssi == 0)
            return;
        this.beacon.setSignalStrength(rssi);
        //Get the last delay
>>>>>>> BeaconRestructure

    }
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
<<<<<<< HEAD
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
=======
        String intentAction;
        Log.i(TAG, "Changed");
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "New Polling");
            gatt.discoverServices();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    Log.i(TAG, "Gatt calling poll");
                    beacon.poll();
                }
            };
            //rssiTimer = new Timer();
            //rssiTimer.schedule(task, 500, 2500);
        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            Log.i(TAG, "State Connecting");
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.i(TAG, "State disconnected");

        }
    }
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "Discovered Services.");

        }
>>>>>>> BeaconRestructure
    }


}
