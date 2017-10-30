package com.BeaconManager.beaconService.location;

import java.util.ArrayList;

/**
 * Created by Kevin on 24/10/2017.
 */

public abstract class Localiser {
    protected final double DEFAULT_PATHLOSS = 2.2;
    protected final long DEFAULT_ZERO = -61;
    protected long strengthDistanceZero;
    protected double pathLossFactor;
    public Localiser() {
         strengthDistanceZero = DEFAULT_ZERO;
         pathLossFactor = DEFAULT_PATHLOSS;
    }


    public double convert(double db) {
        return Math.pow(10, (strengthDistanceZero - db)/ (pathLossFactor * 10));
    }
    public abstract Position localise(ArrayList<Position> positions);
    public void setPathLossFactor(double pathLossFactor) {
        this.pathLossFactor = pathLossFactor;
    };
    public void setStrengthDistanceZero(long strengthDistanceZero) {
        this.strengthDistanceZero = strengthDistanceZero;
    };

    public double pathLossFactor() {
        return this.pathLossFactor;
    }

    public long distanceZero() {
        return this.strengthDistanceZero;
    }
}
