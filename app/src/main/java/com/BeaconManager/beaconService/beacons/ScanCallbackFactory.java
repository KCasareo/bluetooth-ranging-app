package com.BeaconManager.beaconService.beacons;

import android.bluetooth.le.ScanCallback;

/**
 * Created by Kevin on 5/01/2018.
 */

public interface ScanCallbackFactory {
    public ScanCallback create();
}
