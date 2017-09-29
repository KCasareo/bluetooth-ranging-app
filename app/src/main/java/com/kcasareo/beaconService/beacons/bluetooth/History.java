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
        signalDataHistory = new ArrayList<>();
    }

    // Add history
    // The latest history is retrieved using the the main activity's service connection.
    public void update(SignalData data) {
        signalDataHistory.add(data);
        signalDataHistory.get(signalDataHistory.size() -1).sort();
    }

    /* Get the data at frame X */
    public SignalData read(int at) {
        return signalDataHistory.get(at);
    }
}
