package com.kcasareo.beaconService.beacons.Bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalData implements Parcelable {
    private long rssi;
    protected SignalData(Parcel in) {
        rssi = in.readLong();
    }


    public SignalData(long rssi) {
        this.rssi = rssi;
    }

    public final Creator<SignalData> CREATOR = new Creator<SignalData>() {
        @Override
        public SignalData createFromParcel(Parcel in) {
            return new SignalData(in);
        }

        @Override
        public SignalData[] newArray(int size) {
            return new SignalData[size];
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
