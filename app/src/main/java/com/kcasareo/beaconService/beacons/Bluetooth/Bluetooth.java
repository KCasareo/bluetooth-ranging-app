package com.kcasareo.beaconService.beacons.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.os.Parcel;
import android.telephony.SignalStrength;

import com.kcasareo.beaconService.beacons.Beacon;

/**
 * Created by Kevin on 4/06/2017.
 */

public class Bluetooth extends Beacon {
    protected BluetoothDevice device;
    protected int identifier;
    
    public Bluetooth(BluetoothDevice device) {
        // Initialise signal strength to 0.
        super();
        this.device = device;
        this.identifier = device.hashCode();
    }


    @Override
    public long signalStrength() {
        return this.signalStrength;
    }

    @Override
    public void setSignalStrength(long rssi) {
        this.signalStrength = rssi;
    }

    @Override
    public String id() {
        return null;
    }
}
