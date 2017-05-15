
package com.kcasareo.beaconService.beacons.bluetooth;

import com.kcasareo.beaconService.beacons.Beacon;


/**
 * Created by Kevin on 5/05/2017.
 */
public abstract class Bluetooth extends Beacon {
    protected String id;


    public Bluetooth(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return null;
    }

}
