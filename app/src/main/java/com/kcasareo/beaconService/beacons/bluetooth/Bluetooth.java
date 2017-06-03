
package com.kcasareo.beaconService.beacons.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import com.kcasareo.beaconService.beacons.Beacon;


/**
 * Created by Kevin on 5/05/2017.
 */
public abstract class Bluetooth extends Beacon {
    protected String id;
    protected BluetoothDevice device;
    protected BluetoothGatt gatt;

    public Bluetooth(String id) {
        this.id = id;
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

    public void connect() { gatt.connect(); }
    public void disconnect() { gatt.disconnect(); }

    @Override
    public synchronized void setSignalStrength(int signalStrength) {
        super.setSignalStrength(signalStrength);
    }
}
