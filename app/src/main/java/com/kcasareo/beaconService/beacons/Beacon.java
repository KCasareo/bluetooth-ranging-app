package com.kcasareo.beaconService.beacons;

import com.kcasareo.beaconService.beacons.bluetooth.SignalDatum;
import com.kcasareo.location.Position;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    private final String TAG = getClass().getSimpleName();
    protected long signalStrength;
    protected String address;
    protected String name;
    protected Position position;
    protected long id;
    public abstract long signalStrength();
    public abstract void setSignalStrength(long signalStrength);
    public abstract long id();
    public abstract String name();
    public abstract void poll();
    public abstract SignalDatum datum();
    public abstract String address();
    public abstract Runnable task();
    public abstract void update(Position position);
    /*
    * Returns exportable data.
    *
    * */
    public SignalDatum signalData() {
        return new SignalDatum(signalStrength, address, id, name, position);
    }

    public Beacon() {
        //signalStrength = 0;
    }



}
