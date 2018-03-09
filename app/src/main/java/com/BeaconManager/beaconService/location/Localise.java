package com.BeaconManager.beaconService.location;

import android.util.Log;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

/**
 * Created by Kevin on 23/08/2017.
 * Stateless static helper functions for Localisation and Ranging.
 * All static pure methods.
 */



public class Localise {

    public static double distance(double pos_x, double pos_y) {
        return distance(pos_x, pos_y, 0);
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
    private final String TAG = "Localise";
    // Convert the RSSI indicator to distance
    public static double convert(double db) {
        return Math.pow(10, (rss0 - db)/ (factor * 10));
    }

    //Determine what position the nodes are in.
    /* Explicitly asks for three nodes.
    * This is a naive localisation w/ linear least square algorithm.
    * Expects the three closest nodes.
    * */
    public static Position localise(ArrayList<Position> positions) {
        final int MAT3_SIZE = 3;
        final int MIN_BEACONS = 4;
        /* Require a 3x3 matrix to find x,y,z*/
        /*
        *   [ a b c ] [ x ]   [ d ]
        *   [ e f g ] [ y ] = [ h ]
        *   [ i j k ] [ z ]   [ l ]
        *   Solve for x,y,z
        * R_2 ** 2 - R_1 ** 2
        * R_3 ** 2 - R_2 ** 2
        * R_4 ** 2 - R_3 ** 2
        *
        * LHS always results in some variant of 2x(x2-x1) + 2y(y2-y1) + 2z(z2-z1)
        *
        *
        *  Constraints: m x m matrix, m = d+1 where d is the number of dimensions of the problem space.
        *  A^-1 . A  . [ x y z ]^T =    A^-1 . [d h i]^T
        *
        * */
        // Error if not enough beacons
        if(positions.size() < MIN_BEACONS)
            return null;

        double[][] matrixLeft = new double[MAT3_SIZE][MAT3_SIZE];
        // Column matrix for the answer
        double[][] matrixRight = new double[3][1];



        for(int row = 0; row < MAT3_SIZE; row++) {
            Position first = positions.get(row);
            double r1 = first.range();
            double x1 = first.x();
            double y1 = first.y();
            double z1 = first.z();
            Position second = positions.get(row+1);
            double r2 = second.range();
            double x2 = second.x();
            double y2 = second.y();
            double z2 = second.z();
            matrixLeft[row] =
                new double[]{
                    -2*(x1 - x2),
                    -2*(y1 - y2),
                    -2*(z1 - z2)
            };
            matrixRight[row][0] = r1*r1-r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2 - z1*z1 + z2*z2;
        }

        RealMatrix lhs = new Array2DRowRealMatrix(matrixLeft);
        RealMatrix rhs = new Array2DRowRealMatrix(matrixRight);

        // Post multiply the inverse of the left side matrix with the rhs to get approximations for x,y,z
        rhs = MatrixUtils.inverse(lhs).multiply(rhs);
        double[][] result = rhs.getData();
        return new Position(result[0][0],result[1][0],result[2][0]);
    }

    public static Position localise(Position pos1, Position pos2, Position pos3) {
        final int MAT2_SIZE = 2;
        final int MAX_SAMPLE = 3;

        String TAG = "2d Localise";
        /*
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

        //Storage for readability, going to arrange as a add of linear equations.
        double a, b, c, d, e, f;
        //double[][] matrix
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
        * //*//*
        RealMatrix rhs = new Array2DRowRealMatrix(new double[]{c, f});
        RealMatrix lhs = new Array2DRowRealMatrix(
                new double[][] {
                        {a, b},
                        {d, e}
                });


        if ((a*e - b*d) == 0) {
            return null;
        }
        double detA = 1/(a*e - b*d);

        rhs = MatrixUtils.inverse(lhs).multiply(rhs);

        // ;
        x = rhs.getData()[0][0];//(e*c - b*f) * detA;
        y = rhs.getData()[1][0];//(-d*c + a*f) * detA;
        Log.i(TAG, "X:" +x  + "Y" + y);
        return new Position(x,y);
        //*/
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(pos1);
        positions.add(pos2);
        positions.add(pos3);
        double x,y;
        double[][] matrixLeft = new double[MAT2_SIZE][MAT2_SIZE];
        double[][] matrixRight = new double[2][1];
        for (int row = 0; row < MAX_SAMPLE; row++) {
            Position first = positions.get(row);

            double r1 = first.range();
            double x1 = first.x();
            double y1 = first.y();
            Position second = positions.get(row+1);
            double r2 = second.range();
            double x2 = second.x();
            double y2 = second.y();
            matrixLeft[row] =
                    new double[]{
                            -2*(x1 - x2),
                            -2*(y1 - y2)
                    };
            matrixRight[row][0] = r1*r1-r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2;
        }

        RealMatrix lhs = new Array2DRowRealMatrix(matrixLeft);
        RealMatrix rhs = new Array2DRowRealMatrix(matrixRight);

        // Post multiply the inverse of the left side matrix with the rhs to get approximations for x,y,z
        rhs = MatrixUtils.inverse(lhs).multiply(rhs);
        double[][] result = rhs.getData();



        // ;
        x = result[0][0]; //(e*c - b*f) * detA;
        y = result[1][0];//(-d*c + a*f) * detA;
        Log.i(TAG, "X:" +x  + "Y" + y);
        return new Position(x , y);
    }


}
