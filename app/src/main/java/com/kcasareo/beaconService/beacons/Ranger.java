package com.kcasareo.beaconService.beacons;

/** Ranging Localise Class
 * Created by Kevin on 16/08/2017.
 */

public class Ranger {
    private static final double speed_light_seconds = 299792458;

    /* Time in seconds helper method
    *
    *
    * */
    public static double meters(double seconds) {
        return speed_light_seconds * seconds;
    }

    /* Defined unit helper
    * Given time in units, return distance in meters
    *
    * */
    public static double meters(long time, UNIT_TIME unit_time) {
        switch (unit_time) {
            case SECONDS:
                return meters((double)time);
            case MILLISECONDS:
                return meters((double) time / 1000);
            case MICROSECONDS:
                return meters((double) time / 1000000);
            case NANOSECONDS:
                return meters((double) time / 1000000000);
            default:
                return -1;
        }
    }
}
