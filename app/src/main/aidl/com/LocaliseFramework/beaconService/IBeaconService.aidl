// IBeaconService.aidl
package com.LocaliseFramework.beaconService;
//import com.kcasareo.beaconService.frames.Snapshot;
import com.LocaliseFramework.beaconService.IBeaconServiceCallback;
import com.LocaliseFramework.beaconService.location.MODE;
// Declare any non-default types here with import statements

interface IBeaconService {

    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);

    //com.kcasareo.beaconService.frames.Snapshot sendSnapshot(com.kcasareo.beaconService.frames.Snapshot snapshot);

    //oneway void lastSnap(IBeaconServiceCallback callback);
    //oneway void closest(IBeaconServiceCallback callback);
    oneway void signalsStrength(IBeaconServiceCallback callback);
    oneway void localise(IBeaconServiceCallback callback);
    void whitelistAddress(String address);
    void updatePosition(String address, double x, double y);
    void setMode(in MODE mode);
    /* Register callback */
    //void registerCallback(IBeaconServiceCallback callback);
    //void unregisterCallback(IBeaconServiceCallback callback);

}
