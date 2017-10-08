// IBeaconServiceCallback.aidl
package com.LocaliseFramework.beaconService;

// Declare any non-default types here with import statements
//import com.kcasareo.beaconService.frames.Snapshot;
import com.LocaliseFramework.beaconService.beacons.bluetooth.SignalData;
import com.LocaliseFramework.beaconService.location.Position;
/* This is the callback interface to be implemented at the clientside.
*
*
*/

interface IBeaconServiceCallback {
    //void handleResponse(String address);

    // Handle snapshot returns in the client.
    //void handleResponse(in Snapshot snapshot);
    void signalsResponse(in SignalData data);
    void localiseResponse(in Position position);
    //void closestResponse(in SignalData data);
}
