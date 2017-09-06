package com.kcasareo.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Kevin on 23/08/2017.
 */

public class Position implements Comparable<Position>, Parcelable {
    private double pos_x, pos_y;
    private double range;

    public Position(double pos_x, double pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public Position (double pos_x, double pos_y, double range) {
        this(pos_x, pos_y);
        this.range = range;
    }

    protected Position(Parcel in) {
        pos_x = in.readDouble();
        pos_y = in.readDouble();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    public double x() {
        return pos_x;
    }

    public double y() {
        return pos_y;
    }

    public void update(double pos_x, double pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    /*
    * compareTo return values;
    * */
    private final static int EQUALS = 0;
    private final static int GREATER = 1;
    private final static int LESS = -1;

    @Override
    public int compareTo(@NonNull Position other) {
        double local = Helper.distance(this.pos_x, this.pos_y);
        double compared = Helper.distance(other.x(), other.y());
        return local == compared ? EQUALS :
                local > compared ? GREATER : LESS;
    }

    // Incase you have to check for distance between nodes
    public double range(@NonNull Position other) {
        return Helper.distance(Math.abs(this.pos_x - other.x()), Math.abs(this.pos_y - other.y()));
    }

    // Return range from sensor to node
    public double range() {
        return this.range;
    }

    public void setRange(@NonNull Position local) {
        this.range = range(local);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(pos_x);
        dest.writeDouble(pos_y);
    }
}
