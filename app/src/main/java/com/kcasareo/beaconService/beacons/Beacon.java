package com.kcasareo.beaconService.beacons;

import android.os.ParcelUuid;

import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    private final String TAG = getClass().getSimpleName();
    protected long signalStrength;
    protected String address;
    protected String name;
    protected ParcelUuid id;
    public abstract long signalStrength();
    public abstract void setSignalStrength(long signalStrength);
    public abstract ParcelUuid id();
    public abstract String name();
    public abstract void poll();
    public abstract SignalDatum datum();
    public abstract String address();
    public abstract Runnable task();
    /*
    * Returns exportable data.
    *
    * */
    public SignalDatum signalData() {
        return new SignalDatum(signalStrength, address, id, name);
    }

    public Beacon() { signalStrength = 0; }



}
