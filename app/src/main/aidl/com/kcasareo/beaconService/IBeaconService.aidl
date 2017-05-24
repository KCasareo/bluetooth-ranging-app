// IBeaconService.aidl
package com.kcasareo.beaconService;
import com.kcasareo.beaconService.frames.Snapshot;
import com.kcasareo.beaconService.IBeaconServiceCallback;

// Declare any non-default types here with import statements

interface IBeaconService {
    //int getPid();

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);

    //com.kcasareo.beaconService.frames.Snapshot sendSnapshot(com.kcasareo.beaconService.frames.Snapshot snapshot);

    oneway void lastSnap(IBeaconServiceCallback callback);

    /* Register callback */
    void registerCallback(IBeaconServiceCallback callback);
    void unregisterCallback(IBeaconServiceCallback callback);

}
