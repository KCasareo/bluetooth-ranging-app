package com.BeaconManager.beaconService.beacons.bluetooth;

import com.BeaconManager.beaconService.beacons.BEACON_TYPE;
import com.BeaconManager.beaconService.beacons.BeaconFactoryParam;
import com.BeaconManager.beaconService.location.Position;

/**
 * Created by Kevin on 30/12/2017.
 */

public class BluetoothFactoryParam implements BeaconFactoryParam {
    protected BEACON_TYPE type;
    public String name;
    public String address;
    public int identifier;
    public Position position;

    public BluetoothFactoryParam() {
        type = BEACON_TYPE.BLUETOOTH;
    }

    public BluetoothFactoryParam(String name, String address, int identifier, Position position) {
        this();
        this.name = name;
        this.address = address;
        this.identifier = identifier;
        this.position = position;
    }

    @Override
    public BEACON_TYPE type() {
        return type();
    }
}
