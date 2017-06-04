package com.kcasareo.beaconService.beacons;

import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    private final String TAG = getClass().getSimpleName();
    protected long signalStrength;
    protected String id;
    public abstract long signalStrength();
    public abstract void setSignalStrength(long rssi);
    public abstract String id();
    public abstract void poll();

    /*
    * Returns exportable data.
    *
    * */
    public SignalDatum signalData() {
        return new SignalDatum(signalStrength);
    }

    public Beacon() { signalStrength = 0; }

}
