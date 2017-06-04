
package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.os.Parcel;

import com.kcasareo.beaconService.beacons.bluetooth.GattCallback;
import com.kcasareo.beaconService.beacons.Beacon;


/**
 * Created by Kevin on 5/05/2017.
 */
public abstract class Bluetooth extends Beacon {
    protected String id;
    protected BluetoothDevice device;
    protected BluetoothGatt gatt;

    // This callback object to be created and bound by BeaconService
    //protected BluetoothGattCallback callback;

    public Bluetooth(String id) {
        this.id = id;
    }

    public Bluetooth(BluetoothDevice device) {
        // Set id to UUID
        this(device.getUuids().toString());
        this.device = device;
    }

    @Override
    public String id() {
        return id;
    }

    public Bluetooth(BluetoothDevice device, BluetoothGatt gatt) {
        this();
        this.device = device;
        this.gatt = gatt;
    }

    public Bluetooth() {
        super();
    }

    public Bluetooth(Parcel in) {
        readFromParcel(in);
    }

    public void connect() { gatt.connect(); }
    public void disconnect() { gatt.disconnect(); }

    @Override
    public synchronized void setSignalStrength(int signalStrength) {
        super.setSignalStrength(signalStrength);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(device, flags);
    }

    @Override
    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        device = in.readParcelable(BluetoothGatt.class.getClassLoader());
    }
}
