package com.kcasareo.location;

import java.util.ArrayList;

/**
 * Created by Kevin on 23/08/2017.
 * Stateless static helper functions
 */

public class Helper {
    public static double distance(double pos_x, double pos_y) {
        return Math.pow(Math.pow(pos_x, 2) + Math.pow(pos_y, 2), 0.5);
    }

    // For 3d entries
    public static double distance(double pos_x, double pos_y, double pos_z) {
        return Math.pow(Math.pow(pos_x, 2) + Math.pow(pos_y, 2) + Math.pow(pos_z, 2), 0.5);
    }
    /* rss0 - the RSSI when the distance from the beacon is 1m */
    /* empirical result places this value between -52 to -65*/
    /* -52 occurs when there is clear LOS between the phone and the sensor. */
    /* -65 when the sensor is on the floor */
    private static final double rss0 = -54;
    /* path loss factor - factor n, usually somewhere between 2 and 2.5 */
    private static final double factor = 2.7;
    // Convert the RSSI indicator to distance
    public static double convert(double db) {
        return Math.pow(10, (rss0 - db)/ (factor * 10));
    }

    //Determine what position the nodes are in.
    /* Explicitly asks for three nodes.
    * This is a naive localisation w/ linear least square algorithm.
    * Expects the three closest nodes.
    * */
    public static Position localise(Position pos1, Position pos2, Position pos3) {
        double x,y;
        x = y = 0;

        // Put calculating code here.
        double r1 = pos1.range();
        double r2 = pos2.range();
        double r3 = pos3.range();

        double x1 = pos1.x();
        double x2 = pos2.x();
        double x3 = pos3.x();

        double y1 = pos1.y();
        double y2 = pos2.y();
        double y3 = pos3.y();

        //Storage for readability, going to arrange as a set of linear equations.
        double a, b, c, d, e, f;
        a = -2*x1 + 2*x2;
        b = -2*y1 + 2*y2;
        c = r1*r1 - r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2;
        d = -2*x2 + 2*x3;
        e = -2*y2 + 2*y3;
        f = r2*r2 - r3*r3 - x2*x2 + x3*x3 - y2*y2 + y3*y3;
        /*
        *  [ a b ] [ x ] = [ c ]
        *  [ d e ] [ y ]   [ f ]
        *
        *  Let [ a b ] = A
        *      [ d e ]
        *
        *  Linear Least Squares to solve the closest point [x y]
        *  A^-1 A [ x y ]^T = A^-1 [ c f ]^T
        *
        *  | x | =     1    * [ e -b ] [ c ]
        *  | y |    ae - bd   [-d  a ] [ f ]
        *
        * */

        if ((a*e - b*d) == 0) {
            return null;
        }
        double detA = 1/(a*e - b*d);

        // ;
        x = (e*c - b*f) * detA;
        y = (-d*c + a*f) * detA;


        return new Position(x,y);
    }
}
