package com.BeaconManager.beaconService.beacons;

/**
 * Created by Kevin on 30/12/2017.
 */

public interface BeaconFactory {
    public Beacon create(BeaconFactoryParam param);
}
