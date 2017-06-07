package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import com.kcasareo.beaconService.beacons.Beacon;

/**
 * Created by Kevin on 4/06/2017.
 */

public class Bluetooth extends Beacon {
    private final String TAG = getClass().getSimpleName();
    protected BluetoothDevice device;
    protected int identifier;
    protected BluetoothGatt profile;

    public Bluetooth(BluetoothDevice device) {
        // Initialise signal strength to 0.
        super();
        this.device = device;
        this.identifier = device.hashCode();
        this.address = device.getAddress();
        this.name = device.getName();
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
        this.signalStrength = signalStrength;
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
        if (profile != null)
            profile.readRemoteRssi();
    }

    @Override
    public String address() {
        return this.address;
    }

    @Override
    public SignalDatum datum() {
        return new SignalDatum(signalStrength, address, id, name);
    }
}
