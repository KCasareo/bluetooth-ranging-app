package com.BeaconManager.beaconService.location;

import android.util.Log;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

/**
 * Created by Kevin on 24/10/2017.
 */

public class DimensionTwo extends Localiser {
    private final int MAX_SAMPLE = 3;
    private final int MAT2_SIZE = 2;

    public DimensionTwo() {

    }

    @Override
    public Position localise(ArrayList<Position> positions) {
        String TAG = "2d Localise";
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
