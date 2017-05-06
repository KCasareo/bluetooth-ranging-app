
package com.kcasareo.beaconService.Beacons.Bluetooth;

import com.kcasareo.beaconService.Beacons.Beacon;


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
