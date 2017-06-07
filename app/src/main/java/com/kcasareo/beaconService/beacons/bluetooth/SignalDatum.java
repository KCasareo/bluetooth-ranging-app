package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalDatum implements Parcelable {
    private long rssi;
    private String name;
    private long id;
    protected SignalDatum(Parcel in) {
        rssi = in.readLong();
        name = in.readString();
    }

    public String name() {
        return name;
    }


    public SignalDatum(long rssi, String name, long id) {
        this.rssi = rssi;
        this.name = name;
        this.id = id;
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
        dest.writeString(this.name);
    }

    // Returns hashcode of device detected.
    public long id() {
        return id;
    }

    public String toString() {
        return "Name: " + this.name + " RSSI: " + this.rssi;
    }
}