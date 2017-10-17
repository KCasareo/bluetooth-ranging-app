package com.LocaliseFramework.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.util.Log;

import com.LocaliseFramework.beaconService.beacons.Beacon;
import com.LocaliseFramework.beaconService.location.Position;

import java.util.ArrayList;
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
    protected final int MAX_SIZE = 20;

    public Bluetooth(BluetoothDevice device) {
        // Initialise signal strength to 0.
        super();
        this.strengths = new ArrayList<>();
        this.device = device;
        this.identifier = device.hashCode();
        this.address = device.getAddress();
        this.name = device.getName();
        this.position = new Position(0, 0);
        if (this.name == null) {
            this.name = "Generic Bluetooth";
        }
        this.id = device.hashCode();

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
        // Calculate the weighted signal strength before adding it.
        strengths.add(calculate(signalStrength));
        // Remove the first to ensure only MAX_SIZE number of values included in weight calc
        if (strengths.size() > MAX_SIZE)
            strengths.remove(0);
    }

    @Override
    protected long calculate(long signalStrength) {
        final double weightCurrent = 0.25;
        final double weightNew = 0.75;
        double total = 0;
        for (long value : strengths) {
            total += value;
        }
        // Weight function
        return (long) (total / strengths.size() * weightCurrent + signalStrength * weightNew);
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public String name() {
        return this.name;
    }

    // Async request from server for rssi.
    @Override
    public void poll() {
        // Profile is null for some reason.
        Log.i(TAG, "Polling");
        if (profile != null) {
            Log.i(TAG, "Fire read remote");
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
            @Override
            public void run() {
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
        Log.d(TAG, "Position is " + this.position.toString());
        return new SignalDatum(strengths.get(strengths.size()-1), address, id, name, this.position);
    }
}
