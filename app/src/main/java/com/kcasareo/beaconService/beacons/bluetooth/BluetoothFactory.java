package com.kcasareo.beaconService.beacons.bluetooth;

import com.kcasareo.beaconService.beacons.BeaconCreateDescription;

/**
 * Created by Kevin on 5/05/2017.
 */
public class BluetoothFactory {

    private BluetoothFactory() {

    }

    public static Bluetooth create(BeaconCreateDescription description) {
        //Bluetooth bluetooth;
        switch(description.bluetooth_type()) {
            case ESTIMOTE:
                return new Estimote(description.id());
            case SENSOR_TAG:
                return new SensorTag(description.id());
            default:
                // Return a non-reliable bluetooth sensor connection if the connection is unrecognised;
                return new DynamicBluetooth(description.device());
        }
    }
}