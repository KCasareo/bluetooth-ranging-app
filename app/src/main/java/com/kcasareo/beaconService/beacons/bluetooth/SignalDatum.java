package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalDatum implements Parcelable, Comparable<SignalDatum> {
    private long rssi;
    private double distance = 0;
    private double delay = 0;
    private String address;
    private long id;
    private String name;
    private final String TAG = getClass().getSimpleName();

    protected SignalDatum(Parcel in) {
        rssi = in.readLong();
        address = in.readString();
        name = in.readString();
        distance = in.readDouble();
        //delay = in.readDouble();
    }

    public String name() {
        return name;
    }

    public double distance() { return distance; }


    public SignalDatum(long rssi, String address, long id, String name) {
        this.rssi = rssi;
        this.address = address;
        this.id = id;
        this.name = name;
        //this.delay = new Delay();
        Log.i(TAG, "New Data point: " + rssi + " " + address + " " + id + " " + "name");
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.rssi);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeDouble(this.distance);
        //dest.writeDouble(this.distance);
    }

    // Returns hashcode of device detected.
    public long id() {
        return id;
    }

    public String toString() {
        return "Name: " + this.name + " RSSI: " + this.rssi;
    }

    protected String address() {
        return address;
    }

    // Manipulate contains method in BeaconAdapter.set()
    @Override
    public boolean equals(Object o) {
        return this.address.equals(((SignalDatum) o).address());
    }


    // For sorting hashmap
    @Override
    public int compareTo(@NonNull SignalDatum o) {
        return o.rssi == this.rssi ? 0 :    // Equal
                o.rssi > this.rssi? -1 : 1; // Less Than : Greater Than
    }

    /* Default fragment view */
    protected class ScanViewFragment extends FragmentActivity {

    }
}
