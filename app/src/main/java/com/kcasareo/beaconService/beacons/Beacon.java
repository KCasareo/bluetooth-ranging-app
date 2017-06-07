package com.kcasareo.beaconService.beacons;

import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    private final String TAG = getClass().getSimpleName();
    protected long signalStrength;
    protected String name;
    public abstract long signalStrength();
    public abstract void setSignalStrength(long signalStrength);
    public abstract String id();
    public abstract void poll();
    public abstract SignalDatum datum();
    /*
    * Returns exportable data.
    *
    * */
    public SignalDatum signalData() {
        return new SignalDatum(signalStrength, name);
    }

    public Beacon() { signalStrength = 0; }

}
