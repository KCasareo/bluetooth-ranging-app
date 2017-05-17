// IBeaconServiceCallback.aidl
package com.kcasareo.beaconService;

// Declare any non-default types here with import statements
import com.kcasareo.beaconService.frames.Snapshot;


/* This is the callback interface to be implemented at the clientside.
*
*
*/

interface IBeaconServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    oneway void valueChanged(int value);

    //void handleResponse(String name);

    // Handle snapshot returns in the client.
    void handleResponse(in Snapshot snapshot);
}
