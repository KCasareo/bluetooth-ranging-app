package com.kcasareo.beaconService.beacons.bluetooth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 28/09/2017.
 * History object to be constructed at the view side.
 */

public class History {
    private ArrayList<SignalData> signalDataHistory;

    public History() {

    }

    // Add history
    public void update() {

    }

    // Parse
    public SignalData read(int at) {
        return signalDataHistory.get(at);
    }
}
