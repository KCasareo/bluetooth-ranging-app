package com.BeaconManager.beaconService.location;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

/**
 * Created by Kevin on 24/10/2017.
 */

public class DimensionThree extends Localiser {
    public DimensionThree() {
        super();
    }

    @Override
    public Position localise(ArrayList<Position> positions) {
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
}
