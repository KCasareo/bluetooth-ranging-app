package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
<<<<<<< HEAD
import android.os.ParcelUuid;
=======
>>>>>>> BeaconRestructure
import android.util.Log;

import com.kcasareo.beaconService.beacons.Beacon;
import com.kcasareo.beaconService.location.Position;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 4/06/2017.
 */

public class Bluetooth extends Beacon {
    private final String TAG = getClass().getSimpleName();
    protected BluetoothDevice device;
    protected int identifier;
    protected BluetoothGatt profile;
    protected TimerTask rssiTask;
    protected Timer timer;

    public Bluetooth(BluetoothDevice device) {
        // Initialise signal strength to 0.
        super();
        this.device = device;
        this.identifier = device.hashCode();
        this.address = device.getAddress();
        this.name = device.getName();
        this.position = new Position(0, 0);
        if (this.name == null) {
            this.name = "Generic Bluetooth";
        }
<<<<<<< HEAD
        this.id = device.getUuids()[0];
=======
        this.id = device.hashCode();
        rssiTask = new TimerTask() {
            @Override
            public void run() {
                poll();
            }
        };
>>>>>>> BeaconRestructure
    }


    public void setProfile(BluetoothGatt profile) {
        this.profile = profile;
    }

    @Override
    public long signalStrength() {
        return this.signalStrength;
    }

    // Will be set by the gatt callback
    @Override
    public void setSignalStrength(long signalStrength) {
        this.signalStrength = signalStrength;
    }

    @Override
    public ParcelUuid id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    // Async request from server for rssi.
    @Override
    public void poll() {
        // Profile is null for some reason.
<<<<<<< HEAD
        //Log.i(TAG, "Poll");
        if (profile != null) {
            //Log.d(TAG, "calling");
=======
        Log.i(TAG, "Polling");
        if (profile != null) {
            Log.i(TAG, "Fire read remote");
>>>>>>> BeaconRestructure
            profile.readRemoteRssi();
        }
    }

    @Override
    public String address() {
        return this.address;
    }

    @Override
    public Runnable task() {
        return new Runnable() {
            private String TAG = Bluetooth.this.TAG + "/task()";
            @Override
            public void run() {
                Log.i(TAG, "Polling");
                poll();
            }
        };
    }

    public void update(Position position) {
        Log.d(TAG, position.toString());
        Log.d(TAG, this.position.toString());
        this.position = position;
    }

    @Override
    public SignalDatum datum() {
<<<<<<< HEAD
        Log.i(TAG, "Datum: " + signalStrength + " Address" + address + " Id" + id + " Name" + name);
        return new SignalDatum(signalStrength, address, id, name);
=======
        Log.d(TAG, "Position is " + this.position.toString());
        return new SignalDatum(signalStrength, address, id, name, this.position);
>>>>>>> BeaconRestructure
    }
}
