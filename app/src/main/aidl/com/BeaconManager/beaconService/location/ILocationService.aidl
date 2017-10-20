// ILocationService.aidl
package com.LocaliseFramework.beaconService.location;
import com.LocaliseFramework.beaconService.location.Position;
// Declare any non-default types here with import statements

interface ILocationService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    double distance(String UUID);
    Position position();
    void add(double pos_x, double pos_y, String UUID);
}
