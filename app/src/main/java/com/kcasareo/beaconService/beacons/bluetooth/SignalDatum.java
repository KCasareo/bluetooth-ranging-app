package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalDatum implements Parcelable {
    private long rssi;
    protected SignalDatum(Parcel in) {
        rssi = in.readLong();
    }


    public SignalDatum(long rssi) {
        this.rssi = rssi;
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
    }
}
