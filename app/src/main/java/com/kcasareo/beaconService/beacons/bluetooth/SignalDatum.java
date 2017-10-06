package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kcasareo.beaconService.location.Localise;
import com.kcasareo.beaconService.location.Position;

/**
 * Created by Kevin on 4/06/2017.
 */

<<<<<<< HEAD
public class SignalDatum implements Parcelable {
    private long rssi;
    private String address;
    private ParcelUuid id;
    private String name;
=======
public class SignalDatum implements Parcelable, Comparable<SignalDatum> {
    protected long rssi;
    protected double distance = 0;
    //private double delay = 0;
    protected String address;
    protected long id;
    protected String name;
    protected Position position;
    private final String TAG = getClass().getSimpleName();

    /* When extending SignalDatum, always call super first */
>>>>>>> BeaconRestructure
    protected SignalDatum(Parcel in) {
        rssi = in.readLong();
        id = in.readLong();
        address = in.readString();
        name = in.readString();
        distance = in.readDouble();
        position = in.readParcelable(Position.class.getClassLoader());

        //delay = in.readDouble();

    }

    public String name() {
        return name;
    }

    public double distance() { return distance; }

    public long rssi() { return rssi; }

<<<<<<< HEAD
    public SignalDatum(long rssi, String address, ParcelUuid id, String name) {
=======

    public SignalDatum(long rssi, String address, long id, String name, Position position) {
>>>>>>> BeaconRestructure
        this.rssi = rssi;
        this.address = address;
        this.id = id;
        this.name = name;
        this.distance = Localise.convert((double)rssi);
        this.position = position;
        position.setRange(distance);
        Log.i(TAG, "New Data point: " + rssi + " " + address + " " + id + " " + "name");
        Log.d(TAG, "Finding");
    }

    public final Creator<SignalDatum> CREATOR = new Creator<SignalDatum>() {
        @Override
        public SignalDatum createFromParcel(Parcel in) {
            return new SignalDatum(in);
        }

        @Override
        public SignalDatum[] newArray(int size) {
            return new SignalDatum[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /* When extending SignalDatum, call super.writeToParcel(dest, flags) before adding your custom fields */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.rssi);
        dest.writeLong(this.id);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeDouble(this.distance);
        dest.writeParcelable(this.position, 0);
    }

    // Returns hashcode of device detected.
    public ParcelUuid id() {
        return id;
    }

    public void update(double x, double y) {
        position.update(x, y);
    }

    public void update(Position position) {
        Log.d(TAG, "Updating Position");
        this.position = position;
    }

    public String toString() {
        return "Name: " + this.name + " RSSI: " + this.rssi + "\n Distance: " + String.format("%.2fm", this.distance) + String.format(" X:%.1f Y:%.1f", this.position.x(), this.position.y()); }

    public String address() {
        return address;
    }

    public double x() {
        return position.x();
    }

    public double y() {
        return position.y();
    }

    // Manipulate contains method in BeaconAdapter.add()
    @Override
    public boolean equals(Object o) {
        // Guard against incorrect types
        if (o instanceof SignalDatum)
            return this.address.equals(((SignalDatum) o).address());
        return false;
    }


    // For sorting hashmap
    @Override
    public int compareTo(@NonNull SignalDatum o) {
        return o.rssi == this.rssi ? 0 :    // Equal
                o.rssi > this.rssi? -1 : 1; // Less Than : Greater Than
    }

}
