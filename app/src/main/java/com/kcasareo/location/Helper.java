package com.kcasareo.location;

/**
 * Created by Kevin on 23/08/2017.
 * Stateless helper functions
 */

public class Helper {
    public static double distance(double pos_x, double pos_y) {
        return Math.pow(Math.pow(pos_x, 2) + Math.pow(pos_y, 2), 0.5);
    }

    // For 3d positions
    public static double distance(double pos_x, double pos_y, double pos_z) {
        return Math.pow(Math.pow(pos_x, 2) + Math.pow(pos_y, 2) + Math.pow(pos_z, 2), 0.5);
    }
}
