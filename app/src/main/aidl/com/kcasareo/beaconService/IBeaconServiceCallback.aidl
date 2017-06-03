// IBeaconServiceCallback.aidl
package com.kcasareo.beaconService;

// Declare any non-default types here with import statements
import com.kcasareo.beaconService.frames.Snapshot;
import java.util.Map;

/* This is the callback interface to be implemented at the clientside.
*
*
*/

interface IBeaconServiceCallback {
    //void handleResponse(String name);

    // Handle snapshot returns in the client.
    void handleResponse(in Snapshot snapshot);
    void signalsResponse(in Map signalStrength);
}
