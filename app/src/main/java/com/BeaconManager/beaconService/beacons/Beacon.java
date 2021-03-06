package com.BeaconManager.beaconService.beacons;


import com.BeaconManager.beaconService.beacons.bluetooth.SignalDatum;
import com.BeaconManager.beaconService.location.Localiser;
import com.BeaconManager.beaconService.location.Position;

import java.util.ArrayList;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    private final String TAG = getClass().getSimpleName();
    protected long signalStrength;
    protected Localiser localiser;
    protected String address;
    protected String name;
    protected Position position;
    protected long id;
    protected ArrayList<Long> strengths;
    public abstract long id();
    public abstract long signalStrength();
    public abstract void setSignalStrength(long signalStrength);
    public abstract String name();
    public abstract SignalDatum datum();
    public abstract String address();
    public abstract Runnable task();
    public abstract void update(Position position);
    public abstract void setLocaliser(Localiser localiser);
/*
    public Beacon() {

    }*/
}
