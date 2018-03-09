package com.BeaconManager.beaconService;

import android.bluetooth.le.ScanCallback;

import com.BeaconManager.beaconService.beacons.Beacon;
import com.BeaconManager.beaconService.beacons.BeaconFactory;
import com.BeaconManager.beaconService.beacons.BeaconFactoryParam;
import com.BeaconManager.beaconService.beacons.ScanCallbackFactory;
import com.BeaconManager.beaconService.beacons.bluetooth.BluetoothFactory;
import com.BeaconManager.beaconService.location.LocaliserFactory;

/**
 * Change this to an actual interface.
 * Created by Kevin on 28/12/2017.
 */

public abstract class PureBeaconService {
    private ScanCallbackFactory scanCallbackFactory;
    private LocaliserFactory localiserFactory;
    private BeaconFactory beaconFactory;
    private IBeaconService iBeaconService;


    public PureBeaconService() {
        this(null, new LocaliserFactory(), new BluetoothFactory());
    }

    public PureBeaconService(ScanCallbackFactory scanCallbackFactory,
                             LocaliserFactory localiserFactory,
                             BeaconFactory beaconFactory) {
        this.scanCallbackFactory = scanCallbackFactory;
        this.localiserFactory = localiserFactory;
        this.beaconFactory = beaconFactory;

    }




}
