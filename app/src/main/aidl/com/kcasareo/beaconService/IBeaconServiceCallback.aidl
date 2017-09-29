// IBeaconServiceCallback.aidl
package com.kcasareo.beaconService;

// Declare any non-default types here with import statements
//import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.beaconService.beacons.bluetooth.SignalData;

/* This is the callback interface to be implemented at the clientside.
*
*
*/

interface IBeaconServiceCallback {
    //void handleResponse(String address);

    // Handle snapshot returns in the client.
    //void handleResponse(in Snapshot snapshot);
    void signalsResponse(in SignalData data);
    //void closestResponse(in SignalData data);
}
