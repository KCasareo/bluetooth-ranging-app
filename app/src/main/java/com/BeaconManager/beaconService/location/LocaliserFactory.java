package com.BeaconManager.beaconService.location;

/**
 * Created by Kevin on 24/10/2017.
 */

public class LocaliserFactory {
    public static Localiser create(MODE mode) {
        switch(mode) {
            case DIM_3:
                return new DimensionThree();
            case DIM_2:
                return new DimensionTwo();
            default:
                return new DimensionTwo();

        }
    }
}
