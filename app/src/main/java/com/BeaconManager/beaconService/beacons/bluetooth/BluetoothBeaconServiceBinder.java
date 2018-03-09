package com.BeaconManager.beaconService.beacons.bluetooth;

import android.os.RemoteException;
import android.util.Log;

import com.BeaconManager.beaconService.IBeaconService;
import com.BeaconManager.beaconService.IBeaconServiceCallback;
import com.BeaconManager.beaconService.beacons.Beacons;
import com.BeaconManager.beaconService.location.MODE;
import com.BeaconManager.beaconService.location.Position;

/** Actually Implement these lol
 * Created by Kevin on 5/01/2018.
 */

public abstract class BluetoothBeaconServiceBinder extends IBeaconService.Stub {
    private String TAG = "ServiceBinder";
    private Beacons beacons;
    public BluetoothBeaconServiceBinder(Beacons beacons) {
        this.beacons = beacons;
    }
    @Override
    public void signalsStrength(IBeaconServiceCallback callback) throws RemoteException {
        // Redesign beacon to take a bluetooth device and connect to gatt
        callback.signalsResponse(beacons.getSignalData());

    }

    @Override
    public void localise(IBeaconServiceCallback callback) throws RemoteException {
        callback.localiseResponse(beacons.localise());
    }

    @Override
    public void getStrengthDistanceZero(IBeaconServiceCallback callback) throws RemoteException {
        callback.strengthDistanceZeroResponse(beacons.getDistanceZero());
    }

    @Override
    public void getPathLossFactor(IBeaconServiceCallback callback) throws RemoteException {
        callback.pathLossFactorResponse(beacons.getPathLossFactor());
    }

    @Override
    public void whitelistAddress(String address) throws RemoteException {
        Log.i(TAG, "Address whitelisted: " + address);
        beacons.filter(address);
    }

    @Override
    public void updatePosition(String address, double x, double y) throws RemoteException {
        beacons.findBeacon(address).update(new Position(x, y));
    }


    // Set Mode to localise
    @Override
    public void setMode(MODE mode) throws RemoteException {
        beacons.setMode(mode);
    }

    @Override
    public void setPathLoss(double pathLoss) throws RemoteException {
        beacons.setPathLoss(pathLoss);
    }

    @Override
    public void setStrengthDistanceZero(long strengthDistanceZero) throws RemoteException {
        beacons.setStrengthDistanceZero(strengthDistanceZero);
    }
}

// Create a private implementation to gattcallback then pass a new copy of for each
