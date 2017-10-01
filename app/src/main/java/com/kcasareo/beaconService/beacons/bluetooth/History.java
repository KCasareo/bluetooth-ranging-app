package com.kcasareo.beaconService.beacons.bluetooth;

import com.kcasareo.beaconService.beacons.BeaconAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 28/09/2017.
 * History object to be constructed at the view side.
 */

public class History {
    private ArrayList<SignalData> signalDataHistory;
    private int index;
    //private BeaconAdapter mBeaconAdapter;
    public History() {
        signalDataHistory = new ArrayList<>();
        index = 0;
    }


    /* Do I need this? //
    public History(BeaconAdapter beaconAdapter) {
        this();
        mBeaconAdapter = beaconAdapter;
    }*/
    // Called by the buttons
    public void next() {
        if (index < signalDataHistory.size() - 1)
            index++;
    }

    // Called
    public void prev() {
        if (index > 0)
            index--;
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

    public SignalData showCurrent() { return read(index); }
}
